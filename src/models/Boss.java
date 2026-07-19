package models;

import helpers.ImageLoader;
import java.util.List;
import java.awt.*;

//this class creates boss enemy for boss levels
public abstract class Boss {

    //boss position
    protected int x;
    protected int y;

    //boss size
    protected final int width;
    protected final int height;

    //boss lives
    private int lives;
    private final int maxLives;

    //boss attack settings
    private long lastAttackTime;
    private final long attackDelay;

    //boss image
    private final Image image;


    public Boss(
            int x, int y,
            int width, int height,
            int lives, long attackDelay,
            String imagePath
    ) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.lives = lives;
        this.maxLives = lives;

        this.attackDelay = attackDelay;
        this.lastAttackTime = System.currentTimeMillis();

        image = ImageLoader.loadImage(imagePath);
    }

    public abstract void move(int panelWidth);

    public abstract List<Egg> createAttack();

    //reduce boss life based on damage
    public void takeDamage(int damage) {
        lives -= damage;
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

        g.drawImage(image, x, y,
                width, height,
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

    //boss attack timer after game pause
    public void addPausedTime(long pausedDuration) {

        lastAttackTime += pausedDuration;
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
