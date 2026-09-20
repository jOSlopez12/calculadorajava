package calculadora;

import modelo.DivisionPorCeroException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculadoraTest {

    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new Calculadora();
    }

    @Test
    @DisplayName("Suma de dos números positivos")
    void testSumaPositivos() {
        assertEquals(8.0, calculadora.sumar(5, 3));
    }

    @Test
    @DisplayName("Suma con números negativos")
    void testSumaNegativos() {
        assertEquals(-2.0, calculadora.sumar(-5, 3));
    }

    @Test
    @DisplayName("Resta de dos números")
    void testResta() {
        assertEquals(2.0, calculadora.restar(5, 3));
    }

    @Test
    @DisplayName("Resta que produce resultado negativo")
    void testRestaNegativa() {
        assertEquals(-2.0, calculadora.restar(3, 5));
    }

    @Test
    @DisplayName("Multiplicación de dos números")
    void testMultiplicacion() {
        assertEquals(15.0, calculadora.multiplicar(5, 3));
    }

    @Test
    @DisplayName("Multiplicación por cero")
    void testMultiplicacionPorCero() {
        assertEquals(0.0, calculadora.multiplicar(5, 0));
    }

    @Test
    @DisplayName("División exacta de dos números")
    void testDivision() {
        assertEquals(2.5, calculadora.dividir(5, 2));
    }

    @Test
    @DisplayName("División entre cero lanza excepción")
    void testDivisionPorCero() {
        assertThrows(DivisionPorCeroException.class, () -> calculadora.dividir(5, 0));
    }

    @Test
    @DisplayName("Ejecutar operación mediante el método genérico ejecutar()")
    void testEjecutarGenerico() {
        assertEquals(10.0, calculadora.ejecutar(TipoOperacion.SUMA, 7, 3));
        assertEquals(4.0, calculadora.ejecutar(TipoOperacion.RESTA, 7, 3));
        assertEquals(21.0, calculadora.ejecutar(TipoOperacion.MULTIPLICACION, 7, 3));
    }

    @Test
    @DisplayName("Operación con decimales")
    void testConDecimales() {
        assertEquals(3.5, calculadora.sumar(1.2, 2.3), 0.0001);
    }
}
