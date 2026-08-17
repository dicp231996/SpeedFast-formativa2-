package model.entities.order;

import data.enumerate.TipoServicio;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

public class PedidoComida extends Pedido {

    // Constructor por defecto
    public PedidoComida() {
        super();
        this.setTipoPedido("Comida");
    }

    // Constructor parametrizado
    public PedidoComida(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
    }

    // Validación de requisitos específicos para comida
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

    // Método toString
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
