package ui;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//this class manages the game panel and game loop

public class GamePanel extends JPanel implements KeyListener {

    //player object
    private Player player;

    //game loop timer
    private Timer gameTimer;

    //move with keys
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    //all bullets
    private ArrayList<Bullets> bullets;

    private ChickenManager chickenManager;

    private boolean gameOver = false;
    private boolean gameWon = false;

    private int scores = 0;

    private ArrayList<Egg> eggs;
    private long lastEggDropTime = 0;

    //shooter chicken attach time
    private long lastShooterShotTime = 0 ;
    private final long shooterShotDelay = 2000;

    //all explosions in the game
    private ArrayList<Explosion> explosions;

    //all power ups in the game
    private ArrayList<PowerUp> powerUps;

    //time between two egg drops
    private long eggDropDelay = 3000;

    private int currentLevel = 1;

    //boss object for boss levels
    private Boss boss;
    private boolean bossLevel = false;

    //checks if current boss is final boss
    private boolean finalBossLevel = false;

    //max powerups on the screen
    private final int maxPowerUpsOnScreen = 3 ;

    //freeze bomb settings
    private boolean freezeActive = false;
    private long freezeEndTime = 0;

    public GamePanel(){

        setBackground(Color.BLACK);

        player = new Player(400,500);

        bullets = new ArrayList<>();

        eggs = new ArrayList<>();

        powerUps = new ArrayList<>();

        explosions = new ArrayList<>();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        gameTimer = new Timer(16, e -> {

            if(upPressed)
                player.moveUp();

            if(downPressed)
                player.moveDown();

            if(leftPressed)
                player.moveLeft();

            if(rightPressed)
                player.moveRight();

            player.stayInsideScreen(getWidth(), getHeight());

            if(!isFreezeActive()){
                if (bossLevel) {
                    boss.move(getWidth());
                } else {
                    chickenManager.moveGroup(getWidth());
                }
            }


            long currentTime = System.currentTimeMillis();

            if(!bossLevel && !isFreezeActive()
                    && currentTime - lastEggDropTime >= eggDropDelay){

                ArrayList<Chicken> bottomChickens = chickenManager.getBottomChickens();

                if(!bottomChickens.isEmpty()){

                    int randomIndex = (int)(Math.random() * bottomChickens.size());

                    Chicken chicken = bottomChickens.get(randomIndex);

                    eggs.add(new Egg(
                            chicken.getX() + chicken.getWidth()/2,
                            chicken.getY() + chicken.getHeight()
                    ));

                    lastEggDropTime = currentTime;
                }
            }

            //shooter chicken attack
            if(!bossLevel && !isFreezeActive()
                    && currentTime - lastShooterShotTime >= shooterShotDelay){

                shooterChickenAttack();
                lastShooterShotTime = currentTime;
            }

            for (int i = 0; i < eggs.size(); i++) {

                Egg egg = eggs.get(i);

                if(!isFreezeActive()){
                    egg.drop();
                }

                if(
                                egg.getX() < 0 ||
                                egg.getX() > getWidth() ||
                                egg.getY() < 0 ||
                                egg.getY() > getHeight()
                ){
                    eggs.remove(i);
                    i--;
                }
            }

            for(int i = 0 ; i < powerUps.size(); i++){

                PowerUp powerUp = powerUps.get(i);

                powerUp.fall();

                //remove power up if it leaves screen
                if(powerUp.getY() > getHeight()){

                    powerUps.remove(i);
                    i--;
                    continue;

                }
                if(powerUp.hitPlayer(player)){

                    if (powerUp.getType().equals("ADD_FIRE")) {
                        player.addFire();

                    } else if (powerUp.getType().equals("EXTRA_LIFE")) {
                        player.addLife();

                    } else if (powerUp.getType().equals("RAPID_FIRE")) {
                        player.activateRapidFire();

                    } else if (powerUp.getType().equals("SHIELD")) {
                        player.activateShield();

                    }else if (powerUp.getType().equals("FREEZE_BOMB")) {
                        activateFreezeBomb();

                    }

                    powerUps.remove(i);
                    i--;                }
            }
            //remove bullets that leave the screen
            for (int i = 0; i < bullets.size(); i++) {

                Bullets bullet = bullets.get(i);

                bullet.fly();

                if (bullet.getY() < 0) {

                    bullets.remove(i);

                    i--;

                }
            }

            if(!bossLevel){
                //check strike between bullet and chicken
                for(int i = 0 ; i < bullets.size(); i++){

                    Bullets bullet = bullets.get(i);

                    for(int j = 0 ; j < chickenManager.getChickens().size(); j++){

                        Chicken chicken = chickenManager.getChickens().get(j);

                        //bullet hit the chicken
                        if (chicken.isHit(bullet)) {

                            chicken.takeDamage();

                            //remove bullet after strike
                            bullets.remove(i);
                            i--;

                            //remove chicken / add score (if it has no lives left)
                            if (chicken.getLives() <= 0) {

                                //increase score
                                scores += chicken.getScore();

                                //add explosion when chicken dies
                                explosions.add(new Explosion(
                                        chicken.getX() + chicken.getWidth() / 2,
                                        chicken.getY() + chicken.getHeight() / 2,
                                        60
                                ));

                                //20% chance to drop powerup + limit number of powerups
                                if (Math.random() < 0.20 && powerUps.size() < maxPowerUpsOnScreen) {

                                    String type;

                                    int randomPower = (int)(Math.random() * 5);

                                    if(randomPower == 0) {

                                        //if add fire is full, give rapid fire instead
                                        if(player.getFireCount() < player.getMaxFireCount()) {
                                            type = "ADD_FIRE";
                                        } else {
                                            type = "RAPID_FIRE";
                                        }

                                    } else if(randomPower == 1) {

                                        //if lives are full, give shield instead
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
                                    ));
                                }

                                chickenManager.getChickens().remove(j);
                                chickenManager.replaceChickenIfNeeded(chicken, getWidth());
                                j--;
                            }
                            break;

                        }
                    }
                }
            }

            if (bossLevel && boss != null) {

                if(boss.canAttack() && !isFreezeActive()){

                    if(finalBossLevel){

                        finalBossAttack();

                    } else {

                        bossLevel4Attack();

                    }
                }
                for (int i = 0; i < bullets.size(); i++) {

                    Bullets bullet = bullets.get(i);

                    if (boss.isHit(bullet)) {

                        boss.takeDamage();

                        bullets.remove(i);
                        i--;

                        if (boss.isDead()) {

                            //add explosion when boss dies
                            explosions.add(new Explosion(
                                    boss.getX() + boss.getWidth() / 2,
                                    boss.getY() + boss.getHeight() / 2,
                                    180
                            ));

                            if(finalBossLevel){

                                scores += 1000;
                                bossLevel = false;
                                finalBossLevel = false;
                                boss = null;

                                gameWon = true;
                                gameTimer.stop();

                            }else{

                                scores += 500;
                                currentLevel = 5;

                                bossLevel = false;
                                boss = null;

                                startLevel();
                            }
                        }


                        break;
                    }
                }
            }

            //player hits chicken
            for(Chicken chicken : chickenManager.getChickens()){
                if(chicken.hitPlayer(player)){

                    if(player.canTakeDamage()){
                        player.takeDamage();

                        if(player.getLives() <= 0){
                            gameOver = true;
                            gameTimer.stop();
                        }
                    }
                    break;
                }
            }

            //check strike between egg and player(spaceship)
            for(int i = 0 ; i < eggs.size() ; i++){
                Egg egg = eggs.get(i);

                if(player.isHit(egg)){

                    //add explosion when egg hits player
                    explosions.add(new Explosion(
                            egg.getX() + egg.getWidth() / 2,
                            egg.getY() + egg.getHeight() / 2,
                            50
                    ));

                    eggs.remove(i);
                    i--;

                    if(player.canTakeDamage()){

                        if (!player.isShieldActive()){

                            player.takeDamage();

                            if(player.getLives() <= 0) {
                                gameOver = true;
                                gameTimer.stop();
                            }

                        }
                    }

                }
            }

            //go to next level after killing all chickens
            if (!gameOver && !gameWon && !bossLevel && chickenManager.getChickens().isEmpty()) {

                if (currentLevel == 3) {
                    scores += 200;
                    currentLevel = 4;
                    startBossLevel4();
                } else if(currentLevel == 7){
                    scores += 200;
                    currentLevel = 8;
                    startFinalBossLevel8();
                }else {
                    scores += 200;
                    currentLevel++;
                    startLevel();
                }
            }

            if (!bossLevel && chickenManager.reachedBottom(getHeight())) {

                gameOver = true;

                gameTimer.stop();

                repaint();

            }
            //remove finished explosions
            for(int i = 0; i < explosions.size(); i++) {

                Explosion explosion = explosions.get(i);

                if(explosion.isFinished()) {

                    explosions.remove(i);
                    i--;

                }
            }
            repaint();
        });

