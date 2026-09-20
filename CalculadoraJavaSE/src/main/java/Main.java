import ui.CalculadoraGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraGUI ventana = new CalculadoraGUI();
            ventana.setVisible(true);
        });
    }
}
