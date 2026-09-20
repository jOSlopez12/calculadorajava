package modelo;

/**
 * Excepción de negocio: se lanza cuando se intenta dividir entre cero.
 * Extiende de RuntimeException para no obligar a declarar "throws"
 * en cada método, pero documenta claramente la regla de negocio.
 */
public class DivisionPorCeroException extends RuntimeException {

    public DivisionPorCeroException(String mensaje) {
        super(mensaje);
    }
}
