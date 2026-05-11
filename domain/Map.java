package domain;

import java.util.List;
import java.util.ArrayList;
/**
 * Representa el tablero de un nivel del juego.
 */
public class Map {

    protected int width;
    protected int height;
    protected int cellSize;
    protected List<Coin> coins;
    protected List<Enemy> enemies;
/**
* Crea un mapa con las dimensiones indicadas.
*
* @param width  ancho del tablero en píxeles
* @param height alto del tablero en píxeles
*/
    public Map(int width, int height) {
        this.width    = width;
        this.height   = height;
        this.cellSize = 40;
        this.coins    = new ArrayList<>();
        this.enemies  = new ArrayList<>();
    }
}
