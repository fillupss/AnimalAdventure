package main.objects;

import main.brain.Animation;
import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Enemy {


    private Animation walkRight, walkLeft;
    private BufferedImage[] rightWalk = new BufferedImage[7];
    private BufferedImage[] leftWalk = new BufferedImage[7];
    private BufferedImage[] rightAttack = new BufferedImage[7];
    private BufferedImage[] leftAttack = new BufferedImage[7];

    private boolean faceDirection; // false -> right, true -> left

    public Zombie(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 100;
        health = maxHealth;
        idleImage = images.zombie[21];
        maxDistance = 100;
        faceDirection = true;
        velX = -1;

        // setting up all the animations for the zombie
        for(int i = 0; i < 7; i++){
            rightAttack[i] = images.zombie[i];
            leftAttack[i] = images.zombie[i+7];
            rightWalk[i] = images.zombie[i + 14];
            leftWalk[i] = images.zombie[i+21];
        }

        walkLeft = new Animation(100,leftWalk);
        walkRight = new Animation(100,rightWalk);
        attackLeft = new Animation(200,leftAttack);
        attackRight = new Animation(200,rightAttack);
    }

    @Override
    public void tick() {
        x += velX;
        y += 0;

        walkLeft.tick();
        walkRight.tick();
        attackLeft.tick();
        attackRight.tick();

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
                g.drawImage(attackRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(attackLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            if((attackRight.getIndex() == rightAttack.length -1) || (attackLeft.getIndex() == leftAttack.length -1)){
                this.isAttacking = false;
            }
        }
        else if(velX != 0){
            if(!faceDirection)
                g.drawImage(walkRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(walkLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
        }
        else{
            if(!faceDirection)
                g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(images.zombie[14],(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
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
