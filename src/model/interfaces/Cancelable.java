package model.interfaces;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

public interface Cancelable {
    Repartidor cancelarPedido(Pedido pedido, String motivo);
    boolean isCancelado(Pedido pedido);
    String getMotivoCancelacion(Pedido pedido);
}
