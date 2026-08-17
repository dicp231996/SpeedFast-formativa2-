package model.core;

import model.entities.dealer.Repartidor;

public abstract class Pedido {

    // Atributos base
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;

    // Atributo protegido: Pertenece a Pedido, pero las clases hijas pueden verlo y usarlo
    protected Repartidor repartidorAsignado;

    // Constructor por defecto
    public Pedido() {
        this.idPedido = "GEN-0000";
        this.direccionEntrega = "Dirección no especificada";
        this.tipoPedido = "Estándar";
        this.repartidorAsignado = null; // Inicia sin repartidor
    }

    // Constructor con todos los atributos base
    public Pedido(String idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.repartidorAsignado = null;
    }

    // Getters
    public String getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    // Setters
    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public void setRepartidorAsignado(Repartidor repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    // =========================================================
    // MÉTODO ABSTRACTO (Patrón Template Method)
    // Cada clase hija DEBE implementar este método con sus reglas
    // =========================================================
    protected abstract boolean validarRequisitos(Repartidor candidato);

    // =========================================================
    // LÓGICA CENTRALIZADA DE ASIGNACIÓN
    // =========================================================
    public void asignarRepartidor(Repartidor candidato) {
        System.out.println("Evaluando al repartidor " + candidato.getNombreCompleto() +
                " para el pedido " + this.idPedido + "...");

        // Llama al método polimórfico del hijo correspondiente
        if (validarRequisitos(candidato)) {
            this.repartidorAsignado = candidato;
            System.out.println("-> ÉXITO: Repartidor asignado correctamente.\n");
        } else {
            System.out.println("-> RECHAZADO: El repartidor no cumple con los requisitos del pedido.\n");
        }
    }

    // Método toString base usando StringBuilder
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Detalles del Pedido -> ID: ")
                .append(this.idPedido)
                .append(" | Dirección de Entrega: ")
                .append(this.direccionEntrega)
                .append(" | Tipo de Pedido: ")
                .append(this.tipoPedido);

        return sb.toString();
    }
}

