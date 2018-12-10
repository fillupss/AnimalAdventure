package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public abstract class PowerUp extends GameObject {

    protected int bulletCount;

    public PowerUp(float x, float y, ObjectID id, Controller c) {

        super(x, y, id, c);
        idleImage = images.mysteryBlock;
        bulletCount = 0;
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(idleImage,(int)x,(int)y,idleImage.getWidth(),idleImage.getHeight(),null);
    }

    public void function(Player p){
        
    }
}
