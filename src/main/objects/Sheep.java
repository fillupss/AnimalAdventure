package main.objects;

import main.brain.Animation;
import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sheep extends Enemy {

    private Animation walkRight, walkLeft;
    private BufferedImage[] rightWalk = new BufferedImage[3];
    private BufferedImage[] leftWalk = new BufferedImage[3];
    private BufferedImage[] rightAttack = new BufferedImage[6];
    private BufferedImage[] leftAttack = new BufferedImage[6];
    private boolean faceDirection; // false -> right, true -> left


    public Sheep(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 100;
        health = maxHealth;
        idleImage = images.sheep[12];
        maxDistance = 120;
        faceDirection = true;
        velX = -3;

        // setting up all the animations for the zombie
        for(int i = 0; i < 3; i++){
            leftWalk[i] = images.sheep[i+14];
            rightWalk[i] = images.sheep[i+17];
        }
        for(int i = 0; i < 6; i++){
            leftAttack[i] = images.sheep[i];
            rightAttack[i] = images.sheep[i+6];
        }

        walkRight = new Animation(100,rightWalk);
        walkLeft = new Animation(100,leftWalk);
        attackLeft = new Animation(100,leftAttack);
        attackRight = new Animation(100,rightAttack);
    }

    @Override
    public void tick() {
        x += velX;
        y += 0;

        walkRight.tick();
        walkLeft.tick();
        attackRight.tick();
        attackLeft.tick();

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
        if(isAttacking){
            if(!faceDirection)
                g.drawImage(attackRight.getCurrentFrame(),(int)x,(int)y, idleImage.getWidth(), idleImage.getHeight(),null);
            else
                g.drawImage(attackLeft.getCurrentFrame(),(int)x,(int)y, idleImage.getWidth(), idleImage.getHeight(),null);
            if((attackRight.getIndex() == rightAttack.length -1) || (attackLeft.getIndex() == leftAttack.length -1)){
                this.isAttacking = false;
            }
        }
        else if(velX != 0){
            if(!faceDirection)
                g.drawImage(walkRight.getCurrentFrame(),(int)x,(int)y, idleImage.getWidth(), idleImage.getHeight(),null);
            else
                g.drawImage(walkLeft.getCurrentFrame(),(int)x,(int)y, idleImage.getWidth(), idleImage.getHeight(),null);
        }
        else{
            if(faceDirection){
                g.drawImage(idleImage,(int)x,(int)y,null);
            }
            else{
                g.drawImage(images.sheep[13], (int)x, (int)y, null);
            }
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
