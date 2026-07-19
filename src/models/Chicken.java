package models;

import helpers.ImageLoader;

import java.awt.*;

public abstract class Chicken {

    //chicken position
    protected int x;
    protected int y;
    protected double exactX;
    protected double exactY;

    //chicken size;
    protected  int width;
    protected  int height;

    protected int spawnSpeed;

    protected int lives;

    //cell for the chicken
    protected Cell cell;

    //its true while a chicken is flying to cell
    protected boolean movingToCell = false;

    //chicken image
    protected Image image;

    public Chicken(
            int x, int y,
            int width, int height,
            int spawnSpeed, int lives,
            String imagePath
    ){

        this.x = x;
        this.y = y;

        this.exactX = x;
        this.exactY = y;

        this.width = width;
        this.height = height;

        this.spawnSpeed = spawnSpeed;
        this.lives = lives;

        this.image = ImageLoader.loadImage(imagePath);
    }

    //draw chicken
    public void draw(Graphics g){

        g.drawImage(
                image,
                x, y,
                width, height,
                null
        );
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

    //move replacement chicken toward its cell
    public void moveToCell() {

        if (cell == null) {
            return;
        }

        //current cell position
        double targetX = cell.getX();
        double targetY = cell.getY();

        //distance to the cell
        double distanceX = targetX - exactX;
        double distanceY = targetY - exactY;

        //total distance to the cell
        double distance = Math.sqrt(
                distanceX * distanceX +
                        distanceY * distanceY
        );

        //place the chicken on the cell when it got close
        if (distance <= spawnSpeed) {

            exactX = targetX;
            exactY = targetY;

            x = (int)Math.round(exactX);
            y = (int)Math.round(exactY);

            movingToCell = false;

            return;
        }

        //move toward the target cell
        exactX += distanceX / distance * spawnSpeed;
        exactY += distanceY / distance * spawnSpeed;

        //update the position
        x = (int) Math.round(exactX);
        y = (int) Math.round(exactY);
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

    //check if the chicken is hit by the player
    public boolean hitPlayer(Player player){
        return player.getX() < x + width &&
                player.getX() + player.getWidth() > x &&
                player.getY() < y + height &&
                player.getY() + player.getHeight() > y;
    }

    //normal zigzag and fast don't shoot horizontally (just shooter)
    public Egg shootAtPlayer(Player player) {

        return null;

    }

    //update the chicken inside the formation
    public void updateInFormation() {

        followCell();
    }

    //keep the chicken on its cell
    public void followCell() {

        if(cell == null) {
            return;
        }

        exactX = cell.getX();
        exactY = cell.getY();

        x = (int)Math.round(exactX);
        y = (int)Math.round(exactY);
    }

    //move chicken with the formation
    public void moveWithFormation(double dx, double dy) {

        exactX += dx;
        exactY += dy;

        x = (int) Math.round(exactX);
        y = (int) Math.round(exactY);
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

    public int getSpawnSpeed() {
        return spawnSpeed;
    }

    public int getLives(){
        return lives;
    }

    //setters

    public void setX(int x) {

        this.x = x;
        this.exactX = x;
    }

    public void setY(int y) {

        this.y = y;
        this.exactY = y;
    }
}
