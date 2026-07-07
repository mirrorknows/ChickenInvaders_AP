package models;

import java.awt.*;

//this class creates power ups
public class PowerUp {

    //powerup type
    private String type;

    //power up position
    private int x;
    private int y;

    //power up size
    private final int width = 25;
    private final int height = 25;

    //falling speed
    private final int speed = 2;

    public PowerUp(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    //move power up down
    public void fall() {
        y += speed;
    }

    //check if player catches power up
    public boolean hitPlayer(Player player) {
        return player.getX() < x + width &&
                player.getX() + player.getWidth() > x &&
                player.getY() < y + height &&
                player.getY() + player.getHeight() > y;
    }

    //draw power up
    public void draw(Graphics g) {


        if(type.equals("ADD_FIRE")) {

            g.setColor(Color.PINK);

        } else if(type.equals("EXTRA_LIFE")) {

            g.setColor(Color.GREEN);

        } else if(type.equals("RAPID_FIRE")) {

            g.setColor(Color.YELLOW);

        } else if(type.equals("SHIELD")) {

        g.setColor(Color.CYAN);

        }else if(type.equals("FREEZE_BOMB")) {

        g.setColor(Color.BLUE);

    }


        g.fillOval(x, y, width, height);


        g.setColor(Color.BLACK);


        if(type.equals("ADD_FIRE")) {

            g.drawString("F", x + 8, y + 17);

        } else if(type.equals("EXTRA_LIFE")) {

            g.drawString("+", x + 8, y + 17);

        } else if(type.equals("RAPID_FIRE")) {

            g.drawString("R", x + 8, y + 17);

        } else if(type.equals("SHIELD")) {

            g.drawString("S", x + 8, y + 17);

        } else if(type.equals("FREEZE_BOMB")) {

            g.drawString("B", x + 10, y + 17);

        }

    }

    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}