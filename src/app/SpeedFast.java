package app;

import data.util.GestorInstancias;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

import java.util.ArrayList;

public class SpeedFast {

    public static void main(String[] args) {

        String rutaPedidos = "resources/pedidos.txt";
        String rutaRepartidores = "resources/repartidores.txt";

        System.out.println("=========================================");
        System.out.println("       INICIANDO SISTEMA SPEEDFAST       ");
        System.out.println("=========================================\n");

        System.out.println("--- CARGANDO BASES DE DATOS ---");
        ArrayList<Pedido> listaPedidos = GestorInstancias.cargarPedidos(rutaPedidos);
        ArrayList<Repartidor> listaRepartidores = GestorInstancias.cargarRepartidores(rutaRepartidores);

        System.out.println("Total de Pedidos cargados: " + listaPedidos.size());
        System.out.println("Total de Repartidores cargados: " + listaRepartidores.size() + "\n");

        System.out.println("--- INICIANDO PROCESO DE ASIGNACIÓN ---");

        for (Pedido pedido : listaPedidos) {
            System.out.println(">> Procesando Pedido ID: " + pedido.getIdPedido() + " (" + pedido.getTipoPedido() + ")");

            for (int i = 0; i < listaRepartidores.size(); i++) {
                Repartidor candidato = listaRepartidores.get(i);

                // Ejecución polimórfica de la asignación
                pedido.asignarRepartidor(candidato);

                if (pedido.getRepartidorAsignado() != null) {
                    listaRepartidores.remove(i);
                    break;
                }
            }
        }

        System.out.println("\n=========================================");
        System.out.println("        RESUMEN FINAL DE ENTREGAS        ");
        System.out.println("=========================================");

        for (Pedido pedido : listaPedidos) {
            // Se reemplaza toString() por el nuevo método que incluye el cálculo del tiempo
            pedido.mostrarResumen();
            System.out.println("--------------------------------------------------");
        }

        System.out.println("Repartidores que quedaron sin asignación: " + listaRepartidores.size());
    }
}