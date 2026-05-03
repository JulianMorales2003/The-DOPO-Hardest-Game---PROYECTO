package domain;

import java.awt.Color;

public abstract class Player {

    protected String name;
    protected int x;
    protected int y;
    protected int size;
    protected double speed;
    protected Color color;
    protected int deaths;
    protected int coinsCollected;
    protected int spawnX;
    protected int spawnY;

    public Player(String name, int x, int y) {
        this.name          = name;
        this.x             = x;
        this.y             = y;
        this.spawnX        = x;
        this.spawnY        = y;
        this.deaths        = 0;
        this.coinsCollected = 0;
    }
}