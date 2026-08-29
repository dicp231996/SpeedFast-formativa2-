package data.util;

import model.core.Pedido;
import model.entities.dealer.Repartidor;
import model.entities.order.PedidoComida;
import model.interfaces.Asignable;
import model.interfaces.Cancelable;
import model.interfaces.Historial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControladorEnvios implements Asignable, Historial, Cancelable {

    private ArrayList<Pedido> entregasExitosas;
    private Map<Pedido, String> pedidosCancelados;

    public ControladorEnvios() {
        this.entregasExitosas = new ArrayList<>();
        this.pedidosCancelados = new HashMap<>();
    }

    @Override
    public ArrayList<Repartidor> filtrarRepartidoresElegibles(Pedido pedido, ArrayList<Repartidor> disponibles) {
        ArrayList<Repartidor> elegibles = new ArrayList<>();
        for (Repartidor candidato : disponibles) {
            if (pedido.validarRequisitos(candidato)) {
                elegibles.add(candidato);
            }
        }
        return elegibles;
    }

    @Override
    public void asignarManualmente(Pedido pedido, Repartidor repartidor) {
        if (!isCancelado(pedido)) {
            pedido.asignarRepartidor(repartidor);
        } else {
            System.out.println("-> Error: No se puede asignar un repartidor a un pedido cancelado.");
        }
    }

    @Override
    public void registrarEntregaExitosa(Pedido pedido) {
        if (pedido.getRepartidorAsignado() != null && !isCancelado(pedido)) {
            this.entregasExitosas.add(pedido);
        }
    }

    @Override
    public void mostrarHistorialEntregas() {
        System.out.println("\n--- HISTORIAL DE ENTREGAS EXITOSAS ---");
        if (entregasExitosas.isEmpty()) {
            System.out.println("No hay entregas registradas.");
        } else {
            for (Pedido p : entregasExitosas) {
                System.out.println("ID: " + p.getIdPedido() + " | Entregado por: " + p.getRepartidorAsignado().getNombreCompleto());
            }
        }
    }

    // ==========================================
    // LÓGICA DE CANCELACIÓN CON EXCEPCIÓN
    // ==========================================
    @Override
    public Repartidor cancelarPedido(Pedido pedido, String motivo) {
        Repartidor repartidorLiberado = null;

        if (pedido.getRepartidorAsignado() != null) {
            // AQUÍ ESTÁ LA MAGIA: Solo la comida se puede cancelar en ruta
            if (pedido instanceof PedidoComida) {
                System.out.println("-> Excepción aplicada: Cancelando pedido de comida en curso por contingencia.");

                repartidorLiberado = pedido.getRepartidorAsignado();
                pedido.setRepartidorAsignado(null); // Desvinculamos al repartidor del pedido
                entregasExitosas.remove(pedido);    // Lo borramos del historial de éxitos
                pedidosCancelados.put(pedido, motivo); // Lo añadimos a cancelados

            } else {
                System.out.println("-> Error: El pedido " + pedido.getIdPedido() + " ya está en ruta y su tipo de servicio no admite cancelación tardía.");
            }
        } else {
            // Cancelación normal antes de tener asignación
            pedidosCancelados.put(pedido, motivo);
            System.out.println("-> Pedido " + pedido.getIdPedido() + " cancelado exitosamente.");
        }

        return repartidorLiberado;
    }

    @Override
    public boolean isCancelado(Pedido pedido) {
        return pedidosCancelados.containsKey(pedido);
    }

    @Override
    public String getMotivoCancelacion(Pedido pedido) {
        return pedidosCancelados.getOrDefault(pedido, "El pedido no se encuentra cancelado.");
    }
}