package domain;

import java.awt.Rectangle;

/**
 * Representa una pared estática en el tablero del juego.
 * El jugador no puede atravesarla, los enemigos sí.
 */
public class Wall {

    public int x;
    public int y;
    public int width;
    public int height;

    /**
     * Crea una pared en la posición y dimensiones indicadas.
     *
     * @param x      posición en el eje X
     * @param y      posición en el eje Y
     * @param width  ancho de la pared en píxeles
     * @param height alto de la pared en píxeles
     */
    public Wall(int x, int y, int width, int height) {
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
    }

    /**
     * Retorna los límites de la pared como rectángulo.
     *
     * @return rectángulo con la posición y dimensiones de la pared
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Verifica si un rectángulo colisiona con la pared.
     *
     * @param other rectángulo a verificar
     * @return true si hay colisión
     */
    public boolean collidesWith(Rectangle other) {
        return getBounds().intersects(other);
    }
}