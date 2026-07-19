package ui;

import helpers.ImageLoader;
import helpers.SoundManager;
import managers.ChickenManager;
import models.*;
import managers.PowerUpManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import services.GameResultService;
import services.LoggedUser;

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
    private long pauseStartTime = 0;

    //all bullets
    private ArrayList<Bullets> bullets;

    private ChickenManager chickenManager;

    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean gameSaved = false;
    private boolean endScreenVisible = false;
    private GameResultService gameResultService;


    private int scores = 0;

    private ArrayList<Egg> eggs;
    private long lastEggDropTime = 0;

    //shooter chicken attack time
    private long lastShooterShotTime = 0 ;
    private final long shooterShotDelay = 2000;

    //all explosions in the game
    private ArrayList<Explosion> explosions;

    //time between two egg drops
    private long eggDropDelay = 3000;

    private int currentLevel = 1;

    //boss object for boss levels
    private Boss boss;
    private boolean bossLevel = false;

    //checks if current boss is final boss
    private boolean finalBossLevel = false;

    //manages all power ups
    private PowerUpManager powerUpManager;

    //game background
    private Image backgroundImage;

    //player username
    private String username;

    public GamePanel(String username) {

        if(username == null || username.trim().isEmpty()){
            this.username = "Guest";
        } else {
            this.username = username;
        }

        SoundManager.updateBackgroundMusic();

        backgroundImage = ImageLoader.loadImage(
                "/images/backgrounds/deepSpace.png");

        setBackground(Color.BLACK);

        String selectedPlane = "Default";

        if(LoggedUser.getUser() != null) {

            selectedPlane = LoggedUser.getUser().getSelectedPlane();
        }

        player = new Player(400, 500, selectedPlane);

        bullets = new ArrayList<>();
        eggs = new ArrayList<>();
        explosions = new ArrayList<>();

        powerUpManager = new PowerUpManager();

        gameResultService = new GameResultService();

        setFocusable(true);
        addKeyListener(this);

        //creates the first level
        startLevel();

        gameTimer = new Timer(16, e -> {

            //wait for level 4 boss explosion before starting level 5
            boolean waitingForLevel5 = bossLevel && !finalBossLevel && boss == null;

            //stop game updates if paused
            if(paused){
                return;
            }

            //after boss or end game just explosions continue
            if(gameOver || gameWon || waitingForLevel5){

                updateExplosions();

                if(explosions.isEmpty()){

                    if(waitingForLevel5){

                        bossLevel = false;
                        currentLevel = 5;
                        startLevel();

                    } else {
                        endScreenVisible = true;
                        gameTimer.stop();
                    }
                }
                repaint();
                return;
            }

            //movement and attacks
            updatePlayerMovement();
            updateEnemyMovement();
            updateChickenAttacks();

            //update game objects
            updateEggs();
            powerUpManager.update(player,getHeight());
            updateBullets();

            //check collisions
            checkBulletChickenCollisions();
            updateBossLevel();
            checkChickenPlayerCollision();
            checkEggPlayerCollisions();

            //check game and level status
            checkLevelCompletion();
            checkChickensReachedBottom();

            //remove finished explosions
            updateExplosions();

            repaint();
        });

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
        powerUpManager.draw(g);

        //draw game information
        drawGameInfo(g);

        //game over
        if (gameOver && endScreenVisible) {

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
        if (gameWon && endScreenVisible) {

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));

            String text = "YOU WON!";

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

        if(powerUpManager.isFreezeActive()) {

            g.drawString("FREEZE", x, y);
        }
    }
    //create chicken for levels
    private void startLevel(){

        powerUpManager.resetForNewLevel(player);

        //level object
        Level level = new Level(currentLevel);

        //level settings
        eggDropDelay = level.getEggDropDelay();

        chickenManager = new ChickenManager(level);
        chickenManager.createFormation();

        eggs.clear();
        bullets.clear();

        long levelStartTime = System.currentTimeMillis();

        lastEggDropTime = levelStartTime;
        lastShooterShotTime = levelStartTime;
    }

    //start first boss level after level 3
    private void startBossLevel4() {

        powerUpManager.resetForNewLevel(player);

        bossLevel = true;
        finalBossLevel = false;

        boss = new BossLevel4(300, 80, 50);

        eggs.clear();
        bullets.clear();

        lastEggDropTime = System.currentTimeMillis();
    }

    //start final boss level 8
    private void startFinalBossLevel8(){

        powerUpManager.resetForNewLevel(player);

        bossLevel = true;
        finalBossLevel = true;

        boss = new FinalBoss(300,80,100);

        eggs.clear();
        bullets.clear();

        lastEggDropTime = System.currentTimeMillis();
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

    }

    //adjust all timers so they dont end during pause
    private void addPauseTimeToTimers(long pauseTime) {

        lastEggDropTime += pauseTime;
        lastShooterShotTime += pauseTime;

        //power up timers
        powerUpManager.addPausedTime(
                pauseTime
        );

        //player timers
        player.addPausedTime(pauseTime);

        //boss attack and movement timers
        if(boss != null) {
            boss.addPausedTime(pauseTime);
        }

        //explosions must freeze during pause
        for(Explosion explosion : explosions) {
            explosion.addPausedTime(pauseTime);
        }
    }

    //update chicken group or boss movement
    private void updateEnemyMovement() {

        if(powerUpManager.isFreezeActive()){
            return;
        }

        if(bossLevel && boss != null){
            boss.move(getWidth());
        } else {
            chickenManager.moveGroup(getWidth());
        }
    }

    //move eggs and remove those leave the screen
    private void updateEggs() {

        for(int i = 0; i < eggs.size(); i++){

            Egg egg = eggs.get(i);

            if(!powerUpManager.isFreezeActive()) {
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
    }

    //move bullets and remove those that leave the screen
    private void updateBullets() {

        for(int i = 0; i < bullets.size(); i++){

            Bullets bullet = bullets.get(i);

            bullet.fly();

            if(bullet.getY() < 0){

                bullets.remove(i);
                i--;
            }
        }
    }

    //update normal egg drops and shooter chicken attacks
    private void updateChickenAttacks() {

        if(bossLevel || powerUpManager.isFreezeActive()){
            return;
        }

        long currentTime = System.currentTimeMillis();

        //normal egg drop
        if(currentTime - lastEggDropTime >= eggDropDelay){

            ArrayList<Chicken> bottomChickens =
                    chickenManager.getBottomChickens();

            if(!bottomChickens.isEmpty()){

                int randomIndex =
                        (int)(Math.random() * bottomChickens.size());

                Chicken chicken = bottomChickens.get(randomIndex);

                eggs.add(new Egg(
                        chicken.getX() + chicken.getWidth() / 2,
                        chicken.getY() + chicken.getHeight()
                ));

                lastEggDropTime = currentTime;
            }
        }

        //shooter chicken attack
        if(currentTime - lastShooterShotTime >= shooterShotDelay){

            shooterChickenAttack();
            lastShooterShotTime = currentTime;
        }
    }

    //checks collisions between player bullets and chickens
    private void checkBulletChickenCollisions() {

        if(bossLevel){
            return;
        }

        for(int i = 0; i < bullets.size(); i++){

            Bullets bullet = bullets.get(i);

            for(int j = 0; j < chickenManager.getChickens().size(); j++){

                Chicken chicken =
                        chickenManager.getChickens().get(j);

                //bullet hits chicken
                if(chicken.isHit(bullet)){

                    chicken.takeDamage();

                    //remove bullet after collision
                    bullets.remove(i);
                    i--;

                    //chicken dies
                    if(chicken.getLives() <= 0){

                        scores += chicken.getScore();

                        explosions.add(new Explosion(
                                chicken.getX() + chicken.getWidth() / 2,
                                chicken.getY() + chicken.getHeight() / 2,
                                80
                        ));

                        SoundManager.playExplosionSound();

                        powerUpManager.createPowerUp(chicken, player);

                        chickenManager.getChickens().remove(j);
                        chickenManager.replaceChickenIfNeeded(chicken, getWidth());

                        j--;
                    }

                    break;
                }
            }
        }
    }

    //checks collision between chickens and player
    private void checkChickenPlayerCollision() {

        if(bossLevel){
            return;
        }

        for(Chicken chicken : chickenManager.getChickens()){

            if(chicken.hitPlayer(player)){

                if(player.canTakeDamage()
                        && !player.isShieldActive()){

                    player.takeDamage();

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
    }

    //checks collisions between enemy eggs and player
    private void checkEggPlayerCollisions() {

        for(int i = 0; i < eggs.size(); i++){

            Egg egg = eggs.get(i);

            if(player.isHit(egg)){

                eggs.remove(i);
                i--;

                if(player.canTakeDamage()
                        && !player.isShieldActive()){

                    player.takeDamage();

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
    }

    // boss death and finish the boss level
    private void finishBossLevel() {

        int explosionSize;

        if(finalBossLevel){
            explosionSize = 260;
        } else {
            explosionSize = 180;
        }

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
            //remove boss but keep boss level active
            //until its explosion is finished
            boss = null;
        }
    }

    //update boss attacks and check bullet collisions
    private void updateBossLevel() {

        if(!bossLevel || boss == null){
            return;
        }

        //boss attack
        if(boss.canAttack() && !powerUpManager.isFreezeActive()){

            eggs.addAll(
                    boss.createAttack()
            );
        }

        //bullet and boss collision
        for(int i = 0; i < bullets.size(); i++){

            Bullets bullet = bullets.get(i);

            if(boss.isHit(bullet)){

                boss.takeDamage(player.getBossDamage());

                bullets.remove(i);
                i--;

                if(boss.isDead()){
                    finishBossLevel();
                }

                break;
            }
        }
    }

    //checks if the current level is completed
    private void checkLevelCompletion() {

        if(gameOver || gameWon || bossLevel){
            return;
        }

        if(!chickenManager.getChickens().isEmpty()){
            return;
        }

        //normal level completion score
        scores += 200;

        if(currentLevel == 3){

            currentLevel = 4;
            startBossLevel4();

        } else if(currentLevel == 7){

            currentLevel = 8;
            startFinalBossLevel8();

        } else {

            currentLevel++;
            startLevel();
        }
    }

    //update player movement based on pressed keys
    private void updatePlayerMovement() {

        if(upPressed)
            player.moveUp();

        if(downPressed)
            player.moveDown();

        if(leftPressed)
            player.moveLeft();

        if(rightPressed)
            player.moveRight();

        player.stayInsideScreen(getWidth(), getHeight());
    }

    //end the game if chickens reach the bottom
    private void checkChickensReachedBottom() {

        if(gameOver || gameWon || bossLevel){
            return;
        }

        if(chickenManager.reachedBottom(getHeight())){
            finishGame(false);
        }
    }

    //removes finished explosions
    private void updateExplosions() {

        for(int i = 0; i < explosions.size(); i++){

            Explosion explosion = explosions.get(i);

            if(explosion.isFinished()){

                explosions.remove(i);
                i--;
            }
        }
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

                if(!paused){

                    //start pause
                    paused = true;
                    pauseStartTime = System.currentTimeMillis();

                } else {

                    //finish pause
                    long pauseTime = System.currentTimeMillis() - pauseStartTime;

                    addPauseTimeToTimers(pauseTime);

                    paused = false;
                    pauseStartTime = 0;
                }

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
                break;

            case KeyEvent.VK_RIGHT:
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

