package calculadora;

import modelo.Division;
import modelo.Multiplicacion;
import modelo.Operacion;
import modelo.Resta;
import modelo.Suma;

import java.util.EnumMap;
import java.util.Map;

/**
 * Clase central de la aplicación. Se apoya en el polimorfismo de
 * {@link Operacion} para ejecutar la operación solicitada sin usar
 * bloques if/else o switch gigantes por cada operación.
 *
 * Cada instancia de Operacion se crea una sola vez y se reutiliza
 * (patrón similar a un mini "registry" de estrategias).
 */
public class Calculadora {

    private final Map<TipoOperacion, Operacion> operaciones;

    public Calculadora() {
        operaciones = new EnumMap<>(TipoOperacion.class);
        operaciones.put(TipoOperacion.SUMA, new Suma());
        operaciones.put(TipoOperacion.RESTA, new Resta());
        operaciones.put(TipoOperacion.MULTIPLICACION, new Multiplicacion());
        operaciones.put(TipoOperacion.DIVISION, new Division());
    }

    /**
     * Ejecuta la operación indicada sobre dos operandos.
     *
     * @param tipo tipo de operación a ejecutar
     * @param a    primer operando
     * @param b    segundo operando
     * @return resultado de la operación
     */
    public double ejecutar(TipoOperacion tipo, double a, double b) {
        Operacion operacion = operaciones.get(tipo);
        if (operacion == null) {
            throw new IllegalArgumentException("Operación no soportada: " + tipo);
        }
        return operacion.calcular(a, b);
    }

    // Métodos de conveniencia, útiles para pruebas unitarias claras
    // y para mantener una API sencilla de usar desde la interfaz gráfica.

    public double sumar(double a, double b) {
        return ejecutar(TipoOperacion.SUMA, a, b);
    }

    public double restar(double a, double b) {
        return ejecutar(TipoOperacion.RESTA, a, b);
    }

    public double multiplicar(double a, double b) {
        return ejecutar(TipoOperacion.MULTIPLICACION, a, b);
    }

    public double dividir(double a, double b) {
        return ejecutar(TipoOperacion.DIVISION, a, b);
    }
}
