package main.objects;

import main.brain.Animation;
import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MovingGameObjects {

    private Animation walkRight, walkLeft, jumpRight, jumpLeft, hurtRight, hurtLeft, deadRight, deadLeft;
    private BufferedImage[] rightWalk = new BufferedImage[8];
    private BufferedImage[] leftWalk = new BufferedImage[8];
    private BufferedImage[] rightJump = new BufferedImage[6];
    private BufferedImage[] leftJump = new BufferedImage[6];
    private BufferedImage[] rightHurt = new BufferedImage[8];
    private BufferedImage[] leftHurt = new BufferedImage[8];
    private BufferedImage[] rightDead = new BufferedImage[6];
    private BufferedImage[] leftDead = new BufferedImage[6];


    private LevelLoader levelLoader;
    private boolean isHurting = false, isDead = false;
    private boolean isJumping = false, isFalling = false;
    private boolean faceDirection; // false -> right, true -> left

    private boolean doubleShotPowerUp = false; // 5 shots
    private boolean missilePowerUp = false; // 2 shots

    private boolean isInvincible = false;
    private int maxTime = 125;
    private int currentTime = 0;
    private int life = 2;
    private int count = 0; // used to know how many shots are left in the power up
    private int totalCount = 0;
    private float previousX = 0, previousY = 0;


    static int bulletsHit = 0;
    static int finalHit = 0;

    public Player(float x, float y, ObjectID id, Controller handler, LevelLoader levelLoader) {
        super(x, y, id, handler);
        this.levelLoader = levelLoader;
        this.idleImage = images.dogPlayer[28];

        // setting up all the animations for the dog
        for(int i = 0; i < 8; i++){
            rightWalk[i] = images.dogPlayer[i+42];
            leftWalk[i] = images.dogPlayer[i+50];
            rightHurt[i] = images.dogPlayer[i+12];
            leftHurt[i] = images.dogPlayer[i+20];
        }
        for(int i = 0; i < 6; i++){
            rightDead[i] = images.dogPlayer[i];
            leftDead[i] = images.dogPlayer[i+6];
            rightJump[i] = images.dogPlayer[i+30];
            leftJump[i] = images.dogPlayer[i+36];
        }

        walkRight = new Animation(100,rightWalk);
        walkLeft = new Animation(100,leftWalk);
        jumpRight = new Animation(200,rightJump);
        jumpLeft = new Animation(200,leftJump);
        hurtLeft = new Animation(100,leftHurt);
        hurtRight = new Animation(100,rightHurt);
        deadLeft = new Animation(500,leftDead);
        deadRight = new Animation(500,rightDead);


        faceDirection = false;
        health = 3;

    }

    @Override
    public void tick() {

        x += velX;
        y += velY;


        // the animations
        walkLeft.tick();
        walkRight.tick();
        jumpLeft.tick();
        jumpRight.tick();
        hurtLeft.tick();
        hurtRight.tick();
        deadLeft.tick();
        deadRight.tick();

        // isInvincible is true only when the player recently got git
        // it is so the player can not get hit consecutively in quick successions
        // currentTime will check if the invincibility period is over
        if(isInvincible && currentTime == maxTime){
            currentTime = 0;
            isInvincible = false;
        }
        else if(isInvincible){
            currentTime++;
        }

        if(velX < 0){
            faceDirection = true;
        }
        else if(velX > 0){
            faceDirection = false;
        }

        if(isJumping || isFalling){
            velY += GRAVITY;
            if(y >= 750){
                health = health - 1;
                x = previousX;
                y = previousY;
                isInvincible = true;
                isDead();
            }
        }
        collision();
    }

    @Override
    public void collision() {
        for(int i = 0; i < handler.getHandler().size(); i++){
            GameObject temp = handler.getHandler().get(i);
            if(temp.getId().equals(ObjectID.Block) || temp.getId().equals(ObjectID.PowerUp) || temp.getId().equals(ObjectID.SandBlock)){
                if(this.getTopCollisionBounds().intersects(temp.getCollisionBounds())){
                    y = temp.getY() + 32;
                    velY = 0;
                    if(temp.getId().equals(ObjectID.PowerUp)){
                        temp.function(this);
                        handler.removeObject(temp);
                    }
                }

                if(this.getCollisionBounds().intersects(temp.getCollisionBounds())){
                    velY = 0;
                    this.y = temp.getY() - idleImage.getHeight()/SCALE;
                    isJumping = false;
                    isFalling = false;
                    previousX = x;
                    previousY = y;
                }
                else{
                    isFalling = true;
                }
                if(this.getRightCollisionBounds().intersects(temp.getCollisionBounds())){
                    x = temp.getX() - 48;
                }
                if(this.getLeftCollisionBounds().intersects(temp.getCollisionBounds())){
                    x = temp.getX() + 32;
                }
            }
            else if(temp.getId().equals(ObjectID.Flag)){
                if(this.getCollisionBounds().intersects(temp.getCollisionBounds()))
                    levelLoader.switchLevel();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if(isDead){
            isInvincible = true;
            currentTime = 0;
            if(!faceDirection)
                g.drawImage(deadRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(deadLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            if((deadRight.getIndex() == rightDead.length -1) || (deadLeft.getIndex() == leftDead.length -1)){
                this.isDead = false;
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
        else if(isJumping){
            if(!faceDirection)
                g.drawImage(jumpRight.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
            else
                g.drawImage(jumpLeft.getCurrentFrame(),(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
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
                g.drawImage(images.dogPlayer[29],(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
        }
    }

    // used for collision with bottom of block
    public Rectangle getTopCollisionBounds(){
        return new Rectangle((int)x + 10,(int)y,(int)(this.idleImage.getWidth()/SCALE - 20), 10);
    }

    // used for collision with block
    public Rectangle getRightCollisionBounds(){
        return new Rectangle((int)(x + this.idleImage.getWidth()/SCALE - 10), (int)y + 10,10,(int)(this.idleImage.getHeight()/SCALE - 18));
    }

    // used for collision with block
    public Rectangle getLeftCollisionBounds(){
        return new Rectangle((int)x, (int)y+10,10,(int)(this.idleImage.getHeight()/SCALE - 18));
    }

    // used for collision with block falling or jumping
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)x + 10, (int)y + 10, (int)(idleImage.getWidth()/SCALE - 20), (int)(idleImage.getHeight()/SCALE - 10));
    }

    @Override
    public void shoot() {
        if(missilePowerUp && count > 0){
            if(faceDirection)
                handler.addObject(new BulletBill(this.x, this.y + idleImage.getHeight()/SCALE/2 - 10, ObjectID.PlayerBullet, handler, faceDirection));
            else
                handler.addObject(new BulletBill(this.x + idleImage.getWidth()/2, this.y + idleImage.getHeight()/SCALE/2 - 10, ObjectID.PlayerBullet, handler, faceDirection));
            count--;
        }
        else if(doubleShotPowerUp && count > 0){
            if(faceDirection){
                handler.addObject(new Bullet(this.x, this.y + idleImage.getHeight()/SCALE/2, ObjectID.PlayerBullet, handler, faceDirection));
                handler.addObject(new Bullet(this.x, this.y + idleImage.getHeight()/SCALE/2 - 15, ObjectID.PlayerBullet, handler, faceDirection));
            }
            else{
                handler.addObject(new Bullet(this.x + idleImage.getWidth()/2, this.y + idleImage.getHeight()/SCALE/2, ObjectID.PlayerBullet, handler, faceDirection));
                handler.addObject(new Bullet(this.x + idleImage.getWidth()/2, this.y + idleImage.getHeight()/SCALE/2 - 15, ObjectID.PlayerBullet, handler, faceDirection));
            }
            count--;
        }
        else{
            if(faceDirection){
                handler.addObject(new Bullet(this.x, this.y + idleImage.getHeight()/SCALE/2, ObjectID.PlayerBullet, handler, faceDirection));

            }
            else{
                handler.addObject(new Bullet(this.x + idleImage.getWidth()/2, this.y + idleImage.getHeight()/SCALE/2, ObjectID.PlayerBullet, handler, faceDirection));

            }
        }
        if(count == 0){
            totalCount = 0;
        }
    }

    @Override
    public void isDead() {
        if(health <= 0){
            isDead = true;
            this.resetDeathAnimation();
            isHurting = false;
            if(life >= 0){
                if(life == 0){
                    this.health = 0;
                    velX = 0;
                    velY = 0;
                }
                else{
                    this.health = 3;
                    isInvincible = true;
                }
                this.life--;
            }
        }
    }

    public void setJumping(boolean isJumping){
        this.isJumping = isJumping;
    }

    public boolean getJumping(){
        return isJumping;
    }

    public void setInvincible(boolean isInvincible){
        this.isInvincible = isInvincible;
    }

    public boolean getInvincible(){
        return isInvincible;
    }

    public int getLifeLeft(){
        return life;
    }

    public void setCount(int count){
        this.count = count;
    }
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }

    public int getCount(){
        return count;
    }
    public int getTotalCount(){
        return totalCount;
    }

    public void setDoubleShotPowerUp(boolean doubleShotPowerUp){
        this.doubleShotPowerUp = doubleShotPowerUp;
    }

    public void setMissilePowerUp(boolean missilePowerUp){
        this.missilePowerUp = missilePowerUp;
    }


    public static void setBulletsHit(int c){
        bulletsHit = c;
    }

    public static int getBulletsHit(){
        return bulletsHit;
    }

    public static void setFinalHit(int c){
        finalHit = c;
    }

    public static int getFinalHit(){
        return finalHit;
    }

    public void setHurting(boolean isHurting){
        this.isHurting = isHurting;
    }
    public boolean isHurting(){
        return isHurting;
    }
    public boolean isRespawning(){
        return isDead;
    }

    public Animation getHurtRight(){
        return hurtRight;
    }
    public Animation getHurtLeft(){
        return hurtLeft;
    }

    // reset animation is just to see the full animation
    // sometimes the index wouldnt start at 0 when animation is to be triggered
    private void resetDeathAnimation(){
        this.deadRight.setIndex(0);
        this.deadLeft.setIndex(0);
    }

}
