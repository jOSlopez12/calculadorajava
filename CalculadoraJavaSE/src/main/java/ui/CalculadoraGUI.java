package ui;

import calculadora.Calculadora;
import calculadora.TipoOperacion;
import modelo.DivisionPorCeroException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Interfaz gráfica de escritorio construida con Swing (parte estándar
 * de Java SE, no requiere ningún framework externo).
 *
 * Esta clase solo se encarga de la presentación: delega todo el
 * cálculo a {@link Calculadora}, respetando la separación de
 * responsabilidades. El comportamiento imita una calculadora de
 * teléfono: se teclean dígitos, se elige una operación y se pulsa "=".
 */
public class CalculadoraGUI extends JFrame {

    // ---- Paleta de colores (tema oscuro estilo app móvil) ----
    private static final Color BG_FONDO = new Color(0x1c1c1e);
    private static final Color BG_NUMERO = new Color(0x333333);
    private static final Color BG_NUMERO_HOVER = new Color(0x474747);
    private static final Color BG_OPERADOR = new Color(0xff9f0a);
    private static final Color BG_FUNCION = new Color(0xa5a5a5);
    private static final Color TEXTO_CLARO = Color.WHITE;
    private static final Color TEXTO_OSCURO = new Color(0x1c1c1e);
    private static final Color TEXTO_SECUNDARIO = new Color(0x8e8e93);
    private static final Font FUENTE_HISTORIAL = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font FUENTE_RESULTADO = new Font("SansSerif", Font.PLAIN, 42);
    private static final Font FUENTE_BOTON = new Font("SansSerif", Font.PLAIN, 22);

    private final Calculadora calculadora = new Calculadora();

    private final JLabel etiquetaHistorial = new JLabel(" ", SwingConstants.RIGHT);
    private final JLabel etiquetaResultado = new JLabel("0", SwingConstants.RIGHT);

    // Estado de la calculadora (entrada estilo teléfono)
    private String entradaActual = "0";
    private Double valorAcumulado = null;
    private TipoOperacion operacionPendiente = null;
    private boolean esperandoNuevoValor = false;

