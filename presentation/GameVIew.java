package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame implements KeyListener {

    // Dimensiones del tablero
    private static final int MAP_W  = 800;
    private static final int MAP_H  = 520;
    private static final int CELL   = 40;
    private static final int HUD_H  = 50;

    // Colores fiel al original
    private static final Color BG        = new Color(204, 204, 204);
    private static final Color GRID      = new Color(170, 170, 170);
    private static final Color BORDER    = new Color(0, 0, 128);
    private static final Color SAFE_FILL = new Color(0, 190, 100, 160);
    private static final Color SAFE_LINE = new Color(0, 140, 60);
    private static final Color HUD_BG    = new Color(230, 230, 240);
    private static final Color HUD_TEXT  = new Color(20, 20, 20);

    // Entidades
    private RedPlayer player;
    private List<BasicEnemy> enemies;
    private List<Coin> coins;

    // Estado
    private int deaths = 0;
    private final JFrame previous;
    private Timer gameLoop;

    // Teclas presionadas
    private boolean up, down, left, right;

    public GameView(JFrame previous, String playerName) {
        this.previous = previous;

        setTitle("The DOPO Hardest Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        // Inicializar entidades
        player  = new RedPlayer(playerName, 20, MAP_H / 2 - 15);
        enemies = new ArrayList<>();
        enemies.add(new BasicEnemy(300, 200,  1,  0));
        enemies.add(new BasicEnemy(500,  80,  0,  1));
        enemies.add(new BasicEnemy(200, 350, -1,  1));
        coins = new ArrayList<>();
        coins.add(new Coin(180, 140));
        coins.add(new Coin(320, 240));
        coins.add(new Coin(460, 140));
        coins.add(new Coin(580, 300));
        coins.add(new Coin(670, 190));

        // Panel de juego
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                drawHUD(g2);
                g2.translate(0, HUD_H);
                drawGrid(g2);
                drawSafeZones(g2);
                drawCoins(g2);
                drawEnemies(g2);
                drawPlayer(g2);
                drawBorder(g2);
                g2.translate(0, -HUD_H);
            }
        };
        panel.setPreferredSize(new Dimension(MAP_W, MAP_H + HUD_H));
        panel.setBackground(BG);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        addKeyListener(this);

        // Game loop 60fps
        gameLoop = new Timer(16, e -> {
            handleMovement();
            for (BasicEnemy en : enemies) en.update(MAP_W, MAP_H);
            checkCollisions();
            panel.repaint();
        });
        gameLoop.start();

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                exitToMenu();
            }
        });
    }

    // ── Dibujo ────────────────────────────────────────────────────────────────

    private void drawHUD(Graphics2D g) {
        g.setColor(HUD_BG);
        g.fillRect(0, 0, MAP_W, HUD_H);
        g.setColor(BORDER);
        g.fillRect(0, HUD_H - 2, MAP_W, 2);

        // Cuadradito del jugador
        g.setColor(player.color);
        g.fillRect(12, 15, 18, 18);
        g.setColor(Color.BLACK);
        g.drawRect(12, 15, 18, 18);

        // Nombre y muertes
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(HUD_TEXT);
        g.drawString(player.name, 38, 28);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(80, 80, 100));
        g.drawString("Muertes: " + deaths, 38, 42);

        // Centro: monedas
        long left = coins.stream().filter(c -> !c.collected).count();
        g.setColor(new Color(255, 215, 0));
        g.fillOval(MAP_W / 2 - 60, 18, 14, 14));
        g.setColor(Color.BLACK);
        g.drawOval(MAP_W / 2 - 60, 18, 14, 14);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(left == 0 ? new Color(0, 160, 60) : HUD_TEXT);
        g.drawString(left + " monedas", MAP_W / 2 - 42, 30);

        // Controles
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(80, 80, 100));
        g.drawString("[P] pausa   [ESC] menú", MAP_W - 160, 30);
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(BG);
        g.fillRect(0, 0, MAP_W, MAP_H);
        g.setColor(GRID);
        g.setStroke(new BasicStroke(0.5f));
        for (int x = 0; x <= MAP_W; x += CELL) g.drawLine(x, 0, x, MAP_H);
        for (int y = 0; y <= MAP_H; y += CELL) g.drawLine(0, y, MAP_W, y);
        g.setStroke(new BasicStroke(1f));
    }

    private void drawSafeZones(Graphics2D g) {
        // Zona inicio izquierda
        g.setColor(SAFE_FILL);
        g.fillRect(0, MAP_H / 2 - 60, 60, 120);
        g.setColor(SAFE_LINE);
        g.setStroke(new BasicStroke(2f));
        g.drawRect(0, MAP_H / 2 - 60, 60, 120);
        g.setStroke(new BasicStroke(1f));
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(0, 80, 30));
        g.drawString("START", 10, MAP_H / 2 + 5);

        // Zona final derecha
        g.setColor(SAFE_FILL);
        g.fillRect(MAP_W - 60, MAP_H / 2 - 60, 60, 120);
        g.setColor(SAFE_LINE);
        g.setStroke(new BasicStroke(2f));
        g.drawRect(MAP_W - 60, MAP_H / 2 - 60, 60, 120);
        g.setStroke(new BasicStroke(1f));
        g.drawString("END", MAP_W - 45, MAP_H / 2 + 5);
    }

    private void drawCoins(Graphics2D g) {
        for (Coin c : coins) {
            if (c.collected) continue;
            // Borde negro
            g.setColor(Color.BLACK);
            g.fillOval(c.x - 3, c.y - 3, c.size + 6, c.size + 6);
            // Relleno amarillo
            g.setColor(new Color(255, 215, 0));
            g.fillOval(c.x, c.y, c.size, c.size);
        }
    }

    private void drawEnemies(Graphics2D g) {
        for (BasicEnemy e : enemies) {
            // Borde negro
            g.setColor(Color.BLACK);
            g.fillOval(e.x - 4, e.y - 4, e.size + 8, e.size + 8);
            // Relleno azul
            g.setColor(new Color(30, 100, 220));
            g.fillOval(e.x, e.y, e.size, e.size);
        }
    }

    private void drawPlayer(Graphics2D g) {
        // Borde negro
        g.setColor(Color.BLACK);
        g.fillRect(player.x - 4, player.y - 4, player.size + 8, player.size + 8);
        // Cuadrado rojo
        g.setColor(player.color);
        g.fillRect(player.x, player.y, player.size, player.size);
    }

    private void drawBorder(Graphics2D g) {
        g.setColor(BORDER);
        g.setStroke(new BasicStroke(3f));
        g.drawRect(0, 0, MAP_W - 1, MAP_H - 1);
        g.setStroke(new BasicStroke(1f));
    }

    // ── Lógica ────────────────────────────────────────────────────────────────

    private void handleMovement() {
        if (up)    player.y = Math.max(0, player.y - (int) player.speed);
        if (down)  player.y = Math.min(MAP_H - player.size, player.y + (int) player.speed);
        if (left)  player.x = Math.max(0, player.x - (int) player.speed);
        if (right) player.x = Math.min(MAP_W - player.size, player.x + (int) player.speed);
    }

    private void checkCollisions() {
        Rectangle pb = new Rectangle(player.x, player.y, player.size, player.size);

        // Enemigos → muerte y reset
        for (BasicEnemy e : enemies) {
            if (new Rectangle(e.x, e.y, e.size, e.size).intersects(pb)) {
                deaths++;
                player.x = player.spawnX;
                player.y = player.spawnY;
                resetEnemies();
                resetCoins();
                return;
            }
        }

        // Monedas
        for (Coin c : coins) {
            if (!c.collected && new Rectangle(c.x, c.y, c.size, c.size).intersects(pb)) {
                c.collected = true;
            }
        }
    }

    private void resetEnemies() {
        enemies.clear();
        enemies.add(new BasicEnemy(300, 200,  1,  0));
        enemies.add(new BasicEnemy(500,  80,  0,  1));
        enemies.add(new BasicEnemy(200, 350, -1,  1));
    }

    private void resetCoins() {
        for (Coin c : coins) c.collected = false;
    }

    private void exitToMenu() {
        gameLoop.stop();
        dispose();
        previous.setVisible(true);
    }

    // ── KeyListener ───────────────────────────────────────────────────────────

    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)    up    = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  down  = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  left  = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) exitToMenu();
    }

    @Override public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)    up    = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  down  = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  left  = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
}