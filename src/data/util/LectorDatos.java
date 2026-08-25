package data.util;

import data.enumerate.TipoServicio;
import model.core.Pedido;
import model.entities.dealer.Repartidor;
import model.entities.order.PedidoComida;
import model.entities.order.PedidoEncomienda;
import model.entities.order.PedidoExpress;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LectorDatos {

    // Método para leer el archivo de pedidos y devolver un ArrayList de objetos Pedido
    public static ArrayList<Pedido> leerPedidos(String rutaArchivo) {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";"); // Separador

                String tipoPedido = datos[0];
                String idPedido = datos[1];
                String direccion = datos[2];

                // Todos los pedidos ahora requieren una distancia (índice 3)
                double distanciaKm = Double.parseDouble(datos[3]);

                // Instanciación basada en el tipo leído con los nuevos constructores
                if (tipoPedido.equalsIgnoreCase("Comida")) {
                    listaPedidos.add(new PedidoComida(idPedido, direccion, distanciaKm));

                } else if (tipoPedido.equalsIgnoreCase("Encomienda")) {
                    // El índice 4 ahora es exclusivo para el peso de la Encomienda
                    double peso = Double.parseDouble(datos[4]);
                    listaPedidos.add(new PedidoEncomienda(idPedido, direccion, distanciaKm, peso));

                } else if (tipoPedido.equalsIgnoreCase("Express")) {
                    listaPedidos.add(new PedidoExpress(idPedido, direccion, distanciaKm));
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en el archivo de pedidos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico en pedidos: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Faltan datos en una línea del archivo de pedidos.");
        }

        return listaPedidos;
    }

    // Método para leer el archivo de repartidores y devolver un ArrayList de objetos Repartidor
    public static ArrayList<Repartidor> leerRepartidores(String rutaArchivo) {
        ArrayList<Repartidor> listaRepartidores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                // Mapeo de datos respetando el orden del archivo txt
                String nombre = datos[0];
                String telefono = datos[1];

                // valueOf convierte el String exacto a la constante del Enumerador
                TipoServicio tipo = TipoServicio.valueOf(datos[2]);

                boolean tieneMochila = Boolean.parseBoolean(datos[3]);
                double capacidadPeso = Double.parseDouble(datos[4]);
                boolean estaCerca = Boolean.parseBoolean(datos[5]);

                // Instanciación y adición a la lista
                Repartidor repartidor = new Repartidor(nombre, telefono, tipo, tieneMochila, capacidadPeso, estaCerca);
                listaRepartidores.add(repartidor);
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en el archivo de repartidores: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error al asignar el enumerador TipoServicio: " + e.getMessage());
        }

        return listaRepartidores;
    }
}