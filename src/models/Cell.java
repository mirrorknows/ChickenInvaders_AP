package models;

public class Cell {

    //row and column in the formation
    private int row;
    private int col;

    //current cell position
    private double x;
    private double y;

    //number of chickens that must be defeated in this cell
    private int counter;

    //type when a replacement chicken is created
    private String type;


    //create a cell with position counter and chicken type
    public Cell(int row, int col, double x, double y, int counter, String type){

        this.row = row;
        this.col = col;

        this.x = x;
        this.y = y;

        this.counter = counter;

        this.type = type;
    }

    // decrease the number of chickens left in this cell
    public void decreaseCounter(){
        counter--;
    }

    //check if this cell needs another replacement chicken
    public boolean hasMoreChickens(){
        return counter > 0;
    }

    //move the cell together
    public void move(double dx, double dy){
        x += dx;
        y += dy;
    }

    public int getCol(){
        return col;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public String getType(){
        return type;
    }


}
