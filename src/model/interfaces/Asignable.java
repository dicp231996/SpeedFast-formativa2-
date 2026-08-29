package model.interfaces;
import model.core.Pedido;
import model.entities.dealer.Repartidor;
import java.util.ArrayList;

public interface Asignable {
    // Recibe la base de datos de repartidores y devuelve SOLO los que cumplen los requisitos del pedido
    ArrayList<Repartidor> filtrarRepartidoresElegibles(Pedido pedido, ArrayList<Repartidor> disponibles);

    // Asigna un repartidor seleccionado manualmente por el usuario
    void asignarManualmente(Pedido pedido, Repartidor repartidor);
}
