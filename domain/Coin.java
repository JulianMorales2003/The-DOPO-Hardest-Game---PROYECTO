package domain;
/**
 * Representa una moneda estática en el tablero del juego.
 */
public class Coin {

    public int x;
    public int y;
    public int size;
    public boolean collected;
    /**
     * Crea una moneda en la posición indicada, sin recolectar.
     *
     * @param x posición en el eje X
     * @param y posición en el eje Y
     */
    public Coin(int x, int y) {
        this.x         = x;
        this.y         = y;
        this.size      = 20;
        this.collected = false;
    }
}

