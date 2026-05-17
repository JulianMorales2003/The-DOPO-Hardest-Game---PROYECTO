package domain;

import domain.levels.Level;
import domain.levels.Level1;
import domain.levels.Level2;
import domain.levels.Level3;
import exceptions.DopoGameException;
import java.awt.Rectangle;
import java.util.List;

/**
 * Fachada del dominio del juego.
 * Centraliza el acceso a todas las entidades y operaciones
 * del juego para simplificar la interacción con la presentación.
 */
public class GameFacade {

    private Player player;
    private Level nivelActual;
    private int numeroNivel;
    private int deaths;
    private boolean nivelCompleto;
    private boolean pausado;

    /**
     * Crea la fachada e inicializa el estado del juego.
     *
     * @param playerName nombre del jugador
     * @param spawnX     posición inicial X del jugador
     * @param spawnY     posición inicial Y del jugador
     */
    public GameFacade(String playerName, int spawnX, int spawnY) {
        if (playerName == null || playerName.isBlank()) {
            throw new DopoGameException(
                DopoGameException.ErrorCode.NOMBRE_JUGADOR_INVALIDO,
                "El nombre del jugador no puede estar vacío"
            );
        }
        this.player        = new RedPlayer(playerName, spawnX, spawnY);
        this.numeroNivel   = 1;
        this.deaths        = 0;
        this.nivelCompleto = false;
        this.pausado       = false;
        cargarNivel(numeroNivel);
    }

    // ── Movimiento ────────────────────────────────────────────────────────────

    /**
     * Mueve el jugador hacia arriba dentro de los límites del mapa.
     *
     * @param mapH alto del mapa en píxeles
     */
    public void moverArriba(int mapH) {
        int oldX = player.x;
        int oldY = player.y;
        player.y = Math.max(0, player.y - (int) player.speed);
        if (colisionaConPared()) {
            player.x = oldX;
            player.y = oldY;
        }
    }

    /**
     * Mueve el jugador hacia abajo dentro de los límites del mapa.
     *
     * @param mapH alto del mapa en píxeles
     */
    public void moverAbajo(int mapH) {
        int oldX = player.x;
        int oldY = player.y;
        player.y = Math.min(mapH - player.size, player.y + (int) player.speed);
        if (colisionaConPared()) {
            player.x = oldX;
            player.y = oldY;
        }
    }

    /**
     * Mueve el jugador hacia la izquierda dentro de los límites del mapa.
     *
     * @param mapW ancho del mapa en píxeles
     */
    public void moverIzquierda(int mapW) {
        int oldX = player.x;
        int oldY = player.y;
        player.x = Math.max(0, player.x - (int) player.speed);
        if (colisionaConPared()) {
            player.x = oldX;
            player.y = oldY;
        }
    }

    /**
     * Mueve el jugador hacia la derecha dentro de los límites del mapa.
     *
     * @param mapW ancho del mapa en píxeles
     */
    public void moverDerecha(int mapW) {
        int oldX = player.x;
        int oldY = player.y;
        player.x = Math.min(mapW - player.size, player.x + (int) player.speed);
        if (colisionaConPared()) {
            player.x = oldX;
            player.y = oldY;
        }
    }

    // ── Lógica del juego ──────────────────────────────────────────────────────

    /**
     * Actualiza la posición de todos los enemigos del nivel actual.
     *
     * @param mapW ancho del mapa en píxeles
     * @param mapH alto del mapa en píxeles
     */
    public void actualizarEnemigos(int mapW, int mapH) {
        for (BasicEnemy e : nivelActual.getEnemies()) {
            e.update(mapW, mapH);
        }
    }

    /**
     * Verifica colisiones del jugador con enemigos y monedas.
     * Si toca un enemigo reinicia el nivel.
     * Si toca una moneda la marca como recolectada.
     *
     * @return true si el jugador murió en esta verificación
     */
    public boolean verificarColisiones() {
        Rectangle pb = getPlayerBounds();

        for (BasicEnemy e : nivelActual.getEnemies()) {
            if (getEnemyBounds(e).intersects(pb)) {
                deaths++;
                reiniciarNivel();
                return true;
            }
        }

        for (Coin c : nivelActual.getCoins()) {
            if (!c.collected && getCoinBounds(c).intersects(pb)) {
                c.collected = true;
                player.coinsCollected++;
            }
        }

        return false;
    }

