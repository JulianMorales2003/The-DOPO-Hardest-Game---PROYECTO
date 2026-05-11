package domain;
/**
 * Representa una moneda estática en el tablero del juego.
 */
public class Coin {

    public int x;
    public int y;
    public int size;
    public boolean collected;

    public Coin(int x, int y) {
        this.x         = x;
        this.y         = y;
        this.size      = 20;
        this.collected = false;
    }
}

