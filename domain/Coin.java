package domain;

public class Coin {

    protected int x;
    protected int y;
    protected int size;
    protected boolean collected;

    public Coin(int x, int y) {
        this.x         = x;
        this.y         = y;
        this.size      = 20;
        this.collected = false;
    }
}