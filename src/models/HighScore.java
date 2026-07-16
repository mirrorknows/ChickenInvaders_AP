package models;

public class HighScore {

    private String username;
    private int score;
    private int level;
    private String playedTime;

    public HighScore(String username, int score, int level, String playedTime){

        this.username = username;
        this.score = score;
        this.level = level;
        this.playedTime = playedTime;

    }

    public String getUsername(){
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public String getPlayedTime() {
        return playedTime;
    }
}
