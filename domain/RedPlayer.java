package domain;

import java.awt.Color;

public class RedPlayer extends Player {

    public RedPlayer(String name, int x, int y) {
        super(name, x, y);
        this.speed = 3;
        this.size  = 30;
        this.color = new Color(210, 40, 40);
    }
}