package main.objects;

import main.brain.Animation;
import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Robot extends Enemy {

    private Animation shootLeft;
    private BufferedImage[] leftAttack = new BufferedImage[8];
    private BufferedImage[] leftShoot = new BufferedImage[4];

    private boolean faceDirection = true; // false -> right, true -> left
    private boolean isShooting = false;
    private int rateOfFire, counter;

    public Robot(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        maxHealth = 100;
        health = maxHealth;
        idleImage = images.robot[1];
        rateOfFire = 150;
        counter = 0;

        for(int i = 0; i < 8; i++){
            leftAttack[i] = images.robot[i+2];
        }
        for(int i = 0; i < 4; i++){
            leftShoot[i] = images.robot[i+10];
        }

        attackLeft = new Animation(200,leftAttack);
        shootLeft = new Animation(200,leftShoot);
    }

    @Override
    public void tick() {
        x += 0;
        y += 0;
        attackLeft.tick();
        shootLeft.tick();
        collision();
        shoot();
    }

    @Override
    public void draw(Graphics g) {
        if(isShooting){
            g.drawImage(shootLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            if(shootLeft.getIndex() == leftShoot.length -1){
                this.isShooting = false;
                handler.addObject(new BlastBullet(x - 20,y + idleImage.getHeight()/2 - 30,ObjectID.EnemyBullet,handler, faceDirection));
            }
        }
        else if(isAttacking){
            g.drawImage(attackLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            if(attackLeft.getIndex() == leftAttack.length -1){
                this.isAttacking = false;
            }
        }
        else
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
            isShooting = true;
            resetShootAnimation();
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

    private void resetShootAnimation(){
        this.shootLeft.setIndex(0);
    }

}
