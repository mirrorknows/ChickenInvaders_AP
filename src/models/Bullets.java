package models;
//this class creates bullets
public class Bullets {

    //bullet position
    private int x;
    private int y;

    //bullet speed
    private final int speed = 8 ;

    //bullet size
    private final int width = 5;
    private final int height = 15;

    public Bullets(int x , int y){

        this.x = x;
        this.y = y;

    }

    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //moves bullet up
    public void fly(){
        y -= speed;
    }
}
