package model.interfaces;
import model.core.Pedido;

public interface Historial {
    // Añade un pedido finalizado a la lista de éxitos
    void registrarEntregaExitosa(Pedido pedido);

    // Imprime en consola todos los pedidos que llegaron a su destino
    void mostrarHistorialEntregas();
}
