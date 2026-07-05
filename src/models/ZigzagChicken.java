package models;

public class ZigzagChicken extends Chicken {

    private boolean movingDown = true;

    public ZigzagChicken(int x, int y) {

        super(x, y, 50, 50, 2, 2);

    }

    @Override
    public void move(int direction) {

    }
    @Override
    public int getScore(){
        return 20;
    }
}