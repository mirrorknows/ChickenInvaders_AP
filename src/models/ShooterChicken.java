package models;

import java.awt.*;

public class ShooterChicken extends Chicken {

    public ShooterChicken(int x, int y, int lives) {

        super(x, y, 38, 38, 1, lives);

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
    public Color getColor() {
        return Color.WHITE;
    }
}