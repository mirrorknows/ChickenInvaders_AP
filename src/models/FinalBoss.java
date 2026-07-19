package models;

import java.util.ArrayList;
import java.util.List;

//final boss level 8
public class FinalBoss extends Boss {

    private static final double MIN_SPEED = 1.5;
    private static final double MAX_SPEED = 3.0;
    private static final double ACCELERATION_AMOUNT = 0.01;

    //horizontal movement
    private double exactX;
    private double moveSpeed = 2;
    private double acceleration = ACCELERATION_AMOUNT;
    private int direction = 1;

    //vertical movement
    private int startY;
    private double verticalAngle = 0;

    //50 up and 50 down
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

        nextDirectionChangeTime =
                System.currentTimeMillis() + getRandDirectionDelay();
    }

    @Override
    public void move(int panelWidth) {

        //change speed smoothly
        moveSpeed += acceleration;

        if(moveSpeed >= MAX_SPEED) {

            moveSpeed = MAX_SPEED;
            acceleration = -ACCELERATION_AMOUNT;

        } else if(moveSpeed <= MIN_SPEED) {

            moveSpeed = MIN_SPEED;
            acceleration = ACCELERATION_AMOUNT;
        }

        long currentTime = System.currentTimeMillis();

        //change direction sometimes
        if(currentTime >= nextDirectionChangeTime) {

            direction *= -1;
            nextDirectionChangeTime =
                    currentTime + getRandDirectionDelay();
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

        y = startY + (int)(verticalRange * Math.sin(verticalAngle));
    }

    //random delay between 2 & 4 sec
    private long getRandDirectionDelay() {
        return 2000 + (long)(Math.random() * 2000);
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
    public void addPausedTime(long pauseTime) {

        super.addPausedTime(pauseTime);
        nextDirectionChangeTime += pauseTime;
    }
}
