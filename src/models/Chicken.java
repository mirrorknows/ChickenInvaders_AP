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

    //cell for the chicken
    protected Cell cell;

    protected boolean movingToCell = false;

    protected int targetX;
    protected int targetY;

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


    public int getTargetX(){

        return targetX;

    }

    public int getTargetY(){

        return targetY;

    }
    //setters

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell(){

        return cell;

    }

    public boolean isMovingToCell(){

        return movingToCell;

    }

    public void startMovingToCell(){

        movingToCell = true;

    }
    public abstract void move(int direction, double groupSpeed);

    // move replacement chicken to its current cell
    public void moveToCell(){

        if(cell == null){
            return;
        }

        int speed = 2;

        int currentTargetX = cell.getX();
        int currentTargetY = cell.getY();

        if(x < currentTargetX){

            x += speed;

            if(x > currentTargetX){
                x = currentTargetX;
            }

        } else if(x > currentTargetX){

            x -= speed;

            if(x < currentTargetX){
                x = currentTargetX;
            }
        }

        if(y < currentTargetY){

            y += speed;

            if(y > currentTargetY){
                y = currentTargetY;
            }

        } else if(y > currentTargetY){

            y -= speed;

            if(y < currentTargetY){
                y = currentTargetY;
            }
        }

        if(x == currentTargetX && y == currentTargetY){

            movingToCell = false;

        }
    }

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

    public void followCell(){

        if(cell != null){

            x = cell.getX();
            y = cell.getY();

        }

    }
}
