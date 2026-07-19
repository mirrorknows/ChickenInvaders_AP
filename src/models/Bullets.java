package models;

import helpers.ImageLoader;

import java.awt.*;

//this class creates bullets
public class Bullets {

    //bullet position
    private int x;
    private int y;

    //bullet speed
    private final int speed = 8 ;

    //bullet size
    private final int width = 20;
    private final int height = 38;

    //bullet image
    private static final Image image =
            ImageLoader.loadImage("/images/planes/shot.png");

    public Bullets(int centerX, int playerY){

        this.x = centerX - width / 2;
        this.y = playerY - height;

    }

    //draw bullet
    public void draw(Graphics g){

        g.drawImage(
                image,
                x, y,
                width, height,
                null
        );
    }

    //moves bullet up
    public void fly(){
        y -= speed;
    }

    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
