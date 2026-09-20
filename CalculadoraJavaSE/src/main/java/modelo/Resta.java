package modelo;

/**
 * Operación de resta. Encapsula la lógica de restar dos números.
 */
public class Resta implements Operacion {

    @Override
    public double calcular(double a, double b) {
        return a - b;
    }

    @Override
    public String getSimbolo() {
        return "-";
    }
}
