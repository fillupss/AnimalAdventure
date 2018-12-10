package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class Flag extends GameObject {

    public Flag(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        idleImage = images.flag;
    }

    @Override
    public void tick() {
        x += 0;
        y += 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(idleImage,(int)x, (int)y,(int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE), null);
    }

    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)x,(int)y,(int)(idleImage.getWidth()/SCALE), (int)(idleImage.getHeight()/SCALE));
    }
}
