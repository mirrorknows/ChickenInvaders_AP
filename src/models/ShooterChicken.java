package models;

import java.awt.*;

public class ShooterChicken extends Chicken {

    public ShooterChicken(int x, int y, int lives) {

        super(x, y, 35, 35, 1, lives);

    }
    @Override
    public void move(int moveDirection, double groupSpeed) {

        x += (int )(speed * groupSpeed * moveDirection);

    }

    //shooter chicken shoots horizontally at player
    @Override
    public Egg shootAtPlayer(Player player) {

        int startX = getX() + getWidth() / 2;
        int startY = getY() + getHeight() / 2;

        int playerCenterX = player.getX() + player.getWidth() / 2;

        int dx;

        if(playerCenterX < startX) {
            dx = -4;
        } else {
            dx = 4;
        }

        return new Egg(
                startX,
                startY,
                dx,
                0
        );
    }

    @Override
    public int getScore(){
        return 25;
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }
}