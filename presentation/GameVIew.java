package presentation;

import domain.*;
import domain.levels.Level;
import domain.levels.Level1;
import domain.levels.Level2;
import domain.levels.Level3;
import exceptions.DopoGameException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameView extends JFrame implements KeyListener {

    // Dimensiones del tablero
    private static final int MAP_W = 800;
    private static final int MAP_H = 520;
    private static final int CELL  = 40;
    private static final int HUD_H = 50;

    // Zona segura
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

    // Entidades
    private RedPlayer player;
    private List<BasicEnemy> enemies;
    private List<Coin> coins;

    // Niveles
    private Level nivelActual;
    private int numeroNivel = 1;

    // Estado
    private int deaths = 0;
    private boolean nivelCompleto = false;
    private final JFrame previous;
    private Timer gameLoop;

    // Teclas
    private boolean up, down, left, right;

    /**
     * Crea la ventana del juego con el jugador y nivel inicializados.
     *
     * @param previous   ventana anterior para poder regresar
     * @param playerName nombre del jugador ingresado en el menú
     */
    public GameView(JFrame previous, String playerName) {
        this.previous = previous;

        if (playerName == null || playerName.isBlank()) {
            throw new DopoGameException(
                DopoGameException.ErrorCode.NOMBRE_JUGADOR_INVALIDO,
                "El nombre del jugador no puede estar vacío"
            );
        }

        setTitle("The DOPO Hardest Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        inicializarEntidades(playerName);

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
                drawSafeZones(g2);
                drawCoins(g2);
                drawEnemies(g2);
                drawPlayer(g2);
                drawBorder(g2);
                g2.translate(0, -HUD_H);
                if (nivelCompleto) drawNivelCompleto(g2);
            }
        };

        panel.setPreferredSize(new Dimension(MAP_W, MAP_H + HUD_H));
        panel.setBackground(BG);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        addKeyListener(this);

        gameLoop = new Timer(16, e -> {
            if (!nivelCompleto) {
                handleMovement();
                for (BasicEnemy en : enemies) en.update(MAP_W, MAP_H);
                checkCollisions();
                checkNivelCompleto();
            }
            panel.repaint();
        });
        gameLoop.start();

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                exitToMenu();
            }
        });
    }

    /**
     * Inicializa el jugador y carga el nivel indicado.
     *
     * @param playerName nombre del jugador
     */
    private void inicializarEntidades(String playerName) {
        player = new RedPlayer(playerName, 15, MAP_H / 2 - 15);
        cargarNivel(numeroNivel);
    }

    /**
     * Carga el nivel correspondiente al número indicado.
     *
     * @param numero número del nivel a cargar
     */
    private void cargarNivel(int numero) {
        switch (numero) {
            case 1 -> nivelActual = new Level1();
            case 2 -> nivelActual = new Level2();
            case 3 -> nivelActual = new Level3();
            default -> throw new DopoGameException(
                DopoGameException.ErrorCode.NIVEL_NO_ENCONTRADO,
                "El nivel " + numero + " no existe"
            );
        }
        enemies = nivelActual.getEnemies();
        coins   = nivelActual.getCoins();
    }

    // ── Dibujo ────────────────────────────────────────────────────────────────

    /**
     * Dibuja el HUD superior con nombre, muertes y monedas restantes.
     *
     * @param g contexto gráfico
     */
    private void drawHUD(Graphics2D g) {
        g.setColor(HUD_BG);
        g.fillRect(0, 0, MAP_W, HUD_H);
        g.setColor(BORDER);
        g.fillRect(0, HUD_H - 2, MAP_W, 2);

        // Cuadradito jugador
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

        // Nivel actual
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(HUD_TEXT);
        g.drawString("Nivel " + numeroNivel + ": " + nivelActual.getNombre(),
            MAP_W / 2 - 80, 28);

        // Monedas
        long left = coins.stream().filter(c -> !c.collected).count();
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
     * Dibuja las monedas no recolectadas.
     *
     * @param g contexto gráfico
     */
    private void drawCoins(Graphics2D g) {
        for (Coin c : coins) {
            if (c.collected) continue;
            g.setColor(Color.BLACK);
            g.fillOval(c.x - 3, c.y - 3, c.size + 6, c.size + 6);
            g.setColor(new Color(255, 215, 0));
            g.fillOval(c.x, c.y, c.size, c.size);
        }
    }

    /**
     * Dibuja los enemigos en el tablero.
     *
     * @param g contexto gráfico
     */
    private void drawEnemies(Graphics2D g) {
        for (BasicEnemy e : enemies) {
            g.setColor(Color.BLACK);
            g.fillOval(e.x - 4, e.y - 4, e.size + 8, e.size + 8);
            g.setColor(new Color(30, 100, 220));
            g.fillOval(e.x, e.y, e.size, e.size);
        }
    }

    /**
     * Dibuja el jugador en el tablero.
     *
     * @param g contexto gráfico
     */
    private void drawPlayer(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(player.x - 4, player.y - 4, player.size + 8, player.size + 8);
        g.setColor(player.color);
        g.fillRect(player.x, player.y, player.size, player.size);
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
     * Dibuja la pantalla de nivel completado sobre el tablero.
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
        g.drawString("Jugador: " + player.name,   MAP_W / 2 - 80, by + 90);
        g.drawString("Nivel:   " + nivelActual.getNombre(), MAP_W / 2 - 80, by + 112);
        g.drawString("Muertes: " + deaths,         MAP_W / 2 - 80, by + 134);

        if (numeroNivel < 3) {
            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.setColor(new Color(0, 100, 180));
            String sig = "Presiona ENTER para el nivel " + (numeroNivel + 1);
            fm = g.getFontMetrics();
            g.drawString(sig, MAP_W / 2 - fm.stringWidth(sig) / 2, by + 170);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(new Color(80, 80, 80));
        String esc = "Presiona ESC para volver al menú";
        fm = g.getFontMetrics();
        g.drawString(esc, MAP_W / 2 - fm.stringWidth(esc) / 2, by + 195);
    }

    // ── Lógica ────────────────────────────────────────────────────────────────

    /**
     * Mueve el jugador según las teclas presionadas,
     * manteniéndolo dentro de los límites del tablero.
     */
    private void handleMovement() {
        if (up)    player.y = Math.max(0, player.y - (int) player.speed);
        if (down)  player.y = Math.min(MAP_H - player.size, player.y + (int) player.speed);
        if (left)  player.x = Math.max(0, player.x - (int) player.speed);
        if (right) player.x = Math.min(MAP_W - player.size, player.x + (int) player.speed);
    }

    /**
     * Revisa si el jugador tocó un enemigo o recolectó una moneda.
     * Al tocar un enemigo reinicia el nivel actual.
     */
    private void checkCollisions() {
        Rectangle pb = new Rectangle(player.x, player.y, player.size, player.size);

        for (BasicEnemy e : enemies) {
            if (new Rectangle(e.x, e.y, e.size, e.size).intersects(pb)) {
                deaths++;
                resetNivel();
                return;
            }
        }

        for (Coin c : coins) {
            if (!c.collected && new Rectangle(c.x, c.y, c.size, c.size).intersects(pb)) {
                c.collected = true;
            }
        }
    }

    /**
     * Verifica si el jugador llegó a la zona final con todas las monedas.
     * Si se cumple marca el nivel como completado.
     */
    private void checkNivelCompleto() {
        if (!nivelActual.todasLasMonedasRecolectadas()) return;

        Rectangle pb        = new Rectangle(player.x, player.y, player.size, player.size);
        Rectangle zonaFinal = new Rectangle(MAP_W - SAFE_W, SAFE_Y, SAFE_W, SAFE_H);

        if (zonaFinal.intersects(pb)) {
            nivelCompleto = true;
            gameLoop.stop();
        }
    }

    /**
     * Reinicia el nivel actual a su estado original
     * y regresa el jugador al spawn.
     */
    private void resetNivel() {
        nivelActual.reiniciar();
        enemies  = nivelActual.getEnemies();
        coins    = nivelActual.getCoins();
        player.x = player.spawnX;
        player.y = player.spawnY;
    }

    /**
     * Avanza al siguiente nivel si existe.
     */
    private void siguienteNivel() {
        numeroNivel++;
        nivelCompleto = false;
        deaths        = 0;
        cargarNivel(numeroNivel);
        player.x = player.spawnX;
        player.y = player.spawnY;
        gameLoop.start();
    }

    /**
     * Detiene el game loop, cierra la ventana y regresa al menú anterior.
     */
    private void exitToMenu() {
        gameLoop.stop();
        dispose();
        previous.setVisible(true);
    }

    // ── KeyListener ───────────────────────────────────────────────────────────

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)    up    = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  down  = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  left  = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) exitToMenu();

        if (e.getKeyCode() == KeyEvent.VK_ENTER && nivelCompleto) {
            if (numeroNivel < 3) {
                siguienteNivel();
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
}