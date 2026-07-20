package models;

import java.util.ArrayList;
import java.util.List;

//boss for level 4
public class BossLevel4 extends Boss {

    //movement settings
    private int speed = 2;
    private int startY;
    private double angle = 0;
    private int verticalRange = 30;

    public BossLevel4(int x, int y, int lives) {

        super(
                x, y,
                240, 180,
                lives, 1500,
                "/images/enemies/boss1.png"
        );

        startY = y;
    }

    @Override
    public void move(int panelWidth) {

        //horizontal movement
        x += speed;

        //change direction at borders
        if(x <= 0 || x + width >= panelWidth){
            speed *= -1;
        }

        //move up and down
        angle += 0.05;

        y = startY + (int)(verticalRange * Math.sin(angle));
    }

    //boss level 4 attacks in 4 directions
    @Override
    public List<Egg> createAttack() {

        List<Egg> attackEggs = new ArrayList<>();

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        //up
        attackEggs.add(new Egg(centerX, centerY, 0, -4));

        //down
        attackEggs.add(new Egg(centerX, centerY, 0, 4));

        //left
        attackEggs.add(new Egg(centerX, centerY, -4, 0));

        //right
        attackEggs.add(new Egg(centerX, centerY, 4, 0));

        return attackEggs;
    }

}
