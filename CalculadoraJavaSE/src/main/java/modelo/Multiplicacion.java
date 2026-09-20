package modelo;

/**
 * Operación de multiplicación. Encapsula la lógica de multiplicar
 * dos números.
 */
public class Multiplicacion implements Operacion {

    @Override
    public double calcular(double a, double b) {
        return a * b;
    }

    @Override
    public String getSimbolo() {
        return "*";
    }
}
