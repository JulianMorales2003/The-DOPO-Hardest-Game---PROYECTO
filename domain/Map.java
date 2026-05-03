package domain;

import java.util.List;
import java.util.ArrayList;

public class Map {

    protected int width;
    protected int height;
    protected int cellSize;
    protected List<Coin> coins;
    protected List<Enemy> enemies;

    public Map(int width, int height) {
        this.width    = width;
        this.height   = height;
        this.cellSize = 40;
        this.coins    = new ArrayList<>();
        this.enemies  = new ArrayList<>();
    }
}