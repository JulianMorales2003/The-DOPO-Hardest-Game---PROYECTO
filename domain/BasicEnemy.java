package domain;

import java.awt.Color;

public class BasicEnemy extends Enemy {

    public BasicEnemy(int x, int y, double dx, double dy) {
        super(x, y, dx, dy);
        this.speed = 2.5;
        this.size  = 28;
        this.color = new Color(30, 100, 220);
    }

    public void update(int mapW, int mapH) {
        x += dx * speed;
        y += dy * speed;
        if (x <= 0 || x + size >= mapW) { dx = -dx; x = Math.max(0, Math.min(x, mapW - size)); }
        if (y <= 0 || y + size >= mapH) { dy = -dy; y = Math.max(0, Math.min(y, mapH - size)); }
    }
}