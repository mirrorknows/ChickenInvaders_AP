package models;

import helpers.ImageLoader;

import java.awt.*;

//this class draws a simple explosion effect
public class Explosion {

    private int x;
    private int y;

    private int maxSize;

    private long startTime;

    private final long duration = 250;

    //explosion image
    //shared explosion image
    private static final Image image =
            ImageLoader.loadImage(
                    "/images/planes/Explosion.png"
            );

    public Explosion(int x, int y, int maxSize) {

        this.x = x;
        this.y = y;
        this.maxSize = maxSize;

        startTime = System.currentTimeMillis();
    }

    //check if explosion time is finished
    public boolean isFinished() {

        long currentTime = System.currentTimeMillis();

        return currentTime - startTime > duration;
    }

    //draw explosion
    public void draw(Graphics g) {

        long currentTime = System.currentTimeMillis();

        long passedTime = currentTime - startTime;

        double progress =
                passedTime / (double) duration;

        int size = (int)(maxSize * progress);

        if(size < 20) {
            size = 20;
        }

        if(size > maxSize) {
            size = maxSize;
        }

        g.drawImage(
                image,
                x - size / 2,
                y - size / 2,
                size,
                size,
                null
        );
    }

    //freeze explosion animation during pause
    public void addPausedTime(long pauseTime) {

        startTime += pauseTime;
    }

}
