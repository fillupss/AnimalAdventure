package main.objects;

import main.GameWorld;
import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected float x, y;
    protected BufferedImage idleImage; // mainly used for collision, the bufferedImage display will change
    protected ObjectID id;
    protected Controller handler;
    protected Images images;

    protected final float SCALE = 1.5f;

    public GameObject(float x, float y, ObjectID id, Controller handler){
        this.x = x;
        this.y = y;
        this.id = id;
        this.handler = handler;
        this.images = GameWorld.getImages();
    }

    public abstract void tick();

    public abstract void draw(Graphics g);

    public Rectangle getCollisionBounds(){
        return new Rectangle((int)this.x, (int)this.y, this.idleImage.getWidth(), this.idleImage.getHeight());
    }

    public void function(Player p){

    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public ObjectID getId(){
        return id;
    }
}
