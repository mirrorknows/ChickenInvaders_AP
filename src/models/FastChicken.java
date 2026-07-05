package models;

public class FastChicken extends Chicken {

    public FastChicken(int x, int y) {

        super(x, y, 50, 50, 4, 1);

    }

    @Override
    public void move(int direction) {


    }
    @Override
    public int getScore(){
        return 15;
    }
}