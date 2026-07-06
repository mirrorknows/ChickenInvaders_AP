package models;

import java.util.ArrayList;

public class ChickenManager {

    //all chickens alive in the game
    private ArrayList<Chicken> chickens;

    //q means right direction / -1 means left direction
    private int moveDirection  = 1;

    //distance that chickens move down after touching a border
    private int moveDownStep = 20;

    private Level level;

    private double groupSpeed;

    public ChickenManager(Level level){

        this.level = level;
        chickens = new ArrayList<>();

        moveDownStep = level.getMoveDownStep();

        groupSpeed =  level.getGroupSpeed();
    }

    public ArrayList<Chicken> getChickens() {
        return chickens;
    }

    public void addChicken(Chicken chicken){
        chickens.add(chicken);
    }

    //5x8 chicken formation
    public void createFormation() {

        int x = 100;
        int y = 60;

        for (int row = 0; row < 5; row++) {

            x = 100;

            for (int col = 0; col < 8; col++) {

                switch (level.getLevelNumber()) {

                    case 1:

                        chickens.add(new NormalChicken(
                                x,
                                y,
                                level.getNormalLives()
                        ));

                        break;

                    case 2:

                        if (col % 2 == 0) {

                            chickens.add(new NormalChicken(
                                    x,
                                    y,
                                    level.getNormalLives()
                            ));

                        } else {

                            chickens.add(new FastChicken(
                                    x,
                                    y,
                                    level.getFastLives()
                            ));

                        }

                        break;

                    case 3:

                        if (col % 2 == 0) {

                            chickens.add(new NormalChicken(
                                    x,
                                    y,
                                    level.getNormalLives()
                            ));

                        } else {

                            chickens.add(new ZigzagChicken(
                                    x,
                                    y,
                                    level.getZigzagLives()
                            ));

                        }

                        break;

                    case 5:

                        if (col % 2 == 0) {

                            chickens.add(new ShooterChicken(
                                    x,
                                    y,
                                    level.getShooterLives()
                            ));

                        } else {

                            chickens.add(new FastChicken(
                                    x,
                                    y,
                                    level.getFastLives()
                            ));

                        }

                        break;

                    case 6:

                        if (col % 2 == 0) {

                            chickens.add(new ShooterChicken(
                                    x,
                                    y,
                                    level.getShooterLives()
                            ));

                        } else {

                            chickens.add(new ZigzagChicken(
                                    x,
                                    y,
                                    level.getZigzagLives()
                            ));

                        }

                        break;

                    case 7:

                        int randomType = (int) (Math.random() * 4);

                        switch (randomType) {

                            case 0:

                                chickens.add(new NormalChicken(
                                        x,
                                        y,
                                        level.getNormalLives()
                                ));

                                break;

                            case 1:

                                chickens.add(new FastChicken(
                                        x,
                                        y,
                                        level.getFastLives()
                                ));

                                break;

                            case 2:

                                chickens.add(new ZigzagChicken(
                                        x,
                                        y,
                                        level.getZigzagLives()
                                ));

                                break;

                            case 3:

                                chickens.add(new ShooterChicken(
                                        x,
                                        y,
                                        level.getShooterLives()
                                ));

                                break;
                        }

                        break;

                    default:

                        chickens.add(new NormalChicken(
                                x,
                                y,
                                level.getNormalLives()
                        ));

                        break;
                }

                x += 70;
            }

            y += 60;
        }
    }


    //move whole chicken group
    public void moveGroup(int panelWidth) {

        for (Chicken chicken : chickens) {
            chicken.move(moveDirection, groupSpeed);
        }

        boolean hitBorder = false;

        for (Chicken chicken : chickens) {

            if (chicken.getX() < 0 ||
                    chicken.getX() + chicken.getWidth() > panelWidth) {

                hitBorder = true;
                break;
            }
        }

        if (hitBorder) {

            moveDirection *= -1;

            moveDown();
        }
    }

    //if  any chicken reached the bottom
    public boolean reachedBottom(int panelHeight) {

        for (Chicken chicken : chickens) {

            if (chicken.getY() + chicken.getHeight() >= panelHeight) {
                return true;
            }

        }

        return false;
    }

    //move all chickens down
    public void moveDown() {

        for (Chicken chicken : chickens) {

            chicken.setY(chicken.getY() + moveDownStep);

        }

    }

    //last chickens (bottom chickens)
    public ArrayList<Chicken> getBottomChickens(){

        ArrayList<Chicken> bottomChickens = new ArrayList<>();

        for(int col = 0 ; col < 8 ; col++) {

            Chicken bottom = null;

            for (Chicken chicken : chickens) {

                int chickenColumn = (chicken.getX() - 100) / 70;

                if (chickenColumn == col) {

                    if (bottom == null || chicken.getY() > bottom.getY()) {

                        bottom = chicken;

                    }
                }

            }
            if (bottom != null) {

                bottomChickens.add(bottom);

            }
        }
        return bottomChickens;
    }
}