    /**
     * Verifica si el jugador completó el nivel actual.
     * Se completa cuando todas las monedas están recolectadas
     * y el jugador llega a la zona final.
     *
     * @param zonaFinal rectángulo de la zona final del mapa
     * @return true si el nivel fue completado
     */
    public boolean verificarNivelCompleto(Rectangle zonaFinal) {
        if (!nivelActual.todasLasMonedasRecolectadas()) return false;

        if (zonaFinal.intersects(getPlayerBounds())) {
            nivelCompleto = true;
            return true;
        }
        return false;
    }

    /**
     * Avanza al siguiente nivel si existe.
     *
     * @return true si había un siguiente nivel, false si era el último
     */
    public boolean siguienteNivel() {
        if (numeroNivel >= 3) return false;
        numeroNivel++;
        nivelCompleto = false;
        deaths        = 0;
        cargarNivel(numeroNivel);
        player.x = player.spawnX;
        player.y = player.spawnY;
        return true;
    }

    /**
     * Alterna entre pausado y en juego.
     */
    public void togglePausa() {
        pausado = !pausado;
    }

    // ── Métodos privados ──────────────────────────────────────────────────────

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
    }

    /**
     * Reinicia el nivel actual y regresa el jugador al spawn.
     */
    private void reiniciarNivel() {
        nivelActual.reiniciar();
        player.x = player.spawnX;
        player.y = player.spawnY;
    }

    /**
     * Verifica si el jugador colisiona con alguna pared.
     *
     * @return true si hay colisión con alguna pared
     */
    private boolean colisionaConPared() {
        Rectangle pb = getPlayerBounds();
        for (Wall w : nivelActual.getWalls()) {
            if (w.collidesWith(pb)) return true;
        }
        return false;
    }

    /**
     * Retorna los límites del jugador como rectángulo.
     *
     * @return rectángulo con posición y tamaño del jugador
     */
    private Rectangle getPlayerBounds() {
        return new Rectangle(player.x, player.y, player.size, player.size);
    }

    /**
     * Retorna los límites de un enemigo como rectángulo.
     *
     * @param e enemigo del que obtener los límites
     * @return rectángulo con posición y tamaño del enemigo
     */
    private Rectangle getEnemyBounds(BasicEnemy e) {
        return new Rectangle(e.x, e.y, e.size, e.size);
    }

    /**
     * Retorna los límites de una moneda como rectángulo.
     *
     * @param c moneda de la que obtener los límites
     * @return rectángulo con posición y tamaño de la moneda
     */
    private Rectangle getCoinBounds(Coin c) {
        return new Rectangle(c.x, c.y, c.size, c.size);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /**
     * Retorna el jugador actual.
     *
     * @return jugador
     */
    public Player getPlayer() { return player; }

    /**
     * Retorna la lista de enemigos del nivel actual.
     *
     * @return lista de enemigos
     */
    public List<BasicEnemy> getEnemies() { return nivelActual.getEnemies(); }

    /**
     * Retorna la lista de monedas del nivel actual.
     *
     * @return lista de monedas
     */
    public List<Coin> getCoins() { return nivelActual.getCoins(); }

    /**
     * Retorna la lista de paredes del nivel actual.
     *
     * @return lista de paredes
     */
    public List<Wall> getWalls() { return nivelActual.getWalls(); }

    /**
     * Retorna el nombre del nivel actual.
     *
     * @return nombre del nivel
     */
    public String getNombreNivel() { return nivelActual.getNombre(); }

    /**
     * Retorna el número del nivel actual.
     *
     * @return número del nivel
     */
    public int getNumeroNivel() { return numeroNivel; }

    /**
     * Retorna el número de muertes del jugador.
     *
     * @return muertes
     */
    public int getDeaths() { return deaths; }

    /**
     * Retorna cuántas monedas faltan por recolectar.
     *
     * @return monedas restantes
     */
    public long getMonedasRestantes() {
        return nivelActual.getCoins().stream()
                .filter(c -> !c.collected).count();
    }

    /**
     * Indica si el nivel actual está completado.
     *
     * @return true si el nivel está completado
     */
    public boolean isNivelCompleto() { return nivelCompleto; }

    /**
     * Indica si hay un nivel siguiente disponible.
     *
     * @return true si existe un nivel siguiente
     */
    public boolean hayNivelSiguiente() { return numeroNivel < 3; }

    /**
     * Indica si el juego está pausado.
     *
     * @return true si está pausado
     */
    public boolean isPausado() { return pausado; }
}