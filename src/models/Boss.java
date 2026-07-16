package models;

import helpers.ImageLoader;

import java.awt.*;
//this class creates boss enemy for boss levels

public class Boss {

    //boss position
    private int x;
    private int y;

    //boss size
    private int width;
    private int height;

    //boss lives
    private int lives;
    private int maxLives;

    private int speed = 2;

    //vertical movement settings
    private int startY;

    private double angle = 0;

    private int verticalRange = 30;

    //boss attack settings
    private long lastAttackTime = 0;
    private final long attackDelay = 1500;

    protected Image image;

    public Boss(int x, int y, int lives) {

        this.x = x;
        this.y = y;

        this.width = 160;
        this.height = 120;

        this.lives = lives;
        this.maxLives = lives;

        this.startY = y;

        image = ImageLoader.loadImage(
                "/images/enemies/boss1.png"
        );
    }

    //move boss left and right
    public void move(int panelWidth) {
        //horizontal movement
        x += speed;

        //change direction at borders
        if (x <= 0 || x + width >= panelWidth) {
            speed *= -1;
        }

        //vertical smooth movement
        angle += 0.05;
        y = startY + (int)(verticalRange * Math.sin(angle));

    }

    //reduce boss life after getting hit
    public void takeDamage() {
        lives--;
    }

    //check if boss has no lives left
    public boolean isDead() {
        return lives <= 0;
    }

    //check if player bullet hits boss
    public boolean isHit(Bullets bullet) {
        return bullet.getX() < x + width &&
                bullet.getX() + bullet.getWidth() > x &&
                bullet.getY() < y + height &&
                bullet.getY() + bullet.getHeight() > y;
    }

    public void draw(Graphics g) {

        g.drawImage(
                image,
                x,
                y,
                width,
                height,
                null
        );

        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = width;
        int barHeight = 15;

        int barX = x;
        int barY = y - 25;

        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight);

        int currentWidth = (int) ((lives / (double) maxLives) * barWidth);

        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, currentWidth, barHeight);

        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barWidth, barHeight);
    }
    //if boss can attack
    public boolean canAttack(){

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastAttackTime >= attackDelay){

            lastAttackTime = currentTime;
            return true;
        }
        return false;
    }

    //getters
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
