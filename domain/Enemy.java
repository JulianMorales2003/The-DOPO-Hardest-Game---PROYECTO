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

    public Enemy(int x, int y, double dx, double dy) {
        this.x     = x;
        this.y     = y;
        this.dx    = dx;
        this.dy    = dy;
        this.color = new Color(30, 100, 220);
    }
}
