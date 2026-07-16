package models;

import java.awt.*;

//this class is for normal chicken (enemy)
public class NormalChicken extends Chicken{

    public NormalChicken(int x, int y, int lives){
        super(
                 x, y,
                35, 35,
                1,
                lives, "/images/enemies/normal_chicken.png"
        );
    }

    //group move in direction (right or left)

    @Override
    public void move(int moveDirection, double groupSpeed) {

        x += (int )(speed* groupSpeed * moveDirection);

    }
    //score player gains after killing normal chicken
    @Override
    public int getScore(){
        return 10;
    }

}
