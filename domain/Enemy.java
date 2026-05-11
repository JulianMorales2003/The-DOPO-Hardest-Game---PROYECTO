package domain;

import java.awt.Color;

/**
 * Clase abstracta que representa un enemigo en el juego.
 */
public abstract class Enemy {

    public int x;
    public int y;
    public int size;
    public double speed;
    public Color color;
    public double dx;
    public double dy;

    /**
     * Constructor base del enemigo.
     *
     * @param x  posición inicial en el eje X
     * @param y  posición inicial en el eje Y
     * @param dx dirección inicial en X
     * @param dy dirección inicial en Y
     */
    public Enemy(int x, int y, double dx, double dy) {
        this.x     = x;
        this.y     = y;
        this.dx    = dx;
        this.dy    = dy;
        this.color = new Color(30, 100, 220);
    }

    /**
     * Actualiza la posición del enemigo en cada tick del game loop.
     *
     * @param mapW ancho del tablero en píxeles
     * @param mapH alto del tablero en píxeles
     */
    public abstract void update(int mapW, int mapH);
}