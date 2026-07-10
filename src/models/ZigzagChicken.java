package models;

import java.awt.*;

public class ZigzagChicken extends Chicken {

    private int startY;
    private double angle = 0;
    private int moveRange = 10;

    //private int zigzagStep = 0;



    public ZigzagChicken(int x, int y, int lives) {

        super(x, y, 35, 35, 1, lives);
        startY = y;
    }

    @Override
    public void move(int moveDirection, double groupSpeed) {

        x += (int)(speed * groupSpeed * moveDirection);

        angle += 0.15;

        y = startY + (int)(moveRange * Math.sin(angle));
    }

    @Override
    public void setY(int y) {

        super.setY(y);

        startY = y;
    }

    @Override
    public void updatePositionFromCell(){

        if(getCell() != null){

            angle += 0.15;

            x = getCell().getX();
            y = getCell().getY() + (int)(moveRange * Math.sin(angle));

        }
    }

    @Override
    public int getScore(){
        return 20;
    }

    @Override
    public Color getColor(){
        return Color.MAGENTA;
    }
}