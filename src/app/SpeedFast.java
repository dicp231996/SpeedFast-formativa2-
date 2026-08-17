package app;

import data.util.GestorInstancias;
import model.core.Pedido;
import model.entities.dealer.Repartidor;

import java.util.ArrayList;

public class SpeedFast {

    public static void main(String[] args) {

        // 1. Definir las rutas de los archivos .txt (ajusta la ruta según dónde los hayas guardado)
        String rutaPedidos = "resources/pedidos.txt";
        String rutaRepartidores = "resources/repartidores.txt";

        System.out.println("=========================================");
        System.out.println("       INICIANDO SISTEMA SPEEDFAST       ");
        System.out.println("=========================================\n");

        // 2. Cargar datos dinámicamente usando el Gestor
        System.out.println("--- CARGANDO BASES DE DATOS ---");
        ArrayList<Pedido> listaPedidos = GestorInstancias.cargarPedidos(rutaPedidos);
        ArrayList<Repartidor> listaRepartidores = GestorInstancias.cargarRepartidores(rutaRepartidores);

        System.out.println("Total de Pedidos cargados: " + listaPedidos.size());
        System.out.println("Total de Repartidores cargados: " + listaRepartidores.size() + "\n");

        // 3. Orquestación: Asignación de repartidores
        System.out.println("--- INICIANDO PROCESO DE ASIGNACIÓN ---");

        for (Pedido pedido : listaPedidos) {
            System.out.println(">> Procesando Pedido ID: " + pedido.getIdPedido() + " (" + pedido.getTipoPedido() + ")");

            for (int i = 0; i < listaRepartidores.size(); i++) {
                Repartidor candidato = listaRepartidores.get(i);

                // El pedido evalúa internamente si el candidato cumple sus requisitos
                pedido.asignarRepartidor(candidato);

                // Si la asignación fue exitosa (el atributo ya no es null)
                if (pedido.getRepartidorAsignado() != null) {
                    // Removemos al repartidor de la lista de disponibles para que no tome otro pedido
                    listaRepartidores.remove(i);
                    // Detenemos la búsqueda para este pedido y pasamos al siguiente
                    break;
                }
            }
        }

        // 4. Mostrar el resumen final aprovechando el Polimorfismo (método toString)
        System.out.println("\n=========================================");
        System.out.println("        RESUMEN FINAL DE ENTREGAS        ");
        System.out.println("=========================================");

        for (Pedido pedido : listaPedidos) {
            System.out.println(pedido.toString());
            System.out.println("--------------------------------------------------");
        }

        System.out.println("Repartidores que quedaron sin asignación: " + listaRepartidores.size());
    }
}
