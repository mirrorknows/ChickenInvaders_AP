package models;

import java.awt.*;

public class FastChicken extends Chicken {

    public FastChicken(int x, int y, int lives) {

        super(x, y, 35, 35, 2, lives);

    }

    @Override
    public void move(int moveDirection, double groupSpeed) {

        x += (int )(speed* groupSpeed * moveDirection);

    }
    @Override
    public int getScore(){
        return 15;
    }

    @Override
    public Color getColor(){
        return Color.BLUE;
    }
}