package managers;

import models.*;

import java.util.ArrayList;

public class ChickenManager {

    //all chickens alive in the game
    private ArrayList<Chicken> chickens;

    //1 means right direction / -1 means left direction
    private int moveDirection  = 1;

    //distance that chickens move down after touching a border
    private int moveDownStep;

    private Level level;

    private double groupSpeed;

    private Cell[][] cells;

    public ChickenManager(Level level){

        this.level = level;
        chickens = new ArrayList<>();

        moveDownStep = level.getMoveDownStep();

        groupSpeed =  level.getGroupSpeed();

        cells = new Cell[5][8];

    }

    public ArrayList<Chicken> getChickens() {
        return chickens;
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

                        addChickenToCell(row, col, x, y, "NORMAL");
                        break;

                    case 2:

                        if (col % 2 == 0) {

                            addChickenToCell(row, col, x, y, "NORMAL");

                        } else {

                            addChickenToCell(row, col, x, y, "FAST");

                        }

                        break;

                    case 3:

                        if (col % 2 == 0) {

                            addChickenToCell(row, col, x, y, "NORMAL");

                        } else {

                            addChickenToCell(row, col, x, y, "ZIGZAG");

                        }

                        break;

                    case 5:

                        if (col % 2 == 0) {

                            addChickenToCell(row, col, x, y, "SHOOTER");

                        } else {

                            addChickenToCell(row, col, x, y, "FAST");

                        }

                        break;

                    case 6:

                        if (col % 2 == 0) {

                            addChickenToCell(row, col, x, y, "SHOOTER");

                        } else {

                            addChickenToCell(row, col, x, y, "ZIGZAG");
                        }

                        break;

                    case 7:

                        int type = col % 4;

                        switch (type) {

                            case 0:

                                addChickenToCell(row, col, x, y, "NORMAL");
                                break;

                            case 1:

                                addChickenToCell(row, col, x, y, "FAST");
                                break;

                            case 2:

                                addChickenToCell(row, col, x, y, "ZIGZAG");
                                break;

                            case 3:

                                addChickenToCell(row, col, x, y, "SHOOTER");
                                break;
                        }

                        break;

                    default:

                        addChickenToCell(row, col, x, y, "NORMAL");
                        break;
                }

                x += 55;
            }

            y += 55;
        }
    }

    //create chicken by type
    private Chicken createChickenByType(String type, int x, int y) {

        if(type.equals("NORMAL")) {

            return new NormalChicken(
                    x,
                    y,
                    level.getNormalLives()
            );

        } else if(type.equals("FAST")) {

            return new FastChicken(
                    x,
                    y,
                    level.getFastLives()
            );

        } else if(type.equals("ZIGZAG")) {

            return new ZigzagChicken(
                    x,
                    y,
                    level.getZigzagLives()
            );

        } else if(type.equals("SHOOTER")) {

            return new ShooterChicken(
                    x,
                    y,
                    level.getShooterLives()
            );
        }

        return new NormalChicken(
                x,
                y,
                level.getNormalLives()
        );
    }

    // create one cell and attach a chicken to it
    private void addChickenToCell(
            int row, int col,
            int x, int y,
            String type){

        //create a cell for the chicken
        Cell cell = new Cell(
                row,
                col,
                x,
                y,
                level.getCellCounter(),
                type
        );


        cells[row][col] = cell;

        //create first chicken for cell
        Chicken chicken = createChickenByType(type,x,y);

        chicken.setCell(cell);
        chickens.add(chicken);

    }

    //move whole formation and sync chicken with cells
    public void moveGroup(int panelWidth){

        double dy = 0;

        //move with level speed
        double dx = groupSpeed * moveDirection;

        boolean hitBorder = false;

        int chickenWidth = 50;

        double leftMost = Double.MAX_VALUE;
        double rightMost = -Double.MAX_VALUE;

        //find left and right edges of the formation
        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 8; col++) {

                Cell cell = cells[row][col];

                if (cell != null && cell.hasMoreChickens()) {

                    if (cell.getX() < leftMost) {
                        leftMost = cell.getX();
                    }

                    if (cell.getX() + chickenWidth > rightMost) {
                        rightMost = cell.getX() + chickenWidth;
                    }
                }
            }
        }

            //stop if all cells have been completely cleared
            if (leftMost == Double.MAX_VALUE ||
                rightMost == -Double.MAX_VALUE) {
            return;
        }

        //prevent from leaving the screen
        if(moveDirection > 0 && rightMost + dx >= panelWidth){

            dx = panelWidth - rightMost;
            hitBorder = true;

        } else if(moveDirection < 0 && leftMost + dx <= 0){

            dx = -leftMost;
            hitBorder = true;

        }

        //change direction after touching border and move down
        if(hitBorder){

            dy = moveDownStep;
            moveDirection *= -1;

        }

        //move every cell as one formation
        for(int row = 0; row < 5; row++){

            for(int col = 0; col < 8; col++){

                Cell cell = cells[row][col];

                if(cell != null){

                    cell.move(dx, dy);

                }
            }
        }

        //sync chickens with cells
        for (Chicken chicken : chickens) {

            if (chicken.isMovingToCell()) {

                //follow horizontal grid movement
                chicken.moveWithFormation(dx, 0);

                //continue flying toward the moving target cell
                chicken.moveToCell();

            } else {
                //chickens inside the formation follow their cells directly
                chicken.updateInFormation();
            }
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

    //last chickens (bottom chickens)
    public ArrayList<Chicken> getBottomChickens(){

        ArrayList<Chicken> bottomChickens = new ArrayList<>();

        for(int col = 0 ; col < 8 ; col++) {

            Chicken bottom = null;

            for (Chicken chicken : chickens) {

                if(!chicken.isMovingToCell() &&
                        chicken.getCell() != null &&
                        chicken.getCell().getCol() == col) {

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

    //spawn another chicken if the cell counter is not finished
    public void replaceChickenIfNeeded(
            Chicken deadChicken,
            int panelWidth){

        Cell cell = deadChicken.getCell();

        //if chicken doesnt have a cell it cannot be replaced
        if(cell == null){
            return;
        }

        cell.decreaseCounter();

        if(cell.hasMoreChickens()){

            int startX;
            //choose randomly one of the two corners
            if(Math.random() < 0.5){
                startX = 0;
            } else{
                startX = panelWidth - deadChicken.getWidth();
            }

            int startY = 0;

            Chicken newChicken = createChickenByType(
                            cell.getType(),
                            startX,
                            startY
                    );

            //give the new chicken the same cell
            newChicken.setCell(cell);

            //start moving chicken to the cell
            newChicken.startMovingToCell();
            chickens.add(newChicken);

        }
    }

}
