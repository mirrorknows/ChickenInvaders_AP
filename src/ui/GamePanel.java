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

    private int scores = 0;

    private ArrayList<Egg> eggs;
    private long lastEggDropTime = 0;

    //time between two egg drops
    private long eggDropDelay = 3000;

    private int currentLevel = 1;

    public GamePanel(){

        setBackground(Color.BLACK);

        player = new Player(400,500);

        bullets = new ArrayList<>();

        eggs = new ArrayList<>();

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

            chickenManager.moveGroup(getWidth());

            long currentTime = System.currentTimeMillis();

            if(currentTime - lastEggDropTime >= eggDropDelay){

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

            for (int i = 0; i < eggs.size(); i++) {

                Egg egg = eggs.get(i);

                egg.drop();

                if (egg.getY() > getHeight()) {

                    eggs.remove(i);

                    i--;
                }
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

                            chickenManager.getChickens().remove(j);

                            j--;

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

                    eggs.remove(i);
                    i--;

                    if(player.canTakeDamage()){
                        player.takeDamage();

                        if(player.getLives() <= 0){
                            gameOver = true;
                            gameTimer.stop();
                        }
                    }

                }
            }

            if(!gameOver && chickenManager.getChickens().isEmpty()){

                currentLevel++;

                startLevel();
            }

            if (chickenManager.reachedBottom(getHeight())) {

                gameOver = true;

                gameTimer.stop();

                repaint();

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

        g.setColor(Color.YELLOW);

        for(Bullets bullet : bullets){

            g.fillRect(
                    bullet.getX(),
                    bullet.getY(),
                    bullet.getWidth(),
                    bullet.getHeight()
            );

        }



        for (Chicken chicken : chickenManager.getChickens()) {
            g.setColor(chicken.getColor());

            g.fillRect(
                    chicken.getX(),
                    chicken.getY(),
                    chicken.getWidth(),
                    chicken.getHeight()
            );

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

        //eggs
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
    }

    //create chicken for levels
    private void startLevel(){

        //level object
        Level level = new Level(currentLevel);

        //level settings
        eggDropDelay = level.getEggDropDelay();

        chickenManager = new ChickenManager(level);
        chickenManager.createFormation();


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

                if (currentTime - player.getLastShotTime() >= player.getShootDelay()){

                    bullets.add(new Bullets(
                            player.getX() + player.getWidth()/ 2 ,
                            player.getY())
                    );

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

