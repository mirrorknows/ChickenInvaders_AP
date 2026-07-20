package models;

import helpers.ImageLoader;

import java.awt.*;

//represents power ups
public class PowerUp {

    //powerup type
    private String type;

    //power up position
    private int x;
    private int y;

    //power up size
    private final int width = 25;
    private final int height = 25;

    //falling speed
    private final int speed = 2;

    //power up image
    private Image image;

    public PowerUp(int x, int y, String type) {

        this.x = x;
        this.y = y;
        this.type = type;

        if(type.equals("ADD_FIRE")) {

            image = ImageLoader.loadImage(
                    "/images/powerups/add_shot.png");

        } else if(type.equals("EXTRA_LIFE")) {

            image = ImageLoader.loadImage(
                    "/images/powerups/heal.png");

        } else if(type.equals("RAPID_FIRE")) {

            image = ImageLoader.loadImage(
                    "/images/powerups/fast_shot.png"
            );

        } else if(type.equals("SHIELD")) {

            image = ImageLoader.loadImage(
                    "/images/powerups/sheild.png");

        } else if(type.equals("FREEZE_BOMB")) {

            image = ImageLoader.loadImage(
                    "/images/powerups/freeze.png");
        }
    }

    //move power up down
    public void fall() {
        y += speed;
    }

    //check if player catches power up
    public boolean hitPlayer(Player player) {
        return player.getX() < x + width &&
                player.getX() + player.getWidth() > x &&
                player.getY() < y + height &&
                player.getY() + player.getHeight() > y;
    }

    //draw power up
    public void draw(Graphics g) {

        g.drawImage(
                image,
                x, y,
                width, height,
                null
        );
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}
