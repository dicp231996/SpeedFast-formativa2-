package app;

import data.util.ControladorEnvios;
import data.util.GestorInstancias;
import model.core.Pedido;
import model.entities.dealer.Repartidor;
import model.entities.order.PedidoComida;

import java.util.ArrayList;
import java.util.Scanner;

public class SpeedFast {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String rutaPedidos = "resources/pedidos.txt";
        String rutaRepartidores = "resources/repartidores.txt";

        System.out.println("=========================================");
        System.out.println("       INICIANDO SISTEMA SPEEDFAST       ");
        System.out.println("=========================================\n");

        ArrayList<Pedido> listaPedidos = GestorInstancias.cargarPedidos(rutaPedidos);
        ArrayList<Repartidor> listaRepartidores = GestorInstancias.cargarRepartidores(rutaRepartidores);
        ControladorEnvios controlador = new ControladorEnvios();

        // =========================================================
        // FASE 1: ASIGNACIÓN DE PEDIDOS (Lógica original restaurada)
        // =========================================================
        System.out.println("--- FASE 1: ASIGNACIÓN DE PEDIDOS ---");
        System.out.println("1. Automática (El sistema evalúa y asigna bajo sus reglas)");
        System.out.println("2. Manual (El usuario elige entre los candidatos aptos)");
        System.out.print("Seleccione el método de asignación: ");

        String tipoAsignacion = scanner.nextLine();
        boolean esManual = tipoAsignacion.equals("2");

        for (Pedido pedido : listaPedidos) {
            System.out.println("\n>> Procesando Pedido ID: " + pedido.getIdPedido() + " (" + pedido.getTipoPedido() + ")");

            if (esManual) {
                // LÓGICA MANUAL: Usando el filtro de la Interfaz IAsignable
                ArrayList<Repartidor> candidatosAptos = controlador.filtrarRepartidoresElegibles(pedido, listaRepartidores);

                if (candidatosAptos.isEmpty()) {
                    System.out.println("-> Alerta: No hay repartidores elegibles. Pedido pendiente.");
                    continue;
                }

                System.out.println("Candidatos disponibles:");
                for (int i = 0; i < candidatosAptos.size(); i++) {
                    Repartidor r = candidatosAptos.get(i);
                    System.out.println("  [" + i + "] " + r.getNombreCompleto() + " | Servicio: " + r.getTipoServicio());
                }
                System.out.print("Ingrese el número del repartidor a asignar: ");

                try {
                    int index = Integer.parseInt(scanner.nextLine());
                    if (index >= 0 && index < candidatosAptos.size()) {
                        Repartidor seleccionado = candidatosAptos.get(index);
                        controlador.asignarManualmente(pedido, seleccionado);
                        controlador.registrarEntregaExitosa(pedido);
                        listaRepartidores.remove(seleccionado);
                    } else {
                        System.out.println("-> Índice inválido. Omitiendo pedido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("-> Entrada inválida. Omitiendo pedido.");
                }

            } else {
                // LÓGICA AUTOMÁTICA: Iteración clásica y Polimorfismo
                for (int i = 0; i < listaRepartidores.size(); i++) {
                    Repartidor candidato = listaRepartidores.get(i);
                    System.out.println("   [Log Main] Intentando hacer match con candidato: " + candidato.getNombreCompleto());

                    // Se ejecuta la lógica polimórfica interna del pedido
                    pedido.asignarRepartidor(candidato);

                    if (pedido.getRepartidorAsignado() != null) {
                        System.out.println("   [Log Main] MATCH EXITOSO. Repartidor asignado y retirado.");
                        controlador.registrarEntregaExitosa(pedido); // Se guarda en el historial de éxitos
                        listaRepartidores.remove(i);
                        break;
                    } else {
                        System.out.println("   [Log Main] DESCARTADO. Pasando al siguiente...");
                    }
                }

                if (pedido.getRepartidorAsignado() == null) {
                    System.out.println("-> Alerta: Ningún repartidor cumplió los requisitos. Pedido pendiente.");
                }
            }
        }

        // =========================================================
        // FASE 2: CANCELACIÓN SECUENCIAL
        // =========================================================
        System.out.println("\n=========================================");
        System.out.println("--- FASE 2: GESTIÓN DE CANCELACIONES ---");
        System.out.println("=========================================");

        boolean enFaseCancelacion = true;

        while (enFaseCancelacion) {
            int activos = 0;
            System.out.println("\nPedidos en ruta (Solo los servicios de Comida admiten cancelación tardía):");

            for (Pedido p : listaPedidos) {
                // Filtramos para mostrar solo comida que ya está en ruta y no ha sido cancelada
                if (p.getRepartidorAsignado() != null && !controlador.isCancelado(p) && p instanceof PedidoComida) {
                    System.out.println("- " + p.getIdPedido() + " | Repartidor: " + p.getRepartidorAsignado().getNombreCompleto());
                    activos++;
                }
            }

            System.out.println("Total de pedidos activos susceptibles a cancelación: " + activos);

            if (activos == 0) {
                System.out.println("-> No quedan pedidos en ruta que puedan ser cancelados bajo esta regla.");
                break;
            }

            System.out.print("\nIngrese el ID del pedido a cancelar (o escriba 'FIN' para avanzar): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("FIN")) {
                enFaseCancelacion = false;
                continue;
            }

            Pedido pedidoACancelar = null;
            for (Pedido p : listaPedidos) {
                if (p.getIdPedido().equalsIgnoreCase(input)) {
                    pedidoACancelar = p;
                    break;
                }
            }

            if (pedidoACancelar != null) {
                System.out.print("Ingrese el motivo de la cancelación: ");
                String motivo = scanner.nextLine();

                // Ejecuta la lógica centralizada y atrapa al repartidor si fue liberado
                Repartidor liberado = controlador.cancelarPedido(pedidoACancelar, motivo);

                if (liberado != null) {
                    listaRepartidores.add(liberado);
                    System.out.println("-> Se reintegró al repartidor " + liberado.getNombreCompleto() + " a la base.");
                }
            } else {
                System.out.println("-> Error: ID no encontrado o inválido.");
            }
        }

        // =========================================================
        // FASE 3: HISTORIALES FINALES
        // =========================================================
        System.out.println("\n=========================================");
        System.out.println("--- FASE 3: HISTORIALES FINALES ---");
        System.out.println("=========================================");

        // Historial de Completados
        controlador.mostrarHistorialEntregas();

        // Historial de Cancelados
        System.out.println("\n--- HISTORIAL DE PEDIDOS CANCELADOS ---");
        boolean hayCancelados = false;

        for (Pedido p : listaPedidos) {
            if (controlador.isCancelado(p)) {
                System.out.println("ID: " + p.getIdPedido() + " | Motivo: " + controlador.getMotivoCancelacion(p));
                hayCancelados = true;
            }
        }

        if (!hayCancelados) {
            System.out.println("No hay pedidos cancelados registrados.");
        }

        System.out.println("\n=========================================");
        System.out.println("       SISTEMA SPEEDFAST FINALIZADO      ");
        System.out.println("=========================================");
        scanner.close();
    }
}