package models;

import java.util.ArrayList;
import java.util.List;

//final boss level 8
public class FinalBoss extends Boss {

    //horizontal movement
    private double exactX;
    private double moveSpeed = 1.5;
    private double acceleration = 0.01;
    private int direction = 1;

    //vertical movement
    private int startY;
    private double verticalAngle = 0;

    //50 up and 50 down = 100 pixels
    private final int verticalRange = 50;

    //random direction change
    private long nextDirectionChangeTime;

    public FinalBoss(int x, int y, int lives) {

        super(
                x, y,
                270, 210,
                lives, 1000,
                "/images/enemies/boss2.png"
        );

        exactX = x;
        startY = y;

        nextDirectionChangeTime = System.currentTimeMillis() + 2500;
    }

    @Override
    public void move(int panelWidth) {

        //change speed smoothly
        moveSpeed += acceleration;

        if(moveSpeed >= 3) {

            moveSpeed = 3;
            acceleration = -0.01;

        } else if(moveSpeed <= 1.5) {

            moveSpeed = 1.5;
            acceleration = 0.01;
        }

        long currentTime = System.currentTimeMillis();

        //change direction sometimes
        if(currentTime >= nextDirectionChangeTime) {

            direction *= -1;

            nextDirectionChangeTime =
                    currentTime
                            + 2000
                            + (long)(Math.random() * 2000);
        }

        //horizontal movement
        exactX += moveSpeed * direction;

        //check borders
        if(exactX <= 0) {

            exactX = 0;
            direction = 1;

        } else if(exactX + width >= panelWidth) {

            exactX = panelWidth - width;
            direction = -1;
        }

        x = (int)Math.round(exactX);

        //vertical smooth movement
        verticalAngle += 0.03;

        y = startY
                + (int)(verticalRange * Math.sin(verticalAngle));
    }

    //final boss attacks in 8 directions
    @Override
    public List<Egg> createAttack() {

        List<Egg> attackEggs = new ArrayList<>();

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        //up
        attackEggs.add(new Egg(centerX, centerY, 0, -5));

        //down
        attackEggs.add(new Egg(centerX, centerY, 0, 5));

        //left
        attackEggs.add(new Egg(centerX, centerY, -5, 0));

        //right
        attackEggs.add(new Egg(centerX, centerY, 5, 0));

        //up-left
        attackEggs.add(new Egg(centerX, centerY, -4, -4));

        //up-right
        attackEggs.add(new Egg(centerX, centerY, 4, -4));

        //down-left
        attackEggs.add(new Egg(centerX, centerY, -4, 4));

        //down-right
        attackEggs.add(new Egg(centerX, centerY, 4, 4));

        return attackEggs;
    }
    @Override
    public void addPausedTime(long pausedDuration) {

        super.addPausedTime(pausedDuration);

        nextDirectionChangeTime += pausedDuration;
    }
}
