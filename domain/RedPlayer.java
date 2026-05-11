package domain;

import java.awt.Color;
/**
 * Jugador rojo — tipo estándar, sin habilidades especiales.
 */
public class RedPlayer extends Player {
 /**
     * Crea un jugador rojo en la posición indicada.
     *
     * @param name nombre del jugador
     * @param x    posición inicial en el eje X
     * @param y    posición inicial en el eje Y
     */
    public RedPlayer(String name, int x, int y) {
        super(name, x, y);
        this.speed = 3;
        this.size  = 30;
        this.color = new Color(210, 40, 40);
    }
}
