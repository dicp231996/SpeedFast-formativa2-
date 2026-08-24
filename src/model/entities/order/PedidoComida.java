package model.entities.order;

import data.enumerate.TipoServicio;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

public class PedidoComida extends Pedido {

    public PedidoComida() {
        super();
        this.setTipoPedido("Comida");
    }

    public PedidoComida(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
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