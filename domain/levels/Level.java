package domain.levels;

import domain.BasicEnemy;
import domain.Coin;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase base que representa un nivel del juego.
 * Cada nivel concreto extiende esta clase y define
 * sus propios enemigos y monedas.
 */
public abstract class Level {

    protected List<BasicEnemy> enemies;
    protected List<Coin> coins;
    protected int numero;
    protected String nombre;

    /**
     * Constructor base del nivel.
     *
     * @param numero número del nivel
     * @param nombre nombre descriptivo del nivel
     */
    public Level(int numero, String nombre) {
        this.numero  = numero;
        this.nombre  = nombre;
        this.enemies = new ArrayList<>();
        this.coins   = new ArrayList<>();
        inicializar();
    }

    /**
     * Inicializa los enemigos y monedas del nivel.
     * Cada subclase define su propia configuración.
     */
    protected abstract void inicializar();

    /**
     * Reinicia el nivel a su estado original.
     */
    public void reiniciar() {
        enemies.clear();
        coins.clear();
        inicializar();
    }

    /**
     * Retorna la lista de enemigos del nivel.
     *
     * @return lista de enemigos
     */
    public List<BasicEnemy> getEnemies() { return enemies; }

    /**
     * Retorna la lista de monedas del nivel.
     *
     * @return lista de monedas
     */
    public List<Coin> getCoins() { return coins; }

    /**
     * Retorna el número del nivel.
     *
     * @return número del nivel
     */
    public int getNumero() { return numero; }

    /**
     * Retorna el nombre del nivel.
     *
     * @return nombre del nivel
     */
    public String getNombre() { return nombre; }

    /**
     * Verifica si todas las monedas del nivel fueron recolectadas.
     *
     * @return true si todas las monedas están recolectadas
     */
    public boolean todasLasMonedasRecolectadas() {
        return coins.stream().allMatch(c -> c.collected);
    }
}