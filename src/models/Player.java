package models;

import helpers.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//this class is player (spaceship)
public class Player {

    //player image
    private Image image;

    //player position
    private int x;
    private int y;

    //player size
    private final int width = 75;
    private final int height = 75;

    //player speed
    private int speed = 5;

    //player lives
    private  int lives = 3;
    private int maxLives = 5;

    //number of bullets in one shot
    private int fireCount = 1;
    private int maxFireCount = 5;

    //shoot delay
    private final int shootDelay = 300;

    //last shot
    private long lastShotTime;

    //last time that got hit
    private long lastDamageTime;

    //time that player will not get hit after damage
    private final long damageCooldown = 1000;

    //rapid fire settings
    private boolean rapidFireActive = false;
    private long rapidFireEndTime = 0;
    private final int rapidFireDelay = 100;

    //shield settings
    private boolean shieldActive = false;
    private long shieldEndTime = 0;

    public Player(int x,int y){

        this.x = x;
        this.y = y;

        image = ImageLoader.loadImage(
                "/images/planes/6.png"
        );

        lastShotTime = 0;
    }

    //draw player
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

    //moves player to the right
    public void moveRight() {
        x += speed;
    }

    //moves player to the left
    public void moveLeft() {
        x -= speed;
    }
    //moves player up
    public void moveUp() {
        y -= speed;
    }
    //moves player down
    public void moveDown() {
        y += speed;
    }

    //keep player inside the screen
    public void stayInsideScreen(int screenWidth, int screenHeight){
        if (x < 0)
            x = 0;

        if (x > screenWidth - width)
            x = screenWidth - width;

        if (y < 0)
            y = 0;

        if (y > screenHeight - height)
            y = screenHeight - height;
    }

    //if player is hit by eggs
    public boolean isHit(Egg egg){

        return egg.getX() < x + width &&
                egg.getX() + egg.getWidth() > x &&
                egg.getY() < y + height &&
                egg.getY() + egg.getHeight() > y;
    }

    public void takeDamage(){
        lives--;
        lastDamageTime = System.currentTimeMillis();
    }

    //check if player can take damage
    public boolean canTakeDamage(){

        long currentTime = System.currentTimeMillis();
        return currentTime - lastDamageTime  >= damageCooldown;
    }

    //increase number of bullets
    public void addFire() {
        if(fireCount < maxFireCount){
            fireCount++;
        }
    }

    //add one life if player is not full
    public void addLife() {
        if (lives < maxLives) {
            lives++;
        }
    }

    //activate rapid fire for 8 seconds
    public void activateRapidFire() {

        rapidFireActive = true;

        rapidFireEndTime = System.currentTimeMillis() + 8000;

    }

    //check if rapid fire is active
    public boolean isRapidFireActive() {

        if(rapidFireActive &&
                System.currentTimeMillis() > rapidFireEndTime) {

            rapidFireActive = false;

        }

        return rapidFireActive;
    }

    //return current shoot delay
    public int getCurrentShootDelay() {

        if(isRapidFireActive()) {

            return rapidFireDelay;

        }

        return shootDelay;
    }
    //shield for 10 seconds
    public void activateShield() {

        shieldActive = true;

        shieldEndTime = System.currentTimeMillis() + 10000;

    }

    //check if shield is active
    public boolean isShieldActive() {

        if(shieldActive &&
                System.currentTimeMillis() > shieldEndTime) {

            shieldActive = false;

        }

        return shieldActive;
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

    public int getSpeed() {
        return speed;
    }

    public int getLives() {
        return lives;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public int getFireCount() {
        return fireCount;
    }

    public int getMaxFireCount() {
        return maxFireCount;
    }

    public int getShootDelay() {
        return shootDelay;
    }

    public long getLastShotTime() {
        return lastShotTime;
    }

    //setters
    public void setLastShotTime(long lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

}
