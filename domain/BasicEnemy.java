package domain;

import java.awt.Color;
/**
 * Enemigo básico que se mueve en línea recta y rebota en los bordes.
 */
public class BasicEnemy extends Enemy {
    /**
     * Crea un enemigo básico en la posición y dirección indicadas.
     *
     * @param x  posición inicial en el eje X
     * @param y  posición inicial en el eje Y
     * @param dx dirección inicial en X
     * @param dy dirección inicial en Y
     */
    public BasicEnemy(int x, int y, double dx, double dy) {
        super(x, y, dx, dy);
        this.speed = 2.5;
        this.size  = 28;
        this.color = new Color(30, 100, 220);
    }
 /**
     * Mueve el enemigo y lo hace rebotar al llegar a los bordes del tablero.
     *
     * @param mapW ancho del tablero en píxeles
     * @param mapH alto del tablero en píxeles
     */
    @Override
    public void update(int mapW, int mapH) {
        x += dx * speed;
        y += dy * speed;
        if (x <= 0 || x + size >= mapW) { dx = -dx; x = Math.max(0, Math.min(x, mapW - size)); }
        if (y <= 0 || y + size >= mapH) { dy = -dy; y = Math.max(0, Math.min(y, mapH - size)); }
    }
}
