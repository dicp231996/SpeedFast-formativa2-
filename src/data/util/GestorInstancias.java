package data.util;

import model.entities.dealer.Repartidor;
import data.enumerate.TipoServicio;
import model.core.Pedido;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class GestorInstancias {

    public static ArrayList<Pedido> cargarPedidos(String rutaArchivo) {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String nombreClase = datos[0];

                try {
                    // 1. Ubicar la clase en el paquete exacto según la jerarquía de tu proyecto
                    Class<?> claseDestino = Class.forName("model.entities.order." + nombreClase);

                    // 2. Buscar el constructor que coincida con la cantidad de argumentos del txt
                    int cantidadArgumentosTxt = datos.length - 1;
                    Constructor<?> constructorAdecuado = null;

                    for (Constructor<?> c : claseDestino.getDeclaredConstructors()) {
                        if (c.getParameterCount() == cantidadArgumentosTxt) {
                            constructorAdecuado = c;
                            break;
                        }
                    }

                    if (constructorAdecuado != null) {
                        // 3. Extraer los tipos de parámetros
                        Class<?>[] tiposParametros = constructorAdecuado.getParameterTypes();
                        Object[] argumentosConvertidos = new Object[cantidadArgumentosTxt];

                        // 4. Casteo dinámico según el tipo de dato que pide el constructor
                        for (int i = 0; i < tiposParametros.length; i++) {
                            String valorTxt = datos[i + 1];

                            if (tiposParametros[i] == String.class) {
                                argumentosConvertidos[i] = valorTxt;
                            } else if (tiposParametros[i] == double.class || tiposParametros[i] == Double.class) {
                                argumentosConvertidos[i] = Double.parseDouble(valorTxt);
                            } else if (tiposParametros[i] == int.class || tiposParametros[i] == Integer.class) {
                                argumentosConvertidos[i] = Integer.parseInt(valorTxt);
                            } else if (tiposParametros[i] == boolean.class || tiposParametros[i] == Boolean.class) {
                                argumentosConvertidos[i] = Boolean.parseBoolean(valorTxt);
                            }
                        }

                        // 5. Instanciar el objeto polimórfico y agregarlo a la lista
                        Pedido nuevoPedido = (Pedido) constructorAdecuado.newInstance(argumentosConvertidos);
                        listaPedidos.add(nuevoPedido);

                    } else {
                        System.err.println("Alerta: No se encontró un constructor en " + nombreClase +
                                " con " + cantidadArgumentosTxt + " parámetros.");
                    }

                } catch (ClassNotFoundException e) {
                    System.err.println("Error: La clase '" + nombreClase + "' no existe en el paquete 'model.entities.order'.");
                } catch (Exception e) {
                    System.err.println("Fallo al instanciar " + nombreClase + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error de lectura en el archivo de pedidos: " + e.getMessage());
        }

        return listaPedidos;
    }

    // Método para cargar los repartidores del paquete 'dealer'
    public static ArrayList<Repartidor> cargarRepartidores(String rutaArchivo) {
        ArrayList<Repartidor> listaRepartidores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                String nombre = datos[0];
                String telefono = datos[1];
                TipoServicio tipo = TipoServicio.valueOf(datos[2]);
                boolean tieneMochila = Boolean.parseBoolean(datos[3]);
                double capacidadPeso = Double.parseDouble(datos[4]);
                boolean estaCerca = Boolean.parseBoolean(datos[5]);

                listaRepartidores.add(new Repartidor(nombre, telefono, tipo, tieneMochila, capacidadPeso, estaCerca));
            }
        } catch (Exception e) {
            System.err.println("Error procesando el archivo de repartidores: " + e.getMessage());
        }

        return listaRepartidores;
    }
}