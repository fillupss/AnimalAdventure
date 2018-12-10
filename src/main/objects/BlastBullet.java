package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class BlastBullet extends Bullet {
    public BlastBullet(float x, float y, ObjectID id, Controller handler, boolean faceDirection) {
        super(x, y, id, handler, faceDirection);
        idleImage = images.robot[0];
        this.damage = 2.0f;
        maxDistance = 400;
        currentDistance = 0;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(idleImage,(int)x,(int)y, (int)(idleImage.getWidth()/3.45),(int)(idleImage.getHeight()/3.45),null);
    }
}
