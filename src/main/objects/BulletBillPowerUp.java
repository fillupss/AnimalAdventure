package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class BulletBillPowerUp extends PowerUp {

    public BulletBillPowerUp(float x, float y, ObjectID id, Controller c) {

        super(x, y, id, c);
        bulletCount = 2;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    @Override
    public void function(Player p) {
        p.setCount(bulletCount);
        p.setTotalCount(bulletCount);
        p.setMissilePowerUp(true);
        p.setDoubleShotPowerUp(false);
    }
}
