package models;
// game history data
public class GameHistory {

    //fields
    private int id;
    private int userId;
    private int level;
    private int score;
    private String playedTime;
    private boolean musicOn;
    private boolean shotSoundOn;
    private boolean crashSoundOn;
    private boolean gameOverSoundOn;
    //constructor
    public GameHistory(int id, int userId, int level, int score,
                       String playedTime, boolean musicOn, boolean shotSoundOn,
                       boolean crashSoundOn, boolean gameOverSoundOn){
        this.id = id;
        this.userId = userId;
        this.level = level;
        this.score = score;
        this.playedTime = playedTime;
        this.musicOn = musicOn;
        this.shotSoundOn = shotSoundOn;
        this.crashSoundOn = crashSoundOn;
        this.gameOverSoundOn = gameOverSoundOn;
    }

    //getters

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }
    public String getPlayedTime(){
        return  playedTime;
    }

    public boolean isShotSoundOn() {
        return shotSoundOn;
    }

    public boolean isCrashSoundOn() {
        return crashSoundOn;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public boolean isGameOverSoundOn() {
        return gameOverSoundOn;
    }

    //setters

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayedTime(String playedTime) {
        this.playedTime = playedTime;
    }

    public void setGameOverSoundOn(boolean gameOverSoundOn) {
        this.gameOverSoundOn = gameOverSoundOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public void setCrashSoundOn(boolean crashSoundOn) {
        this.crashSoundOn = crashSoundOn;
    }

    public void setShotSoundOn(boolean shotSoundOn) {
        this.shotSoundOn = shotSoundOn;
    }

}

