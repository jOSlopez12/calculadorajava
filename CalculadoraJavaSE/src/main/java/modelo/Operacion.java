package modelo;

/**
 * Contrato que deben cumplir todas las operaciones aritméticas.
 * Aplica el principio de polimorfismo: la Calculadora no necesita
 * saber qué operación concreta está ejecutando, solo que cumple
 * este contrato.
 */
public interface Operacion {

    /**
     * Ejecuta la operación sobre dos operandos.
     *
     * @param a primer operando
     * @param b segundo operando
     * @return el resultado de la operación
     */
    double calcular(double a, double b);

    /**
     * @return el símbolo de la operación (ej. "+", "-", "*", "/")
     */
    String getSimbolo();
}