    public CalculadoraGUI() {
        super("Calculadora - Java SE (POO)");
        construirInterfaz();
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 16));
        getContentPane().setBackground(BG_FONDO);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(construirPantalla(), BorderLayout.NORTH);
        add(construirTeclado(), BorderLayout.CENTER);

        setSize(340, 480);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel construirPantalla() {
        JPanel pantalla = new JPanel();
        pantalla.setLayout(new BoxLayout(pantalla, BoxLayout.Y_AXIS));
        pantalla.setOpaque(false);
        pantalla.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

        etiquetaHistorial.setFont(FUENTE_HISTORIAL);
        etiquetaHistorial.setForeground(TEXTO_SECUNDARIO);
        etiquetaHistorial.setAlignmentX(Component.RIGHT_ALIGNMENT);

        etiquetaResultado.setFont(FUENTE_RESULTADO);
        etiquetaResultado.setForeground(TEXTO_CLARO);
        etiquetaResultado.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pantalla.add(etiquetaHistorial);
        pantalla.add(etiquetaResultado);
        return pantalla;
    }

    private JPanel construirTeclado() {
        JPanel teclado = new JPanel(new GridLayout(5, 4, 10, 10));
        teclado.setOpaque(false);

        teclado.add(crearBoton("C", BG_FUNCION, TEXTO_OSCURO, e -> limpiar()));
        teclado.add(crearBoton("±", BG_FUNCION, TEXTO_OSCURO, e -> cambiarSigno()));
        teclado.add(crearBoton("%", BG_FUNCION, TEXTO_OSCURO, e -> porcentaje()));
        teclado.add(crearBoton("÷", BG_OPERADOR, TEXTO_CLARO, e -> elegirOperacion(TipoOperacion.DIVISION)));

        teclado.add(crearBoton("7", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("7")));
        teclado.add(crearBoton("8", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("8")));
        teclado.add(crearBoton("9", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("9")));
        teclado.add(crearBoton("×", BG_OPERADOR, TEXTO_CLARO, e -> elegirOperacion(TipoOperacion.MULTIPLICACION)));

        teclado.add(crearBoton("4", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("4")));
        teclado.add(crearBoton("5", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("5")));
        teclado.add(crearBoton("6", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("6")));
        teclado.add(crearBoton("−", BG_OPERADOR, TEXTO_CLARO, e -> elegirOperacion(TipoOperacion.RESTA)));

        teclado.add(crearBoton("1", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("1")));
        teclado.add(crearBoton("2", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("2")));
        teclado.add(crearBoton("3", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("3")));
        teclado.add(crearBoton("+", BG_OPERADOR, TEXTO_CLARO, e -> elegirOperacion(TipoOperacion.SUMA)));

        teclado.add(crearBoton("0", BG_NUMERO, TEXTO_CLARO, e -> agregarDigito("0")));
        teclado.add(crearBoton(".", BG_NUMERO, TEXTO_CLARO, e -> agregarPunto()));
        teclado.add(crearBoton("=", BG_OPERADOR, TEXTO_CLARO, e -> calcularResultado()));
        teclado.add(new JLabel());

        return teclado;
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? BG_NUMERO_HOVER : getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(colorTexto);
        boton.setBackground(colorFondo);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.addActionListener(accion);
        return boton;
    }

    // ---- Lógica de entrada tipo teléfono ----

    private void agregarDigito(String digito) {
        if (esperandoNuevoValor || entradaActual.equals("0")) {
            entradaActual = digito;
            esperandoNuevoValor = false;
        } else {
            entradaActual += digito;
        }
        etiquetaResultado.setText(entradaActual);
    }

    private void agregarPunto() {
        if (esperandoNuevoValor) {
            entradaActual = "0.";
            esperandoNuevoValor = false;
        } else if (!entradaActual.contains(".")) {
            entradaActual += ".";
        }
        etiquetaResultado.setText(entradaActual);
    }

    private void cambiarSigno() {
        double valor = Double.parseDouble(entradaActual) * -1;
        entradaActual = formatear(valor);
        etiquetaResultado.setText(entradaActual);
    }

    private void porcentaje() {
        double valor = Double.parseDouble(entradaActual) / 100.0;
        entradaActual = formatear(valor);
        etiquetaResultado.setText(entradaActual);
    }

    private void limpiar() {
        entradaActual = "0";
        valorAcumulado = null;
        operacionPendiente = null;
        esperandoNuevoValor = false;
        etiquetaHistorial.setText(" ");
        etiquetaResultado.setText(entradaActual);
    }

    private void elegirOperacion(TipoOperacion operacion) {
        double valorActual = Double.parseDouble(entradaActual);

        if (valorAcumulado != null && !esperandoNuevoValor) {
            valorAcumulado = ejecutarCalculo(valorAcumulado, valorActual, operacionPendiente);
            entradaActual = formatear(valorAcumulado);
            etiquetaResultado.setText(entradaActual);
        } else {
            valorAcumulado = valorActual;
        }

        operacionPendiente = operacion;
        esperandoNuevoValor = true;
        etiquetaHistorial.setText(formatear(valorAcumulado) + " " + simboloDe(operacion));
    }

    private void calcularResultado() {
        if (operacionPendiente == null || valorAcumulado == null) {
            return;
        }
        double valorActual = Double.parseDouble(entradaActual);
        double resultado = ejecutarCalculo(valorAcumulado, valorActual, operacionPendiente);

        etiquetaHistorial.setText(formatear(valorAcumulado) + " " + simboloDe(operacionPendiente) + " " + formatear(valorActual) + " =");
        entradaActual = formatear(resultado);
        etiquetaResultado.setText(entradaActual);

        valorAcumulado = null;
        operacionPendiente = null;
        esperandoNuevoValor = true;
    }

    private double ejecutarCalculo(double a, double b, TipoOperacion operacion) {
        try {
            return calculadora.ejecutar(operacion, a, b);
        } catch (DivisionPorCeroException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de operación", JOptionPane.ERROR_MESSAGE);
            limpiar();
            return 0;
        }
    }

    private String simboloDe(TipoOperacion operacion) {
        return switch (operacion) {
            case SUMA -> "+";
            case RESTA -> "−";
            case MULTIPLICACION -> "×";
            case DIVISION -> "÷";
        };
    }

    private String formatear(double valor) {
        if (valor == Math.floor(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}