package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class Robot extends Enemy {

    private boolean faceDirection = true; // false -> right, true -> left
    private int rateOfFire, counter;

    public Robot(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 100;
        health = maxHealth;
        idleImage = images.robot[1];
        rateOfFire = 150;
        counter = 0;
    }

    @Override
    public void tick() {
        x += 0;
        y += 0;
        collision();
        shoot();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);

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
        if(rateOfFire == counter){
            handler.addObject(new BlastBullet(x - 20,y + idleImage.getHeight()/2 - 30,ObjectID.EnemyBullet,handler, faceDirection));
            counter = 0;
        }
        else{
            counter++;
        }

    }

    @Override
    public void isDead() {
        super.isDead();
    }

}