        startLevel();

        gameTimer.start();
    }

    //draw all game objects
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.fillRect(
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight()
        );
        //draw shield around player
        if(player.isShieldActive()) {

            g.setColor(Color.CYAN);

            g.drawOval(
                    player.getX() - 10,
                    player.getY() - 10,
                    player.getWidth() + 20,
                    player.getHeight() + 20
            );

        }
        g.setColor(Color.YELLOW);

        for(Bullets bullet : bullets){

            g.fillRect(
                    bullet.getX(),
                    bullet.getY(),
                    bullet.getWidth(),
                    bullet.getHeight()
            );

        }

        if (bossLevel && boss != null) {

            boss.draw(g);

        } else {

            for (Chicken chicken : chickenManager.getChickens()) {
                g.setColor(chicken.getColor());

                g.fillRect(
                        chicken.getX(),
                        chicken.getY(),
                        chicken.getWidth(),
                        chicken.getHeight()
                );
            }
        }

        g.setColor(Color.WHITE);

        for (Egg egg : eggs) {

            g.fillOval(
                    egg.getX(),
                    egg.getY(),
                    egg.getWidth(),
                    egg.getHeight()
            );
        }

        //draw explosions
        for(Explosion explosion : explosions) {

            explosion.draw(g);

        }

        //draw power ups
        for(PowerUp powerUp : powerUps){
            powerUp.draw(g);
        }


        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Score : " + scores, 20, 30);

        //lives
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Lives : " + player.getLives(), 20, 60);

        //level
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level : " + currentLevel, 20, 90);

        //fire count
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Fire : " + player.getFireCount(), 20, 120);

        //freeze status
        if(isFreezeActive()) {

            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Freeze", 20, 190);

        }

        //rapid fire status
        if(player.isRapidFireActive()) {

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Rapid Fire", 20, 150);

        }

        //game over
        if (gameOver) {

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 60));

            String text = "GAME OVER";

            FontMetrics fm = g.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;

            g.drawString(text, x, y);
        }

        //win screen
        //win screen
        if (gameWon) {

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 60));

            String text = "YOU WIN";

            FontMetrics fm = g.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;

            g.drawString(text, x, y);

            g.setFont(new Font("Arial", Font.BOLD, 25));

            String scoreText = "Final Score : " + scores;

            int scoreX = (getWidth() - g.getFontMetrics().stringWidth(scoreText)) / 2;

            g.drawString(scoreText, scoreX, y + 50);
        }
    }

    //create chicken for levels
    private void startLevel(){

        //level object
        Level level = new Level(currentLevel);

        //level settings
        eggDropDelay = level.getEggDropDelay();

        chickenManager = new ChickenManager(level);
        chickenManager.createFormation();

        eggs.clear();
        bullets.clear();
        powerUps.clear();

        lastShooterShotTime = System.currentTimeMillis();
    }

    //start first boss level after level 3
    private void startBossLevel4() {

        bossLevel = true;
        finalBossLevel = false;

        boss = new Boss(300, 80, 50);

        eggs.clear();
        bullets.clear();

        lastEggDropTime = System.currentTimeMillis();
    }

    //start final boss level 8
    private void startFinalBossLevel8(){

        bossLevel = true;
        finalBossLevel = true;

        boss = new FinalBoss(300,80,100);

        eggs.clear();
        bullets.clear();
        powerUps.clear();

        lastEggDropTime = System.currentTimeMillis();
    }
    //boss level 4 attacks in four directions
    private void bossLevel4Attack(){

        int centerX = boss.getX() + boss.getWidth() / 2;
        int centerY = boss.getY() + boss.getHeight() / 2;

        //up
        eggs.add(new Egg(centerX, centerY, 0, -4));

        //down
        eggs.add(new Egg(centerX, centerY, 0, 4));

        //left
        eggs.add(new Egg(centerX, centerY, -4, 0));

        //right
        eggs.add(new Egg(centerX, centerY, 4, 0));
    }

    //final boss attacks in eight directions
    private void finalBossAttack(){

        int centerX = boss.getX() + boss.getWidth() / 2;
        int centerY = boss.getY() + boss.getHeight() / 2;

        //up
        eggs.add(new Egg(centerX, centerY, 0, -4));

        //down
        eggs.add(new Egg(centerX, centerY, 0, 4));

        //left
        eggs.add(new Egg(centerX, centerY, -4, 0));

        //right
        eggs.add(new Egg(centerX, centerY, 4, 0));

        //up-left
        eggs.add(new Egg(centerX, centerY, -3, -3));

        //up-right
        eggs.add(new Egg(centerX, centerY, 3, -3));

        //down-left
        eggs.add(new Egg(centerX, centerY, -3, 3));

        //down-right
        eggs.add(new Egg(centerX, centerY, 3, 3));
    }

    //choose one chicken and shoot
    private void shooterChickenAttack() {

        if(chickenManager.getChickens().isEmpty()) {
            return;
        }

        int randomIndex = (int)(Math.random() * chickenManager.getChickens().size());

        Chicken chicken = chickenManager.getChickens().get(randomIndex);

        Egg egg = chicken.shootAtPlayer(player);

        if(egg != null) {

            eggs.add(egg);

        }
    }

    //freeze bomb for 3 sec
    private void activateFreezeBomb() {

        freezeActive = true;

        freezeEndTime = System.currentTimeMillis() + 3000;

    }

    //check freeze time
    private boolean isFreezeActive() {

        if(freezeActive &&
                System.currentTimeMillis() > freezeEndTime) {

            freezeActive = false;

        }

        return freezeActive;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //handle pressed keys
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        long currentTime = System.currentTimeMillis();
        switch (key){

            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                break;

            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;

            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;

            case KeyEvent.VK_D:
                rightPressed = true;
                break;case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;

            case KeyEvent.VK_SPACE:

                if (currentTime - player.getLastShotTime() >= player.getCurrentShootDelay()){

                    int fireCount = player.getFireCount();

                    int spacing = 12;

                    int startX = player.getX() + player.getWidth() / 2 -
                            ((fireCount - 1) * spacing) / 2;

                    for (int i = 0; i < fireCount; i++) {

                        bullets.add(new Bullets(
                                startX + i * spacing,
                                player.getY()
                        ));

                    }

                    player.setLastShotTime(currentTime);
                }
                break;
        }
    }

    //handles released keys
    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key){

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = false;
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
        }
    }
}

