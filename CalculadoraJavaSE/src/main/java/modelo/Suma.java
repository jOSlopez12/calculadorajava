package modelo;

/**
 * Operación de suma. Encapsula la lógica de sumar dos números.
 */
public class Suma implements Operacion {

    @Override
    public double calcular(double a, double b) {
        return a + b;
    }

    @Override
    public String getSimbolo() {
        return "+";
    }
}
