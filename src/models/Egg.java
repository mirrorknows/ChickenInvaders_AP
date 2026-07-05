package models;

public class Egg {

    private int x;
    private int y;

    private final int width = 8;
    private final int height = 12;

    private final int speed = 4;

    public Egg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drop(){
        y += speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}