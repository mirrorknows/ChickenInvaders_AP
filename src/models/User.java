package models;
//user information
public class User {

    private String username;
    private String password;
    private int id;
    private int highScore;
    private int lastLevel;
    //sound settings
    private boolean musicOn;
    private boolean shotSoundOn;
    private boolean crashSoundOn;
    private boolean gameOverSoundOn;

    private String selectedPlane = "Default";

    public User (String username, String password, int id, int highScore, int lastLevel,
                 boolean musicOn, boolean shotSoundOn, boolean crashSoundOn, boolean gameOverSoundOn){
        this.username = username;
        this.password = password;
        this.id = id;
        this.highScore = highScore;
        this.lastLevel = lastLevel;
        this.musicOn = musicOn;
        this.shotSoundOn = shotSoundOn;
        this.crashSoundOn = crashSoundOn;
        this.gameOverSoundOn = gameOverSoundOn;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public int getId() {
        return id;
    }
    public int getHighScore() {
        return highScore;
    }
    public int getLastLevel() {
        return lastLevel;
    }
    public boolean isMusicOn() {
        return musicOn;
    }
    public boolean isCrashSoundOn() {
        return crashSoundOn;
    }
    public boolean isGameOverSoundOn() {
        return gameOverSoundOn;
    }
    public boolean isShotSoundOn() {
        return shotSoundOn;
    }

    public String getSelectedPlane() {
        return selectedPlane;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }
    public void setCrashSoundOn(boolean crashSoundOn) {
        this.crashSoundOn = crashSoundOn;
    }
    public void setGameOverSoundOn(boolean gameOverSoundOn) {
        this.gameOverSoundOn = gameOverSoundOn;
    }
    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }
    public void setShotSoundOn(boolean shotSoundOn) {
        this.shotSoundOn = shotSoundOn;
    }
    public void setSelectedPlane(String selectedPlane) {
        this.selectedPlane = selectedPlane;
    }
}
