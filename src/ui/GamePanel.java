package ui;

import helpers.ImageLoader;
import helpers.SoundManager;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import services.GameResultService;

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

    private boolean paused;

    //all bullets
    private ArrayList<Bullets> bullets;

    private ChickenManager chickenManager;

    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean gameSaved = false;
    private GameResultService gameResultService;


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

    //game background
    private Image backgroundImage;

    //player username
    private String username;

    public GamePanel(String username){

        if(username== null || username.trim().isEmpty()){
            this.username = "Guest";
        }else{
            this.username = username;
        }

        SoundManager.updateBackgroundMusic();

        backgroundImage = ImageLoader.loadImage(
                "/images/backgrounds/main_gameBG.png"
        );
        setBackground(Color.BLACK);

        player = new Player(400,500);

        bullets = new ArrayList<>();

        eggs = new ArrayList<>();

        powerUps = new ArrayList<>();

        explosions = new ArrayList<>();

        gameResultService = new GameResultService();


        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        gameTimer = new Timer(16, e -> {

            if(paused){
                repaint();
                return;
            }
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
                                        80
                                ));

                                SoundManager.playExplosionSound();

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

                            int explosionSize;

                            if(finalBossLevel) {
                                explosionSize = 260;
                            } else {
                                explosionSize = 180;
                            }

                            //add explosion when boss dies
                            explosions.add(new Explosion(
                                    boss.getX() + boss.getWidth() / 2,
                                    boss.getY() + boss.getHeight() / 2,
                                    explosionSize
                            ));

                            SoundManager.playExplosionSound();

                            if(finalBossLevel){

                                scores += 1000;

                                bossLevel = false;
                                finalBossLevel = false;
                                boss = null;

                                finishGame(true);

                            } else {

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

                    if(player.canTakeDamage() && !player.isShieldActive()){
                        player.takeDamage();

                        //explosion after hitting chicken
                        explosions.add(new Explosion(
                                player.getX() + player.getWidth() / 2,
                                player.getY() + player.getHeight() / 2,
                                80
                        ));

                        SoundManager.playExplosionSound();

                        if(player.getLives() <= 0){
                            finishGame(false);
                        }
                    }
                    break;
                }
            }

            //check strike between egg and player
            for(int i = 0; i < eggs.size(); i++){

                Egg egg = eggs.get(i);

                if(player.isHit(egg)){

                    eggs.remove(i);
                    i--;

                    if(player.canTakeDamage()
                            && !player.isShieldActive()){

                        player.takeDamage();

                        //explosion after losing life
                        explosions.add(new Explosion(
                                egg.getX() + egg.getWidth() / 2,
                                egg.getY() + egg.getHeight() / 2,
                                50
                        ));

                        SoundManager.playExplosionSound();

                        if(player.getLives() <= 0){
                            finishGame(false);
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

                finishGame(false);
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
        //draw game background
        g.drawImage(
                backgroundImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );

        //draw shield
        if(player.isShieldActive()) {

            int shieldSize =
                    Math.max(
                            player.getWidth(),
                            player.getHeight()
                    ) + 20;

            int shieldX =
                    player.getX()
                            + player.getWidth() / 2
                            - shieldSize / 2;

            int shieldY =
                    player.getY()
                            + player.getHeight() / 2
                            - shieldSize / 2;

            //transparent shield
            g.setColor(new Color(0, 200, 255, 60));

            g.fillOval(
                    shieldX,
                    shieldY,
                    shieldSize,
                    shieldSize
            );

            //shield border
            g.setColor(new Color(100, 230, 255));

            g.drawOval(
                    shieldX,
                    shieldY,
                    shieldSize,
                    shieldSize
            );
        }

        //draw player
        player.draw(g);

        //draw bullets
        for(Bullets bullet : bullets){

            bullet.draw(g);
        }
        if (bossLevel && boss != null) {

            boss.draw(g);

        } else {

            for(Chicken chicken : chickenManager.getChickens()){

                chicken.draw(g);
            }
        }

        //draw eggs
        for(Egg egg : eggs){

            egg.draw(g);
        }

        //draw explosions
        for(Explosion explosion : explosions) {

            explosion.draw(g);

        }

        //draw power ups
        for(PowerUp powerUp : powerUps){
            powerUp.draw(g);
        }

        //draw game information
        drawGameInfo(g);

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

        //pause screen
        if(paused){

            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));

            String text = "PAUSED";

            FontMetrics fm = g.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;

            g.drawString(text, x, y);

            g.setFont(new Font("Arial", Font.BOLD, 22));

            String info = "Press P to Resume";
            int infoX = (getWidth() - g.getFontMetrics().stringWidth(info)) / 2;

            g.drawString(info, infoX, y + 45);
        }
    }
    //draw game information
    private void drawGameInfo(Graphics g) {

        int x = 20;
        int y = 35;
        int gap = 27;

        g.setFont(new Font("Arial", Font.BOLD, 18));

        //username
        g.setColor(new Color(100, 220, 255));
        g.drawString("PLAYER : " + username, x, y);
        y += gap;

        //score
        g.setColor(Color.WHITE);
        g.drawString("SCORE : " + scores, x, y);
        y += gap;

        //level
        g.setColor(new Color(210, 170, 255));
        g.drawString("LEVEL : " + currentLevel, x, y);
        y += gap;

        //lives
        g.setColor(new Color(255, 100, 120));
        g.drawString("LIVES : " + player.getLives(), x, y);
        y += gap;

        //fire count
        g.setColor(new Color(255, 220, 80));
        g.drawString("FIRE : " + player.getFireCount(), x, y);
        y += gap;

        //power up status
        g.setColor(new Color(100, 255, 170));

        if(player.isRapidFireActive()) {

            g.drawString("RAPID FIRE", x, y);
            y += gap;
        }

        if(player.isShieldActive()) {

            g.drawString("SHIELD", x, y);
            y += gap;
        }

        if(isFreezeActive()) {

            g.drawString("FREEZE", x, y);
        }
    }
    //create chicken for levels
    private void startLevel(){

        resetTemporaryPowerUps();

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

        resetTemporaryPowerUps();

        bossLevel = true;
        finalBossLevel = false;

        boss = new Boss(300, 80, 50);

        eggs.clear();
        bullets.clear();

        lastEggDropTime = System.currentTimeMillis();
    }

    //start final boss level 8
    private void startFinalBossLevel8(){

        resetTemporaryPowerUps();

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
        eggs.add(new Egg(centerX, centerY, 0, -5));

        //down
        eggs.add(new Egg(centerX, centerY, 0, 5));

        //left
        eggs.add(new Egg(centerX, centerY, -5, 0));

        //right
        eggs.add(new Egg(centerX, centerY, 5, 0));

        //up-left
        eggs.add(new Egg(centerX, centerY, -4, -4));

        //up-right
        eggs.add(new Egg(centerX, centerY, 4, -4));

        //down-left
        eggs.add(new Egg(centerX, centerY, -4, 4));

        //down-right
        eggs.add(new Egg(centerX, centerY, 4, 4));
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

    //stop temporary power ups
    private void resetTemporaryPowerUps() {

        freezeActive = false;
        freezeEndTime = 0;

        player.resetTemporaryPowerUps();
    }

    //return to main menu
    private void returnToMainMenu(){

        gameTimer.stop();

        Window window = SwingUtilities.getWindowAncestor(this);

        new MainMenu();

        if(window != null){

            window.dispose();

        }
    }

    private void saveGameResult(){

        if(gameSaved){
            return;
        }

        boolean saved = gameResultService.saveGameResult(currentLevel, scores);

        if(saved){
            gameSaved = true;
        }
    }

    //finish game
    private void finishGame(boolean won) {

        if(gameOver || gameWon) {
            return;
        }

        SoundManager.stopBackgroundMusic();

        if(won) {
            gameWon = true;
            SoundManager.playWinSound();
        } else {
            gameOver = true;
            SoundManager.playGameOverSound();
        }

        saveGameResult();
        gameTimer.stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //handle pressed keys
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //return to menu
        if(key == KeyEvent.VK_ESCAPE){

            if(!gameSaved){
                saveGameResult();
            }

            returnToMainMenu();
            return;
        }
        //pause
        if(key == KeyEvent.VK_P){

            if(!gameOver && !gameWon){

                paused = !paused;

                upPressed = false;
                downPressed = false;
                leftPressed = false;
                rightPressed = false;

                repaint();
            }
            return;
        }

        if(paused || gameOver || gameWon){
            return;
        }

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

                    int spacing = 20;

                    int startX = player.getX() + player.getWidth() / 2 -
                            ((fireCount - 1) * spacing) / 2;

                    for (int i = 0; i < fireCount; i++) {

                        bullets.add(new Bullets(
                                startX + i * spacing,
                                player.getY()
                        ));

                    }

                    SoundManager.playShotSound();

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

