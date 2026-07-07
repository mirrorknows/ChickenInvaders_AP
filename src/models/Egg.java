package models;

public class Egg {

    private int x;
    private int y;

    //move direction
    private int dx;
    private int dy;

    private final int width = 8;
    private final int height = 12;

    private final int speed = 4;

    public Egg(int x, int y) {

        this.x = x;
        this.y = y;

        //direction: down
        this.dx = 0;
        this.dy = speed;

    }


    //boss egg with direction
    public Egg(int x, int y, int dx, int dy) {

        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

    }



    public void drop(){
        x += dx;
        y += dy;
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