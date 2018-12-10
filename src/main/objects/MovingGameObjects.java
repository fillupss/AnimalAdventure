package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MovingGameObjects extends GameObject {

    protected float health, maxHealth;
    protected float velX, velY;
    protected final float GRAVITY = 0.5f;
    protected final float SCALE = 1.5f;

    public MovingGameObjects(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
    }

    public abstract void tick();
    public abstract void draw(Graphics g);

    public abstract void collision();
    public abstract void shoot();
    public abstract void isDead();

    public void setHealth(float health){
        this.health = health;
        // fix a small bug on the HUD
        if(this.health < 1){
            this.health = 0;
        }
        else if(this.health > 100){
            this.health = 100;
        }
    }
    public float getHealth(){
        return health;
    }


    public float getvelX(){
        return velX;
    }
    public float getvelY(){
        return velY;
    }
    public void setVelX(float velX){
        this.velX = velX;
    }
    public void setVelY(float velY){
        this.velY = velY;
    }

    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)x, (int)y, (int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE));
    }

}
