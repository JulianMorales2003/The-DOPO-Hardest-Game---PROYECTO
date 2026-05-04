package domain;

import java.awt.Color;

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