package main.objects;

import main.brain.Animation;
import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cat extends Enemy {

    private Animation walkRight, walkLeft, hurtRight, hurtLeft, deadRight, deadLeft;
    private BufferedImage[] rightWalk = new BufferedImage[9];
    private BufferedImage[] leftWalk = new BufferedImage[9];
    private BufferedImage[] rightHurt = new BufferedImage[7];
    private BufferedImage[] leftHurt = new BufferedImage[7];
    private BufferedImage[] rightDead = new BufferedImage[6];
    private BufferedImage[] leftDead = new BufferedImage[6];

    private boolean faceDirection; // false -> right, true -> left
    private boolean isHurting = false;
    private boolean isDead = false;
    private GameObject followIt;
    private int rateOfFire = 600, checkRateOfFire = 0, tick = 0;

    public Cat(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 500;
        health = maxHealth;
        idleImage = images.catBoss[35];
        faceDirection = true;

        for(int i = 0; i < 6; i++){
            rightDead[i] = images.catBoss[i];
            leftDead[i] = images.catBoss[i+6];
        }
        for(int i = 0; i < 7; i++){
            rightHurt[i] = images.catBoss[i+12];
            leftHurt[i] = images.catBoss[i+19];
        }
        for(int i = 0; i < 9; i++){
            rightWalk[i] = images.catBoss[i+26];
            leftWalk[i] = images.catBoss[i+35];
        }

        for(int i = 0; i < handler.getHandler().size(); i++){
            GameObject tempObject = handler.getHandler().get(i);
            if(tempObject.getId().equals(ObjectID.Player)){
                followIt = tempObject;
            }
        }

        walkRight = new Animation(100,rightWalk);
        walkLeft = new Animation(100,leftWalk);
        hurtLeft = new Animation(100,leftHurt);
        hurtRight = new Animation(100,rightHurt);
        deadLeft = new Animation(750,leftDead);
        deadRight = new Animation(750,rightDead);
    }

    @Override
    public void tick() {
        if(followIt.getX() < this.x - 864 || isDead){
            velX = 0;
        }
        else {
            float diffX = x - followIt.getX();
            if (diffX > 0) {
                velX = -3;
                faceDirection = true;
            } else if(diffX < 0) {
                velX = 3;
                faceDirection = false;
            }
        }
        x += velX;
        y += 0;

        walkRight.tick();
        walkLeft.tick();
        hurtLeft.tick();
        hurtRight.tick();
        deadLeft.tick();
        deadRight.tick();

        collision();
        shoot();

    }

    @Override
    public void draw(Graphics g) {
        if(isDead){
            if(!faceDirection)
                g.drawImage(deadRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(deadLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            if((deadRight.getIndex() == rightDead.length -1) || (deadLeft.getIndex() == leftDead.length -1)){
                this.isDead = false;
                handler.removeSandBlocks();
                handler.removeObject(this);
            }
        }
        else if(isHurting){
            if(!faceDirection)
                g.drawImage(hurtRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(hurtLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            if((hurtRight.getIndex() == rightHurt.length -1) || (hurtLeft.getIndex() == leftHurt.length -1)){
                this.isHurting = false;
            }
        }
        else if(velX != 0){
            if(!faceDirection)
                g.drawImage(walkRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(walkLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
        }
        else{
            if(faceDirection)
                g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else{
                g.drawImage(images.catBoss[26],(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            }
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
        if(rateOfFire == checkRateOfFire && followIt.getX() > this.x - 864 && !isDead){
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
            isDead = true;
            this.resetDeathAnimation();
            handler.removeAllEnemies();
            Player.setFinalHit(Player.getFinalHit()+5);
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

    public void setHurting(boolean isHurting){
        this.isHurting = isHurting;
    }

    private void resetDeathAnimation(){
        this.deadRight.setIndex(0);
        this.deadLeft.setIndex(0);
    }


}
