package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameModesView extends JFrame {

    private static final Color BG           = new Color(150, 180, 220);
    private static final Color TITLE_SHADOW = new Color(60, 60, 60);
    private static final Color TITLE_RED    = new Color(200, 20, 20);
    private static final Color BTN_RED      = new Color(180, 20, 20);
    private static final Color BTN_BLUE     = new Color(20, 20, 160);
    private static final Color BTN_GREEN    = new Color(20, 130, 20);
    private static final Color BTN_LOCKED   = new Color(100, 100, 110);

    private final JFrame previous;

    /**
     * Crea la ventana de selección de modos de juego.
     *
     * @param previous ventana anterior para poder regresar
     */
    public GameModesView(JFrame previous) {
        this.previous = previous;
        setTitle("The DOPO Hardest Game - Modos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(BorderFactory.createLineBorder(new Color(80, 100, 150), 4));

        root.add(buildTitle(),   BorderLayout.NORTH);
        root.add(buildModes(),   BorderLayout.CENTER);
        root.add(buildBack(),    BorderLayout.SOUTH);

        setContentPane(root);
        setSize(750, 480);
        setLocationRelativeTo(null);
    }

    /**
     * Construye el panel del título de la ventana.
     *
     * @return panel con el título dibujado
     */
    private JPanel buildTitle() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();

                g2.setFont(new Font("Arial Black", Font.BOLD, 42));
                FontMetrics fm = g2.getFontMetrics();
                String text = "SELECCIONA UN MODO";
                int x = (w - fm.stringWidth(text)) / 2;

                g2.setColor(TITLE_SHADOW);
                g2.drawString(text, x + 3, 58);
                g2.setColor(TITLE_RED);
                g2.drawString(text, x, 55);
            }
        };
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(750, 80));
        return panel;
    }

    /**
     * Construye el panel con las tarjetas de modos de juego.
     *
     * @return panel con los tres modos
     */
    private JPanel buildModes() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        panel.add(buildModeCard(
            "SOLO\nPLAYER",
            "Un jugador controla\nel cuadrado rojo.\nRecolecta monedas\ny llega a la meta.",
            "← → ↑ ↓",
            BTN_RED,
            true,
            e -> launchSolo()
        ));

        panel.add(buildModeCard(
            "PLAYER\nvs PLAYER",
            "Dos jugadores\ncompiten en el\nmismo tablero.\nGana el más rápido.",
            "WASD + Flechas",
            BTN_BLUE,
            false,
            null
        ));

        panel.add(buildModeCard(
            "PLAYER\nvs MACHINE",
            "Compite contra\nla máquina en\nel mismo tablero.\n¿Puedes ganarle?",
            "← → ↑ ↓",
            BTN_GREEN,
            false,
            null
        ));

        return panel;
    }

    /**
     * Construye una tarjeta de modo de juego.
     *
     * @param title    título del modo
     * @param desc     descripción del modo
     * @param controls controles del modo
     * @param color    color del modo
     * @param enabled  si el modo está habilitado
     * @param action   acción al hacer clic
     * @return panel con la tarjeta del modo
     */
    private JPanel buildModeCard(String title, String desc, String controls,
                                  Color color, boolean enabled, ActionListener action) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(enabled ? new Color(220, 230, 245) : new Color(200, 200, 210));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(enabled ? color : BTN_LOCKED, 2),
            BorderFactory.createEmptyBorder(16, 14, 16, 14)
        ));

        String[] titleLines = title.split("\n");
        for (String line : titleLines) {
            JLabel lbl = new JLabel(line);
            lbl.setFont(new Font("Arial Black", Font.BOLD, 16));
            lbl.setForeground(enabled ? color : BTN_LOCKED);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(lbl);
        }

        card.add(Box.createVerticalStrut(10));

        String[] descLines = desc.split("\n");
        for (String line : descLines) {
            JLabel lbl = new JLabel(line);
            lbl.setFont(new Font("Arial", Font.PLAIN, 12));
            lbl.setForeground(new Color(40, 40, 60));
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(lbl);
        }

        card.add(Box.createVerticalStrut(10));

        JLabel ctrl = new JLabel(controls);
        ctrl.setFont(new Font("Monospaced", Font.BOLD, 11));
        ctrl.setForeground(new Color(80, 80, 100));
        ctrl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(ctrl);

        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(12));

        JButton btn = new JButton(enabled ? "JUGAR" : "Próximamente");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Arial Black", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(enabled ? color : BTN_LOCKED);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setEnabled(enabled);
        if (enabled) {
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addActionListener(action);
        }
        card.add(btn);

        return card;
    }

    /**
     * Construye el panel inferior con el botón de volver.
     *
     * @return panel con el botón de volver
     */
    private JPanel buildBack() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        panel.setBackground(BG);

        JButton back = new JButton("← Volver");
        back.setFont(new Font("Arial", Font.BOLD, 13));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(80, 80, 120));
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.addActionListener(e -> {
            dispose();
            previous.setVisible(true);
        });
        panel.add(back);
        return panel;
    }

    /**
     * Lanza el juego en modo solo pidiendo el nombre del jugador.
     */
    private void launchSolo() {
        String name = JOptionPane.showInputDialog(
            this, "Nombre del jugador:", "The DOPO Hardest Game",
            JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.isBlank()) return;
        new GameView(this, name).setVisible(true);
        setVisible(false);
    }
}