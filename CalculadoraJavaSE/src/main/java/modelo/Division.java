package modelo;

/**
 * Operación de división. Encapsula la lógica de dividir dos números
 * y valida la regla de negocio de no dividir entre cero.
 */
public class Division implements Operacion {

    @Override
    public double calcular(double a, double b) {
        if (b == 0) {
            throw new DivisionPorCeroException("No es posible dividir entre cero.");
        }
        return a / b;
    }

    @Override
    public String getSimbolo() {
        return "/";
    }
}
