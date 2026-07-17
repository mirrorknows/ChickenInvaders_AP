package models;


public class FastChicken extends Chicken {

    public FastChicken(int x, int y, int lives) {

        super(
                x, y,
                45, 45,
                12,
                lives, "/images/enemies/fast_chicken.png"
        );

    }

    @Override
    public int getScore(){
        return 15;
    }

}
