package models;

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

    public Boss(int x, int y, int lives) {
        this.x = x;
        this.y = y;

        this.width = 160;
        this.height = 120;

        this.lives = lives;
        this.maxLives = lives;
    }

    //move boss left and right
    public void move(int panelWidth) {
        x += speed;

        //change direction after touching screen borders
        if (x <= 0 || x + width >= panelWidth) {
            speed *= -1;
        }
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
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, width, height);

        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 250;
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