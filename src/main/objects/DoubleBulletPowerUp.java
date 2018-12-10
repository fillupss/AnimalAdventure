package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public class DoubleBulletPowerUp extends PowerUp {

    public DoubleBulletPowerUp(float x, float y, ObjectID id, Controller c) {

        super(x, y, id, c);
        bulletCount = 5;
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
        p.setMissilePowerUp(false);
        p.setDoubleShotPowerUp(true);
    }
}
