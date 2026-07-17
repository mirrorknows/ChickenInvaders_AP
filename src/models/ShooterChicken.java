package models;

import java.awt.*;

public class ShooterChicken extends Chicken {

    public ShooterChicken(int x, int y, int lives) {

        super(
                x,
                y,
                45, 45, 6,
                lives, "/images/enemies/shooter_chicken.png"
        );

    }

    //shooter chicken shoots horizontally at player
    @Override
    public Egg shootAtPlayer(Player player) {

        int startX = getX() + getWidth() / 2;
        int startY = getY() + getHeight() / 2;

        int playerCenterX = player.getX() + player.getWidth() / 2;

        int dx;

        if(playerCenterX < startX) {
            dx = -5;
        } else {
            dx = 5;
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

}
