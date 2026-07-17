package models;

public class ZigzagChicken extends Chicken {

    private double angle = 0;

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

        double targetX = cell.getX();
        double targetY = cell.getY();

        double distanceX = targetX - exactX;
        double distanceY = targetY - exactY;

        double distance = Math.sqrt(
                distanceX * distanceX +
                        distanceY * distanceY
        );

        if (distance <= spawnSpeed) {

            exactX = targetX;
            exactY = targetY;

            movingToCell = false;

            return;
        }

        //move main position toward cell
        exactX += distanceX / distance * spawnSpeed;
        exactY += distanceY / distance * spawnSpeed;

        angle += 0.15;

        int offset =
                (int) (moveRange * Math.sin(angle));

        x = (int) Math.round(exactX);

        y = Math.max(
                0,
                (int) Math.round(exactY) + offset
        );
    }

    //continue zigzag movement inside formation
    @Override
    public void updatePositionFromCell() {

        if (cell == null) {
            return;
        }

        angle += 0.15;

        exactX = cell.getX();
        exactY = cell.getY();

        int offset =
                (int) (moveRange * Math.sin(angle));

        x = (int) exactX;
        y = (int) exactY + offset;
    }

    @Override
    public int getScore() {
        return 20;
    }
}