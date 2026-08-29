package model.core;

import model.entities.dealer.Repartidor;

public abstract class Pedido {

    // Atributos base
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm; // Nuevo atributo de distancia

    protected Repartidor repartidorAsignado;

    // Constructor por defecto
    public Pedido() {
        this.idPedido = "GEN-0000";
        this.direccionEntrega = "Dirección no especificada";
        this.tipoPedido = "Estándar";
        this.distanciaKm = 0.0;
        this.repartidorAsignado = null;
    }

    // Constructor con todos los atributos base
    public Pedido(String idPedido, String direccionEntrega, String tipoPedido, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
        this.repartidorAsignado = null;
    }

    // Getters
    public String getIdPedido() { return idPedido; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public String getTipoPedido() { return tipoPedido; }
    public double getDistanciaKm() { return distanciaKm; }
    public Repartidor getRepartidorAsignado() { return repartidorAsignado; }

    // Setters
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public void setTipoPedido(String tipoPedido) { this.tipoPedido = tipoPedido; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }
    public void setRepartidorAsignado(Repartidor repartidorAsignado) { this.repartidorAsignado = repartidorAsignado; }

    // =========================================================
    // MÉTODOS ABSTRACTOS
    // =========================================================
    public abstract boolean validarRequisitos(Repartidor candidato);

    // Nuevo método: Obliga a cada tipo de pedido a definir cómo calcula su tiempo
    public abstract double calcularTiempoEntrega();

    // =========================================================
    // LÓGICA CENTRALIZADA DE ASIGNACIÓN
    // =========================================================
    public void asignarRepartidor(Repartidor candidato) {
        System.out.println("Evaluando al repartidor " + candidato.getNombreCompleto() +
                " para el pedido " + this.idPedido + "...");

        if (validarRequisitos(candidato)) {
            this.repartidorAsignado = candidato;
            System.out.println("-> ÉXITO: Repartidor asignado correctamente.\n");
        } else {
            System.out.println("-> RECHAZADO: El repartidor no cumple con los requisitos del pedido.\n");
        }
    }

    // =========================================================
    // NUEVO MÉTODO: MOSTRAR RESUMEN
    // =========================================================
    public void mostrarResumen() {
        System.out.println("--- RESUMEN DEL PEDIDO ---");
        System.out.println("ID: " + this.idPedido);
        System.out.println("Tipo de Servicio: " + this.tipoPedido);
        System.out.println("Dirección: " + this.direccionEntrega);
        System.out.println("Distancia de ruta: " + this.distanciaKm + " km");

        if (this.repartidorAsignado != null) {
            System.out.println("Repartidor Asignado: " + this.repartidorAsignado.getNombreCompleto());
            // Llama al método polimórfico para calcular el tiempo dinámicamente
            System.out.println("Tiempo estimado de entrega: " + calcularTiempoEntrega() + " minutos");
        } else {
            System.out.println("Estado: Esperando asignación de repartidor.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Detalles del Pedido -> ID: ")
                .append(this.idPedido)
                .append(" | Dirección: ")
                .append(this.direccionEntrega)
                .append(" | Tipo: ")
                .append(this.tipoPedido)
                .append(" | Distancia: ")
                .append(this.distanciaKm).append(" km");

        return sb.toString();
    }
}