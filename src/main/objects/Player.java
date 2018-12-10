package main.objects;

import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;

import java.awt.*;

public class Player extends MovingGameObjects {

    private LevelLoader levelLoader;
    private boolean isJumping = false, isFalling = false;
    private boolean faceDirection; // false -> right, true -> left

    private boolean doubleShotPowerUp = false; // 5 shots
    private boolean missilePowerUp = false; // 2 shots

    private boolean isInvincible = false;
    private int maxTime = 75;
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
        this.idleImage = images.dogPlayer[30];
        faceDirection = false;
        health = 3;

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

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

                if(this.getRightCollisionBounds().intersects(temp.getCollisionBounds())){
                    x = temp.getX() - 32;
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
        g.setColor(Color.red);
        //g.drawRect((int)x, (int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE));
        //g.drawRect((int)x,(int)y,(int)(this.idleImage.getWidth()/SCALE), 3);
        //g.drawRect((int)(x + this.idleImage.getWidth()/SCALE - 3), (int)y,3,(int)(this.idleImage.getHeight()/SCALE));
        //g.drawRect((int)x, (int)y,3,(int)(this.idleImage.getHeight()/SCALE));
        if(!faceDirection)
            g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
        else
            g.drawImage(images.dogPlayer[31],(int)x,(int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE),null);
    }

    public Rectangle getTopCollisionBounds(){
        return new Rectangle((int)x,(int)y,(int)(this.idleImage.getWidth()/SCALE), 3);
    }

    public Rectangle getRightCollisionBounds(){
        return new Rectangle((int)(x + this.idleImage.getWidth()/SCALE)-3, (int)y,3,(int)(this.idleImage.getHeight()/SCALE));
    }

    public Rectangle getLeftCollisionBounds(){
        return new Rectangle((int)x, (int)y + 3,3,(int)(this.idleImage.getHeight()/SCALE)-10);
    }

    @Override
    public void shoot() {
        if(missilePowerUp && count > 0){
            if(faceDirection)
                handler.addObject(new BulletBill(this.x, this.y + idleImage.getHeight()/SCALE/2, ObjectID.PlayerBullet, handler, faceDirection));
            else
                handler.addObject(new BulletBill(this.x + idleImage.getWidth()/2, this.y + idleImage.getHeight()/SCALE/2, ObjectID.PlayerBullet, handler, faceDirection));
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

}
