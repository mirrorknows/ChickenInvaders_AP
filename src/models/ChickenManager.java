package models;

import java.util.ArrayList;

public class ChickenManager {

    //all chickens alive in the game
    private ArrayList<Chicken> chickens;

    //q means right direction / -1 means left direction
    private int moveDirection  = 1;

    //distance that chickens move down after touching a border
    private int moveDownStep = 20;

    public ChickenManager(){

        chickens = new ArrayList<>();

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

                chickens.add(new NormalChicken(x, y));

                x += 70;

            }
            //  move to the next row
            y += 60;

        }

    }


    //move whole chicken group
    public void moveGroup(int panelWidth) {

        //if any chicken has reached the left or right border
        boolean hitBorder = false;

        for (Chicken chicken : chickens) {

            if (chicken.getX() <= 0 ||
                    chicken.getX() + chicken.getWidth() >= panelWidth) {

                hitBorder = true;
                break;
            }
        }

        if (hitBorder) {

            //change direction
            moveDirection *= -1;

            moveDown();

        }

        //move chickens in direction
        for (Chicken chicken : chickens) {

            chicken.move(moveDirection);

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
