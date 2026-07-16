package models;

import helpers.ImageLoader;

//final boss level 8
public class FinalBoss extends Boss{

    private long lastFinalAttack = 0;
    private final long finalAttackDelay = 1000;

    public FinalBoss(int x, int y, int lives){

        super(x, y, lives);

        image = ImageLoader.loadImage(
                "/images/enemies/boss2.png"
        );
    }

    @Override
    public boolean canAttack() {

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastFinalAttack >= finalAttackDelay){
            lastFinalAttack = currentTime;
            return true;
        }
    return false;
    }
}
