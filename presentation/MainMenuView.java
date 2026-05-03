package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView extends JFrame {

    private static final Color BG           = new Color(150, 180, 220);
    private static final Color TITLE_SHADOW = new Color(60, 60, 60);
    private static final Color TITLE_RED    = new Color(200, 20, 20);

    public MainMenuView() {
        setTitle("The DOPO Hardest Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(BorderFactory.createLineBorder(new Color(80, 100, 150), 4));

        root.add(buildTitle(),   BorderLayout.CENTER);
        root.add(buildButtons(), BorderLayout.SOUTH);

        setContentPane(root);
        setSize(750, 480);
        setLocationRelativeTo(null);
    }

    private JPanel buildTitle() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();

                // Calcular posición X del título grande primero
                g2.setFont(new Font("Arial Black", Font.BOLD, 72));
                FontMetrics fmBig = g2.getFontMetrics();
                String big = "HARDEST GAME";
                int bx = (w - fmBig.stringWidth(big)) / 2;
                int by = 95;

                // "THE DOPO'S..." dibujado ANTES del título grande
                g2.setFont(new Font("Arial", Font.BOLD, 15));
                g2.setColor(new Color(30, 30, 80));
                g2.drawString("THE DOPO'S...", bx, 20);

                // Sombra del título grande
                g2.setFont(new Font("Arial Black", Font.BOLD, 72));
                g2.setColor(TITLE_SHADOW);
                g2.drawString(big, bx + 4, by + 4);

                // Título rojo encima
                g2.setColor(TITLE_RED);
                g2.drawString(big, bx, by);
            }
        };
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(750, 120));
        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 30));
        panel.setBackground(BG);

        panel.add(buildArcadeButton("PLAY\nGAME",   new Color(180, 20, 20),  e -> openModes()));
        panel.add(buildArcadeButton("LOAD\nGAME",   new Color(20, 20, 160),  null));
        panel.add(buildArcadeButton("DESIGN\nBY",   new Color(20, 130, 20),  e -> openCredits()));

        return panel;
    }

    private JPanel buildArcadeButton(String label, Color baseColor, ActionListener action) {
        String[] lines = label.split("\n");
        boolean enabled = action != null;

        JPanel btn = new JPanel() {
            private boolean hovered = false;
            {
                setOpaque(false);
                setPreferredSize(new Dimension(120, 65));
                setCursor(enabled
                        ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                        : Cursor.getDefaultCursor());

                if (enabled) {
                    addMouseListener(new MouseAdapter() {
                        @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                        @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                        @Override public void mouseClicked(MouseEvent e) {
                            action.actionPerformed(
                                new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
                        }
                    });
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();

                Color bg = !enabled
                        ? new Color(100, 100, 110)
                        : hovered ? baseColor.brighter() : baseColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, w, h, 10, 10);
                g2.setColor(bg.darker());
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, w - 2, h - 2, 10, 10);
                g2.setStroke(new BasicStroke(1f));

                g2.setFont(new Font("Arial Black", Font.BOLD, 17));
                FontMetrics fm = g2.getFontMetrics();
                int lineH = fm.getHeight();
                int startY = (h - lineH * lines.length) / 2 + fm.getAscent();

                for (int i = 0; i < lines.length; i++) {
                    int lx = (w - fm.stringWidth(lines[i])) / 2;
                    int ly = startY + i * lineH;
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.drawString(lines[i], lx + 2, ly + 2);
                    g2.setColor(Color.WHITE);
                    g2.drawString(lines[i], lx, ly);
                }

                if (!enabled) {
                    g2.setColor(new Color(0, 0, 0, 60));
                    g2.fillRoundRect(0, 0, w, h, 10, 10);
                    g2.setFont(new Font("Arial", Font.BOLD, 9));
                    g2.setColor(new Color(200, 200, 200));
                    FontMetrics sm = g2.getFontMetrics();
                    String lk = "Próximamente";
                    g2.drawString(lk, (w - sm.stringWidth(lk)) / 2, h - 5);
                }
            }
        };
        return btn;
    }

    private void openModes() {
        JOptionPane.showMessageDialog(this, "Próximamente: modos de juego");
    }

    private void openCredits() {
        JDialog dialog = new JDialog(this, "Créditos", true);
        dialog.setResizable(false);
        dialog.setSize(460, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panel.add(buildCreditLabel("Diseño Orientado a Programación de Objetos", Font.BOLD, 14));
        panel.add(Box.createVerticalStrut(10));
        panel.add(buildCreditLabel("2026-1", Font.PLAIN, 13));
        panel.add(Box.createVerticalStrut(6));
        panel.add(buildCreditLabel("Profesor: Juan Frasica", Font.PLAIN, 13));
        panel.add(Box.createVerticalStrut(20));
        panel.add(buildCreditLabel("Desarrolladores:", Font.BOLD, 13));
        panel.add(Box.createVerticalStrut(6));
        panel.add(buildCreditLabel("Julian Felipe Morales Zambrano", Font.PLAIN, 13));
        panel.add(Box.createVerticalStrut(4));
        panel.add(buildCreditLabel("Sergio Daniel Buitrago Suancha", Font.PLAIN, 13));
        panel.add(Box.createVerticalStrut(24));

        JButton close = new JButton("Cerrar");
        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        close.setFont(new Font("Arial", Font.BOLD, 13));
        close.setBackground(new Color(180, 20, 20));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> dialog.dispose());
        panel.add(close);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    private JLabel buildCreditLabel(String text, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(new Color(20, 20, 60));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}