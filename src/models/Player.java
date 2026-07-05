package models;
//this class is player (spaceship)
public class Player {

    //player position
    private int x;
    private int y;

    //player size
    private final int width = 60;
    private final int height = 60;

    //player speed
    private int speed = 5;

    //player lives
    private  int lives = 3;
    private int maxLives = 5;

    //shoot delay
    private final int shootDelay = 300;

    //last shot
    private long lastShotTime;

    //last time that got hit
    private long lastDamageTime;

    //time that player will not get hit after damage
    private final long damageCooldown = 1000;

    public Player(int x,int y){

        this.x = x;
        this.y = y;

        lastShotTime = 0;
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
}
