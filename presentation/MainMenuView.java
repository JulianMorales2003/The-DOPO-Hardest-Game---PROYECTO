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

        root.add(buildTopBar(),  BorderLayout.NORTH);
        root.add(buildTitle(),   BorderLayout.CENTER);
        root.add(buildButtons(), BorderLayout.SOUTH);

        setContentPane(root);
        setSize(600, 380);
        setLocationRelativeTo(null);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG);
        bar.setBorder(BorderFactory.createEmptyBorder(12, 16, 0, 16));

        JLabel left = new JLabel("THE DOPO'S...");
        left.setFont(new Font("Arial", Font.BOLD, 14));
        left.setForeground(new Color(30, 30, 80));

        JLabel right = new JLabel("VERSION 1.0");
        right.setFont(new Font("Arial", Font.BOLD, 14));
        right.setForeground(new Color(30, 30, 80));

        bar.add(left,  BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JPanel buildTitle() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();

                // Título principal
                g2.setFont(new Font("Arial Black", Font.BOLD, 62));
                FontMetrics fm = g2.getFontMetrics();
                String line = "HARDEST GAME";
                int x = (w - fm.stringWidth(line)) / 2;
                int y = h / 2 + 20;

                g2.setColor(TITLE_SHADOW);
                g2.drawString(line, x + 4, y + 4);
                g2.setColor(TITLE_RED);
                g2.drawString(line, x, y);
            }
        };
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(600, 160));
        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 30));
        panel.setBackground(BG);

        panel.add(buildArcadeButton("PLAY\nGAME", new Color(180, 20, 20),
                e -> openModes()));
        panel.add(buildArcadeButton("LOAD\nGAME", new Color(20, 20, 160), null));
        panel.add(buildArcadeButton("LEVEL\nSELECT", new Color(20, 130, 20), null));

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

                // Fondo del botón
                Color bg = !enabled
                        ? new Color(100, 100, 110)
                        : hovered ? baseColor.brighter() : baseColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, w, h, 10, 10);
                g2.setColor(bg.darker());
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, w - 2, h - 2, 10, 10);
                g2.setStroke(new BasicStroke(1f));

                // Texto estilo arcade con sombra
                g2.setFont(new Font("Arial Black", Font.BOLD, 17));
                FontMetrics fm = g2.getFontMetrics();
                int lineH = fm.getHeight();
                int startY = (h - lineH * lines.length) / 2 + fm.getAscent();

                for (int i = 0; i < lines.length; i++) {
                    int lx = (w - fm.stringWidth(lines[i])) / 2;
                    int ly = startY + i * lineH;
                    // Sombra
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.drawString(lines[i], lx + 2, ly + 2);
                    // Texto blanco
                    g2.setColor(Color.WHITE);
                    g2.drawString(lines[i], lx, ly);
                }

                // Overlay bloqueado
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
}