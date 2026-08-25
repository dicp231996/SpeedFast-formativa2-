package model.entities.order;

import data.enumerate.TipoServicio;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

public class PedidoComida extends Pedido {

    public PedidoComida() {
        super();
        this.setTipoPedido("Comida");
    }

    // 1. CONSTRUCTOR ACTUALIZADO: Ahora recibe distanciaKm y la pasa al super()
    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Comida", distanciaKm);
    }

    // 2. IMPLEMENTACIÓN DEL MÉTODO ABSTRACTO
    @Override
    public double calcularTiempoEntrega() {
        // 15 minutos base + (2 minutos * cantidad de kilómetros)
        return 15.0 + (2.0 * this.getDistanciaKm());
    }

    @Override
    protected boolean validarRequisitos(Repartidor candidato) {
        if (candidato.getTipoServicio() != TipoServicio.COMIDA) {
            System.out.println(" - Fallo: El repartidor no ofrece el servicio de Comida.");
            return false;
        }
        if (!candidato.isTieneMochilaTermica()) {
            System.out.println(" - Fallo: El repartidor no cuenta con mochila térmica.");
            return false;
        }
        return true;
    }

    @Override
    public void asignarRepartidor(Repartidor candidato) {
        System.out.println("[Protocolo Comida] Iniciando evaluación estándar de repartidor para alimentos...");
        super.asignarRepartidor(candidato);
    }

    public void asignarRepartidor(Repartidor candidato, String criterioAsignacion) {
        System.out.println("[Protocolo Comida] Evaluando candidato bajo el criterio especial: " + criterioAsignacion);

        if (criterioAsignacion.equalsIgnoreCase("Prioridad Alta")) {
            System.out.println(" -> Notificación: Se requiere despacho inmediato para mantener la temperatura óptima.");
        }

        super.asignarRepartidor(candidato);
    }

    @Override
    public void mostrarResumen() {
        // Ejecuta el resumen estándar definido en la clase padre
        super.mostrarResumen();

        // Añade detalles específicos de la comida
        System.out.println("Nota de Despacho (Comida): Mantener los recipientes en posición horizontal y evitar movimientos bruscos.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        if (this.repartidorAsignado != null) {
            sb.append("\n   -> Repartidor a cargo: ").append(this.repartidorAsignado.getNombreCompleto());
        } else {
            sb.append("\n   -> Repartidor a cargo: Pendiente de asignación");
        }
        return sb.toString();
    }
}