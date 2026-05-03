package domain;

import java.awt.Color;

public abstract class Enemy {

    protected int x;
    protected int y;
    protected int size;
    protected double speed;
    protected Color color;
    protected double dx;
    protected double dy;

    public Enemy(int x, int y, double dx, double dy) {
        this.x     = x;
        this.y     = y;
        this.dx    = dx;
        this.dy    = dy;
        this.color = new Color(30, 100, 220);
    }
}