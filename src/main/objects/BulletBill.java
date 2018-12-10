package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class BulletBill extends Bullet {

    public BulletBill(float x, float y, ObjectID id, Controller handler, boolean faceDirection) {
        super(x, y, id, handler, faceDirection);
        idleImage = images.playerBullet[1];
        damage = 80;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
