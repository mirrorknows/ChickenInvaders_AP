package managers;

import models.Chicken;
import models.Player;
import models.PowerUp;

import java.awt.*;
import java.util.ArrayList;

//manages all power ups in the game
public class PowerUpManager {

    //all power ups on the screen
    private final ArrayList<PowerUp> powerUps;

    //maximum power ups on the screen
    private final int maxPowerUpsOnScreen = 3;

    //freeze bomb settings
    private boolean freezeActive;
    private long freezeEndTime;

    public PowerUpManager() {

        powerUps = new ArrayList<>();

        freezeActive = false;
        freezeEndTime = 0;
    }

    //move power ups and apply collected effects
    public void update(Player player, int panelHeight) {

        for(int i = 0; i < powerUps.size(); i++) {

            PowerUp powerUp = powerUps.get(i);
            powerUp.fall();

            //remove power up if it leaves the screen
            if(powerUp.getY() > panelHeight) {

                powerUps.remove(i);
                i--;
                continue;
            }

            //apply power up that player catch
            if(powerUp.hitPlayer(player)) {

                applyPowerUp(powerUp, player);
                powerUps.remove(i);
                i--;
            }
        }
    }

    //apply the collected power up
    private void applyPowerUp(PowerUp powerUp, Player player) {

        String type = powerUp.getType();

        if(type.equals("ADD_FIRE")) {

            player.addFire();

        } else if(type.equals("EXTRA_LIFE")) {

            player.addLife();

        } else if(type.equals("RAPID_FIRE")) {

            player.activateRapidFire();

        } else if(type.equals("SHIELD")) {

            player.activateShield();

        } else if(type.equals("FREEZE_BOMB")) {

            activateFreezeBomb();
        }
    }

    //create a random power up
    public void createPowerUp(Chicken chicken, Player player) {

        //20 percent chance and max 3 power ups
        if(Math.random() >= 0.20 || powerUps.size() >= maxPowerUpsOnScreen) {
            return;
        }

        String type;

        int randomPower = (int)(Math.random() * 5);

        if(randomPower == 0) {

            //give rapid fire if add fire is full
            if(player.getFireCount() < player.getMaxFireCount()) {

                type = "ADD_FIRE";

            } else {
                type = "RAPID_FIRE";
            }

        } else if(randomPower == 1) {

            //give shield if lives are full
            if(player.getLives() < player.getMaxLives()) {

                type = "EXTRA_LIFE";

            } else {
                type = "SHIELD";
            }

        } else if(randomPower == 2) {
            type = "RAPID_FIRE";

        } else if(randomPower == 3) {
            type = "SHIELD";

        } else {
            type = "FREEZE_BOMB";
        }

        powerUps.add(new PowerUp(
                        chicken.getX(),
                        chicken.getY(),
                        type
                )
        );
    }

    //active freeze bomb for 3 sec
    private void activateFreezeBomb() {

        freezeActive = true;
        freezeEndTime = System.currentTimeMillis() + 3000;
    }

    //check if freeze bomb is still active
    public boolean isFreezeActive() {

        if(freezeActive && System.currentTimeMillis() > freezeEndTime) {

            freezeActive = false;
        }

        return freezeActive;
    }

    //reset power ups when a new level starts
    public void resetForNewLevel(Player player) {

        powerUps.clear();
        freezeActive = false;
        freezeEndTime = 0;

        player.resetTemporaryPowerUps();
    }

    //adjust freeze timer after pause
    public void addPausedTime(long pauseTime) {

        if(freezeActive) {
            freezeEndTime += pauseTime;
        }
    }

    //draw all power ups
    public void draw(Graphics g) {

        for(PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }
    }
}