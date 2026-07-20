package models;


public class FastChicken extends Chicken {
    //fast chicken enters the formation faster
    public FastChicken(int x, int y, int lives) {

        super(
                x, y,
                45, 45,
                12,
                lives, "/images/enemies/fast_chicken.png"
        );

    }
    //score player gains after killing fast chicken
    @Override
    public int getScore(){
        return 15;
    }

}
