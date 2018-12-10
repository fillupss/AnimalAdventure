package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class Sheep extends Enemy {

    private boolean faceDirection; // false -> right, true -> left


    public Sheep(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 100;
        health = maxHealth;
        idleImage = images.sheep[12];
        maxDistance = 120;
        faceDirection = true;
        velX = -3;
    }

    @Override
    public void tick() {
        x += velX;
        y += 0;
        currentDistance += Math.abs(velX);

        if(currentDistance >= maxDistance){
            velX *= -1;
            currentDistance = 0;
        }

        if(velX < 0){
            faceDirection = true;
        }
        else if(velX > 0){
            faceDirection = false;
        }
        collision();
    }

    @Override
    public void draw(Graphics g) {
        if(faceDirection){
            g.drawImage(idleImage,(int)x,(int)y,null);
        }
        else{
            g.drawImage(images.sheep[13], (int)x, (int)y, null);
        }
        // display health bar above the enemy
        g.setColor(Color.GRAY);
        g.fillRect((int)x, (int)(y - idleImage.getHeight() / 2) + 10,idleImage.getWidth(), 5);
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)(y - idleImage.getHeight() / 2) + 10, (int)(health * (idleImage.getWidth()/maxHealth)), 5);
        g.setColor(Color.WHITE);
        g.drawRect((int)x, (int)(y - idleImage.getHeight() / 2) + 10,idleImage.getWidth(),5);
        g.drawString((int)health + "/" + (int)maxHealth, (int)x + 5, (int)(y - idleImage.getHeight() / 2 + 8));
    }

    @Override
    public void shoot() {

    }

    @Override
    public void isDead() {
        super.isDead();
    }


}
