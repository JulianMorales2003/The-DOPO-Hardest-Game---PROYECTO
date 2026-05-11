package domain;

import java.awt.Color;

/**
 * Clase abstracta que representa un jugador en el juego.
 */
public abstract class Player {

    public String name;
    public int x;
    public int y;
    public int size;
    public double speed;
    public Color color;
    public int deaths;
    public int coinsCollected;
    public int spawnX;
    public int spawnY;

    /**
     * Constructor base del jugador.
     *
     * @param name nombre del jugador
     * @param x    posición inicial en el eje X
     * @param y    posición inicial en el eje Y
     */
    public Player(String name, int x, int y) {
        this.name           = name;
        this.x              = x;
        this.y              = y;
        this.spawnX         = x;
        this.spawnY         = y;
        this.deaths         = 0;
        this.coinsCollected = 0;
    }
}