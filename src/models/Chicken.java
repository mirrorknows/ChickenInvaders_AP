package models;

import java.awt.*;

public abstract class Chicken {

    //chicken position
    protected int x;
    protected int y;

    //chicken size;
    protected  int width;
    protected  int height;

    //chickens speed
    protected int speed ;

    protected int lives;

    public Chicken(int x, int y, int width, int height, int speed, int lives){

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.speed = speed;

        this.lives = lives;
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

    public int getLives(){
        return lives;
    }

    //setters

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void move(int direction, double groupSpeed);

    public abstract int getScore();

    //reduce one life after getting hit
    public void takeDamage(){
        lives--;
    }

    //check if chicken is hit by bullet
    public boolean isHit(Bullets bullet){

        return bullet.getX() < x + width &&
                bullet.getX() + bullet.getWidth() > x &&
                bullet.getY() < y + height &&
                bullet.getY() + bullet.getHeight() > y;
    }

    public boolean hitPlayer(Player player){
        return player.getX() < x + width &&
                player.getX() + player.getWidth() > x &&
                player.getY() < y + height &&
                player.getY() + player.getHeight() > y;
    }

    //normal zigzag and fast don't shoot horizontally
    public Egg shootAtPlayer(Player player) {

        return null;

    }
    public abstract Color getColor();

}
