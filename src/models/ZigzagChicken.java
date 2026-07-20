package models;

public class ZigzagChicken extends Chicken {

    //control zigzag move
    private double angle = 0;

    //zigzag move range
    private final int moveRange = 10;

    public ZigzagChicken(int x, int y, int lives) {

        super(
                x,
                y,
                50,
                50,
                6,
                lives,
                "/images/enemies/zigzag_chicken.png"
        );
    }

    //zigzag movement toward cell
    @Override
    public void moveToCell() {

        if (cell == null) {
            return;
        }

        //get the current cell position
        double targetX = cell.getX();
        double targetY = cell.getY();

        //distance to the cell
        double distanceX = targetX - exactX;
        double distanceY = targetY - exactY;

        //total distance
        double distance = Math.sqrt(
                distanceX * distanceX +
                        distanceY * distanceY
        );

        //place the chicken on the cell when it gets close
        if (distance <= spawnSpeed) {

            exactX = targetX;
            exactY = targetY;

            x = (int)Math.round(exactX);
            y = (int)Math.round(exactY);
            movingToCell = false;

            return;
        }

        //move to the cell
        exactX += distanceX / distance * spawnSpeed;
        exactY += distanceY / distance * spawnSpeed;

        //continue zigzag movement
        angle += 0.15;

        //calculate up and down movement
        int zigzagY  = (int) (moveRange * Math.sin(angle));

        x = (int) Math.round(exactX);

        y = Math.max(
                0,
                (int) Math.round(exactY) + zigzagY
        );
    }

    //continue zigzag movement inside formation
    @Override
    public void updateInFormation() {

        if (cell == null) {
            return;
        }

        //continue zigzag movement
        angle += 0.15;

        exactX = cell.getX();
        exactY = cell.getY();

        //calculate up and down movement
        int zigzagY = (int) (moveRange * Math.sin(angle));

        x = (int)Math.round(exactX);
        y = (int)Math.round(exactY) + zigzagY;
    }
    //score player gains after killing zigzag chicken
    @Override
    public int getScore() {
        return 20;
    }
}
