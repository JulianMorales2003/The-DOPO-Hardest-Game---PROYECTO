import presentation.MainMenuView;
import javax.swing.*;

/**
 * Clase con el objetivo de iniciar el juego.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new MainMenuView().setVisible(true);
        });
    }
}