package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class Cat extends Enemy {

    private boolean faceDirection; // false -> right, true -> left
    private GameObject followIt;
    private int rateOfFire = 600, checkRateOfFire = 0, tick = 0;

    public Cat(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 500;
        health = maxHealth;
        idleImage = images.catBoss[26];
        faceDirection = true;

        for(int i = 0; i < handler.getHandler().size(); i++){
            GameObject tempObject = handler.getHandler().get(i);
            if(tempObject.getId().equals(ObjectID.Player)){
                followIt = tempObject;
            }
        }
    }

    @Override
    public void tick() {
        if(followIt.getX() < this.x - 864){
            velX = 0;
        }
        else {
            float diffX = x - followIt.getX();
            if (diffX > 0) {
                velX = -3;
                faceDirection = true;
            } else {
                velX = 3;
                faceDirection = false;
            }
        }
        x += velX;
        y += 0;
        collision();
        shoot();

    }

    @Override
    public void draw(Graphics g) {
        if(faceDirection)
            g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
        else{
            g.drawImage(images.catBoss[17],(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
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
        if(rateOfFire == checkRateOfFire && followIt.getX() > this.x - 864){
            if(tick == 2){
                handler.addObject(new Robot(x,y,ObjectID.Enemy,handler));
                tick = 0;
            }
            else if(tick == 1){
                handler.addObject(new Sheep(x,y,ObjectID.Enemy,handler));
                tick++;
            }
            else{
                handler.addObject(new Zombie(x,y,ObjectID.Enemy,handler));
                tick++;
            }
            checkRateOfFire = 0;
            rateOfFire = rateOfFire - 25;
            if(rateOfFire <= 0){
                rateOfFire = 50;
            }
        }
        else if(followIt.getX() > this.x - 864){
            checkRateOfFire++;
        }
    }

    @Override
    public void isDead() {
        if(health <= 0){
            handler.removeSandBlocks();
            handler.removeAllEnemies();
        }
    }

    public void setHealth(float health){
        this.health = health;
        // fix a small bug on the HUD
        if(this.health < 1){
            this.health = 0;
        }
        else if(this.health > maxHealth){
            this.health = maxHealth;
        }
    }

}
