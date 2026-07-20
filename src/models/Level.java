package models;

public class Level {

    //current level number
    private int levelNumber;

    //horizontal movement speed of chicken group
    private double groupSpeed;

    //distance that chickens move down after hitting border
    private int moveDownStep;

    //time between egg drops (milliseconds)
    private int eggDropDelay;

    //enemy lives for this level
    private int normalLives;
    private int fastLives;
    private int zigzagLives;
    private int shooterLives;

    //number of chickens that must be defeated in each cell
    private int cellCounter;

    public Level(int levelNumber) {

        this.levelNumber = levelNumber;

        switch (levelNumber) {

            case 1:

                groupSpeed = 1;
                moveDownStep = 20;
                eggDropDelay = 3000;

                normalLives = 2;
                fastLives = 0;
                zigzagLives = 0;
                shooterLives = 0;

                cellCounter = 2;
                break;

            case 2:

                groupSpeed = 1.5;
                moveDownStep = 20;
                eggDropDelay = 2000;

                normalLives = 2;
                fastLives = 1;
                zigzagLives = 0;
                shooterLives = 0;

                cellCounter = 2;

                break;

            case 3:

                groupSpeed = 2;
                moveDownStep = 25;
                eggDropDelay = 1500;

                normalLives = 2;
                fastLives = 0;
                zigzagLives = 2;
                shooterLives = 0;

                cellCounter = 3;

                break;

            case 5:

                groupSpeed = 2.5;
                moveDownStep = 25;
                eggDropDelay = 1000;

                normalLives = 3;
                fastLives = 2;
                zigzagLives = 0;
                shooterLives = 3;

                cellCounter = 3;
                break;

            case 6:

                groupSpeed = 3;
                moveDownStep = 30;
                eggDropDelay = 800;

                normalLives = 0;
                fastLives = 0;
                zigzagLives = 3;
                shooterLives = 3;

                cellCounter = 4;

                break;

            case 7:

                groupSpeed = 3.5;
                moveDownStep = 30;
                eggDropDelay = 700;

                normalLives = 3;
                fastLives = 2;
                zigzagLives = 3;
                shooterLives = 3;

                cellCounter = 4;

                break;
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public double getGroupSpeed() {
        return groupSpeed;
    }

    public int getMoveDownStep() {
        return moveDownStep;
    }

    public int getEggDropDelay() {
        return eggDropDelay;
    }

    public int getNormalLives() {
        return normalLives;
    }

    public int getFastLives() {
        return fastLives;
    }

    public int getZigzagLives() {
        return zigzagLives;
    }

    public int getShooterLives() {
        return shooterLives;
    }

    public int getCellCounter() {
        return cellCounter;
    }
}
