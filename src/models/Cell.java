package models;

public class Cell {

    private int row;
    private int col;

    private int x;
    private int y;

    private int counter;

    private String type;


    public Cell(int row, int col, int x, int y, int counter, String type){

        this.row = row;
        this.col = col;

        this.x = x;
        this.y = y;

        this.counter = counter;

        this.type = type;
    }

    //getters
    public int getCol(){
        return col;
    }


    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }

    public String getType(){
        return type;
    }

    // decrease the number of chickens left for this cell
    public void decreaseCounter(){
        counter--;
    }

    //if this cell still needs to replace chicken
    public boolean hasMoreChickens(){
        return counter > 0;
    }

    //move grid cell
    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }
}
