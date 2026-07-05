package models;

//this class is for normal chicken (enemy)
public class NormalChicken extends Chicken{

    public NormalChicken(int x, int y){
        super(x,y,50,50,1,2);
    }

    //group move in direction (right or left)
    @Override
    public void move(int moveDirection){

        x += speed* moveDirection;
    }

    //score player gains after killing normal chicken
    @Override
    public int getScore(){
        return 10;
    }
}
