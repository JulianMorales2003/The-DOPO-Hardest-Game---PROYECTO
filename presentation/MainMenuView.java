package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView extends JFrame {

    private static final Color BG           = new Color(170, 190, 230);
    private static final Color TITLE_SHADOW = new Color(80, 80, 80);
    private static final Color BTN_RED      = new Color(180, 20, 20);

    public MainMenuView() {
        setTitle("The DOPO Hardest Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(BorderFactory.createLineBorder(new Color(100, 120, 170), 3));

        root.add(buildTopBar(),  BorderLayout.NORTH);
        root.add(buildTitle(),   BorderLayout.CENTER);
        root.add(buildButtons(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG);
        bar.setBorder(BorderFactory.createEmptyBorder(10, 14, 0, 14));

        JLabel left = new JLabel("THE DOPO'S...");
        left.setFont(new Font("Arial", Font.BOLD, 13));
        left.setForeground(new Color(40, 40, 90));

        JLabel right = new JLabel("v1.0");
        right.setFont(new Font("Arial", Font.BOLD, 12));
        right.setForeground(new Color(40, 40, 90));

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

                g2.setFont(new Font("Arial Black", Font.BOLD, 52));
                FontMetrics fm = g2.getFontMetrics();
                String line = "HARDEST GAME";
                int x = (w - fm.stringWidth(line)) / 2;
                int y = h / 2 + 18;

                // Sombra
                g2.setColor(TITLE_SHADOW);
                g2.drawString(line, x + 3, y + 3);

                // Título rojo
                g2.setColor(new Color(200, 20, 20));
                g2.drawString(line, x, y);
            }
        };
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(500, 100));
        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 20));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 26, 20));

        panel.add(buildArcadeButton("PLAY\nGAME", BTN_RED, true,
                e -> openModes()));

        return panel;
    }

    private JPanel buildArcadeButton(String label, Color baseColor,
                                      boolean enabled, ActionListener action) {
        String[] lines = label.split("\n");

        JPanel btn = new JPanel() {
            private boolean hovered = false;
            {
                setOpaque(false);
                setPreferredSize(new Dimension(108, 58));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                    @Override public void mouseClicked(MouseEvent e) {
                        action.actionPerformed(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();

                Color bg = hovered ? baseColor.brighter() : baseColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, w, h, 8, 8);
                g2.setColor(bg.darker());
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, w - 2, h - 2, 8, 8);
                g2.setStroke(new BasicStroke(1f));

                g2.setFont(new Font("Arial Black", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int lineH = fm.getHeight();
                int startY = (h - lineH * lines.length) / 2 + fm.getAscent();
                g2.setColor(Color.WHITE);
                for (int i = 0; i < lines.length; i++) {
                    g2.drawString(lines[i], (w - fm.stringWidth(lines[i])) / 2, startY + i * lineH);
                }
            }
        };
        return btn;
    }

    private void openModes() {
        JOptionPane.showMessageDialog(this, "Próximamente: modos de juego");
    }
}