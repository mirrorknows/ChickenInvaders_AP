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

                        int randomType = (int) (Math.random() * 4);

                        switch (randomType) {

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

                x += 45;
            }

            y += 45;
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

    // create one grid cell and attach a chicken to it
    private void addChickenToCell(
            int row,
            int col,
            int x,
            int y,
            String type){


        Cell cell = new Cell(
                row,
                col,
                x,
                y,
                level.getCellCounter(),
                type
        );


        cells[row][col] = cell;


        Chicken chicken =
                createChickenByType(type,x,y);


        chicken.setCell(cell);


        chickens.add(chicken);

    }

    //move whole cell and sync chicken with cells
    public void moveGroup(int panelWidth){

        int dx = (int)(groupSpeed * moveDirection);
        int dy = 0;

        int leftMost = Integer.MAX_VALUE;
        int rightMost = Integer.MIN_VALUE;

        for(int row=0; row<5; row++){
            for(int col=0; col<8; col++){

                Cell cell = cells[row][col];

                if(cell != null && cell.hasMoreChickens()){

                    leftMost = Math.min(leftMost, cell.getX());

                    rightMost = Math.max(rightMost, cell.getX()+35);

                }
            }
        }

        if(moveDirection < 0 && leftMost + dx <= 120){

            dx = 120-leftMost;
            moveDirection = 1;
            dy = moveDownStep;
        }

        else if(moveDirection > 0 && rightMost + dx >= panelWidth-20){

            dx = panelWidth-20-rightMost;
            moveDirection = -1;
            dy = moveDownStep;
        }



        //move cells

        for(int row=0; row<5; row++){
            for(int col=0; col<8; col++){

                Cell cell = cells[row][col];

                if(cell != null){

                    cell.move(dx,dy);

                }
            }
        }

        //sync chickens with cells
        for(Chicken chicken : chickens){


            if(chicken.isMovingToCell()){
                chicken.moveToCell();
            } else{
                chicken.followCell();
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

    //craete and replace chicken
    public void replaceChickenIfNeeded(
            Chicken deadChicken,
            int panelWidth){

        Cell cell = deadChicken.getCell();

        if(cell == null){
            return;
        }

        cell.decreaseCounter();

        if(cell.hasMoreChickens()){

            int startX;

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

            newChicken.setCell(cell);
            newChicken.startMovingToCell();
            chickens.add(newChicken);

        }
    }
}
