package data.enumerate;

// Definición del enumerador
public enum TipoServicio {
    // Se asigna el valor amigable entre paréntesis a cada opción
    COMIDA("Entrega de Comida"),
    ENCOMIENDA("Envío de Encomienda"),
    COMPRA_EXPRESS("Compra Express");

    // Atributo privado para guardar el texto amigable
    private final String descripcionVisible;

    // Constructor del enumerador (siempre es privado por defecto en los enum)
    TipoServicio(String descripcionVisible) {
        this.descripcionVisible = descripcionVisible;
    }

    // Getter por si necesitas acceder al String desde otro lado
    public String getDescripcionVisible() {
        return descripcionVisible;
    }

    // Sobrescribimos el toString para que al imprimirlo se muestre el texto amigable
    // en lugar de la constante en MAYÚSCULAS.
    @Override
    public String toString() {
        return descripcionVisible;
    }
}
