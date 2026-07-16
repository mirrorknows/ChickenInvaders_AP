package models;

import helpers.ImageLoader;

import java.awt.*;

public class Egg {

    private int x;
    private int y;

    //move direction
    private int dx;
    private int dy;

    private final int width = 14;
    private final int height = 18;


    //egg image
    private static final Image image = ImageLoader.loadImage("/images/enemies/egg.png");

    public Egg(int x, int y) {

        this(x, y, 0, 4);
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

    //draw egg
    public void draw(Graphics g){

        g.drawImage(
                image,
                x,
                y,
                width,
                height,
                null
        );
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
