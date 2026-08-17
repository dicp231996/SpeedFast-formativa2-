package model.entities.order;

import data.enumerate.TipoServicio;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

public class PedidoEncomienda extends Pedido {

    private double pesoPaquete;

    // Constructor por defecto
    public PedidoEncomienda() {
        super();
        this.setTipoPedido("Encomienda");
        this.pesoPaquete = 0.0;
    }

    // Constructor parametrizado
    public PedidoEncomienda(String idPedido, String direccionEntrega, double pesoPaquete) {
        super(idPedido, direccionEntrega, "Encomienda");
        this.pesoPaquete = pesoPaquete;
    }

    // Getters y Setters para el atributo propio
    public double getPesoPaquete() {
        return pesoPaquete;
    }

    public void setPesoPaquete(double pesoPaquete) {
        this.pesoPaquete = pesoPaquete;
    }

    // Validación de requisitos específicos para encomienda
    @Override
    protected boolean validarRequisitos(Repartidor candidato) {
        if (candidato.getTipoServicio() != TipoServicio.ENCOMIENDA) {
            System.out.println(" - Fallo: El repartidor no ofrece el servicio de Encomienda.");
            return false;
        }
        if (candidato.getCapacidadPesoMax() < this.pesoPaquete) {
            System.out.println(" - Fallo: El peso del paquete (" + this.pesoPaquete + "kg) excede la capacidad máxima del repartidor (" + candidato.getCapacidadPesoMax() + "kg).");
            return false;
        }
        return true;
    }

    // Método toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append("\n   -> Peso del paquete: ").append(this.pesoPaquete).append(" kg");

        if (this.repartidorAsignado != null) {
            sb.append("\n   -> Repartidor a cargo: ").append(this.repartidorAsignado.getNombreCompleto());
        } else {
            sb.append("\n   -> Repartidor a cargo: Pendiente de asignación");
        }
        return sb.toString();
    }
}
