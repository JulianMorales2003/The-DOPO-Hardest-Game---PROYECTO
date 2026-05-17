package presentation;

import domain.GameFacade;
import domain.BasicEnemy;
import domain.Coin;
import domain.Wall;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView extends JFrame implements KeyListener {

    // Dimensiones del tablero
    private static final int MAP_W  = 800;
    private static final int MAP_H  = 520;
    private static final int CELL   = 40;
    private static final int HUD_H  = 50;
    private static final int SAFE_W = 60;
    private static final int SAFE_Y = MAP_H / 2 - 60;
    private static final int SAFE_H = 120;

    // Colores
    private static final Color BG        = new Color(204, 204, 204);
    private static final Color GRID      = new Color(170, 170, 170);
    private static final Color BORDER    = new Color(0, 0, 128);
    private static final Color SAFE_FILL = new Color(0, 190, 100, 160);
    private static final Color SAFE_LINE = new Color(0, 140, 60);
    private static final Color HUD_BG    = new Color(230, 230, 240);
    private static final Color HUD_TEXT  = new Color(20, 20, 20);
    private static final Color WALL_COLOR = new Color(80, 80, 100);

    // Fachada — único punto de contacto con el dominio
    private final GameFacade game;

    // Estado de teclas
    private boolean up, down, left, right;

    // Zona final precalculada
    private final Rectangle zonaFinal = new Rectangle(
            MAP_W - SAFE_W, SAFE_Y, SAFE_W, SAFE_H);

    private final JFrame previous;
    private Timer gameLoop;

    /**
     * Crea la ventana del juego usando la fachada como único acceso al dominio.
     *
     * @param previous   ventana anterior para regresar
     * @param playerName nombre del jugador
     */
    public GameView(JFrame previous, String playerName) {
        this.previous = previous;

        // La fachada valida el nombre y lanza DopoGameException si es inválido
        game = new GameFacade(playerName, 15, MAP_H / 2 - 15);

        setTitle("The DOPO Hardest Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                drawHUD(g2);
                g2.translate(0, HUD_H);
                drawGrid(g2);
                drawWalls(g2);
                drawSafeZones(g2);
                drawCoins(g2);
                drawEnemies(g2);
                drawPlayer(g2);
                drawBorder(g2);
                g2.translate(0, -HUD_H);
                if (game.isNivelCompleto()) drawNivelCompleto(g2);
            }
        };

        panel.setPreferredSize(new Dimension(MAP_W, MAP_H + HUD_H));
        panel.setBackground(BG);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        addKeyListener(this);

        gameLoop = new Timer(16, e -> {
            if (!game.isNivelCompleto()) {
                handleMovement();
                game.actualizarEnemigos(MAP_W, MAP_H);
                game.verificarColisiones();
                game.verificarNivelCompleto(zonaFinal);
            }
            panel.repaint();
        });
        gameLoop.start();

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // ── Movimiento ────────────────────────────────────────────────────────────

    /**
     * Delega el movimiento del jugador a la fachada según las teclas presionadas.
     */
    private void handleMovement() {
        if (up)    game.moverArriba(MAP_H);
        if (down)  game.moverAbajo(MAP_H);
        if (left)  game.moverIzquierda(MAP_W);
        if (right) game.moverDerecha(MAP_W);
    }

    // ── Dibujo ────────────────────────────────────────────────────────────────

    /**
     * Dibuja el HUD con información del juego obtenida desde la fachada.
     *
     * @param g contexto gráfico
     */
    private void drawHUD(Graphics2D g) {
        g.setColor(HUD_BG);
        g.fillRect(0, 0, MAP_W, HUD_H);
        g.setColor(BORDER);
        g.fillRect(0, HUD_H - 2, MAP_W, 2);

        // Cuadradito del jugador
        g.setColor(game.getPlayer().color);
        g.fillRect(12, 15, 18, 18);
        g.setColor(Color.BLACK);
        g.drawRect(12, 15, 18, 18);

        // Nombre y muertes
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(HUD_TEXT);
        g.drawString(game.getPlayer().name, 38, 28);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(80, 80, 100));
        g.drawString("Muertes: " + game.getDeaths(), 38, 42);

        // Nivel actual
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(HUD_TEXT);
        g.drawString("Nivel " + game.getNumeroNivel() + ": " + game.getNombreNivel(),
                MAP_W / 2 - 80, 28);

        // Monedas
        long left = game.getMonedasRestantes();
        g.setColor(Color.BLACK);
        g.fillOval(MAP_W / 2 - 62, 33, 18, 18);
        g.setColor(new Color(255, 215, 0));
        g.fillOval(MAP_W / 2 - 60, 35, 14, 14);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.setColor(left == 0 ? new Color(0, 160, 60) : HUD_TEXT);
        g.drawString(left == 0 ? "¡Ve a la meta!" : left + " monedas",
                MAP_W / 2 - 42, 46);

        // Controles
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(80, 80, 100));
        g.drawString("[ESC] menú", MAP_W - 100, 30);
    }

    /**
     * Dibuja el grid del tablero.
     *
     * @param g contexto gráfico
     */
    private void drawGrid(Graphics2D g) {
        g.setColor(BG);
        g.fillRect(0, 0, MAP_W, MAP_H);
        g.setColor(GRID);
        g.setStroke(new BasicStroke(0.5f));
        for (int x = 0; x <= MAP_W; x += CELL) g.drawLine(x, 0, x, MAP_H);
        for (int y = 0; y <= MAP_H; y += CELL) g.drawLine(0, y, MAP_W, y);
        g.setStroke(new BasicStroke(1f));
    }

    /**
     * Dibuja las paredes del nivel obtenidas desde la fachada.
     *
     * @param g contexto gráfico
     */
    private void drawWalls(Graphics2D g) {
        for (Wall w : game.getWalls()) {
            g.setColor(WALL_COLOR);
            g.fillRect(w.x, w.y, w.width, w.height);
            g.setColor(new Color(50, 50, 70));
            g.setStroke(new BasicStroke(1.5f));
            g.drawRect(w.x, w.y, w.width, w.height);
            g.setStroke(new BasicStroke(1f));
        }
    }

    /**
     * Dibuja las zonas seguras de inicio y fin.
     *
     * @param g contexto gráfico
     */
    private void drawSafeZones(Graphics2D g) {
        // Zona inicio
        g.setColor(SAFE_FILL);
        g.fillRect(0, SAFE_Y, SAFE_W, SAFE_H);
        g.setColor(SAFE_LINE);
        g.setStroke(new BasicStroke(2f));
        g.drawRect(0, SAFE_Y, SAFE_W, SAFE_H);
        g.setStroke(new BasicStroke(1f));
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(0, 80, 30));
        g.drawString("START", 8, MAP_H / 2 + 5);

        // Zona final
        g.setColor(SAFE_FILL);
        g.fillRect(MAP_W - SAFE_W, SAFE_Y, SAFE_W, SAFE_H);
        g.setColor(SAFE_LINE);
        g.setStroke(new BasicStroke(2f));
        g.drawRect(MAP_W - SAFE_W, SAFE_Y, SAFE_W, SAFE_H);
        g.setStroke(new BasicStroke(1f));
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setColor(new Color(0, 80, 30));
        g.drawString("END", MAP_W - 45, MAP_H / 2 + 5);
    }

    /**
     * Dibuja las monedas no recolectadas obtenidas desde la fachada.
     *
     * @param g contexto gráfico
     */
    private void drawCoins(Graphics2D g) {
        for (Coin c : game.getCoins()) {
            if (c.collected) continue;
            g.setColor(Color.BLACK);
            g.fillOval(c.x - 3, c.y - 3, c.size + 6, c.size + 6);
            g.setColor(new Color(255, 215, 0));
            g.fillOval(c.x, c.y, c.size, c.size);
        }
    }

    /**
     * Dibuja los enemigos obtenidos desde la fachada.
     *
     * @param g contexto gráfico
     */
    private void drawEnemies(Graphics2D g) {
        for (BasicEnemy e : game.getEnemies()) {
            g.setColor(Color.BLACK);
            g.fillOval(e.x - 4, e.y - 4, e.size + 8, e.size + 8);
            g.setColor(new Color(30, 100, 220));
            g.fillOval(e.x, e.y, e.size, e.size);
        }
    }

    /**
     * Dibuja el jugador obtenido desde la fachada.
     *
     * @param g contexto gráfico
     */
    private void drawPlayer(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(game.getPlayer().x - 4, game.getPlayer().y - 4,
                game.getPlayer().size + 8, game.getPlayer().size + 8);
        g.setColor(game.getPlayer().color);
        g.fillRect(game.getPlayer().x, game.getPlayer().y,
                game.getPlayer().size, game.getPlayer().size);
    }

    /**
     * Dibuja el borde del tablero.
     *
     * @param g contexto gráfico
     */
    private void drawBorder(Graphics2D g) {
        g.setColor(BORDER);
        g.setStroke(new BasicStroke(3f));
        g.drawRect(0, 0, MAP_W - 1, MAP_H - 1);
        g.setStroke(new BasicStroke(1f));
    }

    /**
     * Dibuja la pantalla de nivel completado con estadísticas.
     *
     * @param g contexto gráfico
     */
    private void drawNivelCompleto(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, MAP_W, MAP_H + HUD_H);

        int bx = MAP_W / 2 - 200;
        int by = MAP_H / 2 - 80;
        g.setColor(new Color(230, 245, 230));
        g.fillRoundRect(bx, by, 400, 220, 20, 20);
        g.setColor(new Color(0, 140, 60));
        g.setStroke(new BasicStroke(3f));
        g.drawRoundRect(bx, by, 400, 220, 20, 20);
        g.setStroke(new BasicStroke(1f));

        g.setFont(new Font("Arial Black", Font.BOLD, 26));
        FontMetrics fm = g.getFontMetrics();
        String titulo = "¡NIVEL COMPLETADO!";
        g.setColor(new Color(0, 120, 40));
        g.drawString(titulo, MAP_W / 2 - fm.stringWidth(titulo) / 2, by + 55);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(new Color(40, 40, 40));
        g.drawString("Jugador: " + game.getPlayer().name,  MAP_W / 2 - 80, by + 90);
        g.drawString("Nivel:   " + game.getNombreNivel(),  MAP_W / 2 - 80, by + 112);
        g.drawString("Muertes: " + game.getDeaths(),       MAP_W / 2 - 80, by + 134);

        if (game.hayNivelSiguiente()) {
            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.setColor(new Color(0, 100, 180));
            String sig = "Presiona ENTER para el nivel " + (game.getNumeroNivel() + 1);
            fm = g.getFontMetrics();
            g.drawString(sig, MAP_W / 2 - fm.stringWidth(sig) / 2, by + 170);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(new Color(80, 80, 80));
        String esc = "Presiona ESC para volver al menú";
        fm = g.getFontMetrics();
        g.drawString(esc, MAP_W / 2 - fm.stringWidth(esc) / 2, by + 195);
    }

    // ── KeyListener ───────────────────────────────────────────────────────────

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)    up    = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  down  = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  left  = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) exitToMenu();

        if (e.getKeyCode() == KeyEvent.VK_ENTER && game.isNivelCompleto()) {
            if (game.hayNivelSiguiente()) {
                game.siguienteNivel();
            } else {
                exitToMenu();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)    up    = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  down  = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  left  = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }

    @Override public void keyTyped(KeyEvent e) {}

    /**
     * Detiene el game loop, cierra la ventana y regresa al menú anterior.
     */
    private void exitToMenu() {
        gameLoop.stop();
        dispose();
        previous.setVisible(true);
    }
}