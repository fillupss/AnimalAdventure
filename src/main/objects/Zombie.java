package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class Zombie extends Enemy {

    private boolean faceDirection; // false -> right, true -> left

    public Zombie(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 100;
        health = maxHealth;
        idleImage = images.zombie[21];
        maxDistance = 100;
        faceDirection = true;
        velX = -1;
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
            g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE), null);
        }
        else{
            g.drawImage(images.zombie[14], (int)x, (int)y,(int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE), null);
        }

        // display health bar above the enemy
        g.setColor(Color.GRAY);
        g.fillRect((int)(x - idleImage.getWidth()/SCALE / 2), (int)(y - idleImage.getHeight()/SCALE / 2) + 10,(int)(idleImage.getWidth()/SCALE * 2), 5);
        g.setColor(Color.RED);
        g.fillRect((int)(x - idleImage.getWidth()/SCALE / 2), (int)(y - idleImage.getHeight()/SCALE / 2) + 10, (int)(health * (idleImage.getWidth()/SCALE/maxHealth)) * 2, 5);
        g.setColor(Color.WHITE);
        g.drawRect((int)(x - idleImage.getWidth()/SCALE / 2), (int)(y - idleImage.getHeight()/SCALE / 2) + 10,(int)(idleImage.getWidth()/SCALE * 2),5);
        g.drawString((int)health + "/" + (int)maxHealth, (int)(x - idleImage.getWidth()/SCALE / 2) + 5, (int)(y - idleImage.getHeight()/SCALE / 2 + 8));
    }

    @Override
    public void shoot() {

    }

    @Override
    public void isDead() {
        super.isDead();
    }

}
