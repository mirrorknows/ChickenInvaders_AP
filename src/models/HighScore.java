package models;

public class HighScore {

    private String username;
    private int score;

    public HighScore(String username, int score){

        this.username = username;
        this.score = score;

    }

    public String getUsername(){
        return username;
    }

    public int getScore() {
        return score;
    }
}
