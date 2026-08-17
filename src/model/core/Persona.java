package model.core;

public abstract class Persona {

    // Atributos
    private String nombreCompleto;
    private String telefonoContacto;

    // Constructor por defecto
    public Persona() {
        this.nombreCompleto = "No especificado";
        this.telefonoContacto = "No especificado";
    }

    // Constructor con todos los atributos
    public Persona(String nombreCompleto, String telefonoContacto) {
        this.nombreCompleto = nombreCompleto;
        this.telefonoContacto = telefonoContacto;
    }

    // Getters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    // Setters
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    // Método toString sobrescrito utilizando StringBuilder
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Información Personal -> Nombre: ")
                .append(this.nombreCompleto)
                .append(" | Teléfono: ")
                .append(this.telefonoContacto);

        return sb.toString();
    }
}
