package models;

import java.awt.*;

//this class is for normal chicken (enemy)
public class NormalChicken extends Chicken{

    public NormalChicken(int x, int y, int lives){
        super(
                 x, y,
                45, 45,
                6,
                lives, "/images/enemies/normal_chicken.png"
        );
    }

    //score player gains after killing normal chicken
    @Override
    public int getScore(){
        return 10;
    }

}
