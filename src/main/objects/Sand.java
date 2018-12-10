package main.objects;

import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;

import java.awt.*;

public class Sand extends Block {

    public Sand(float x, float y, ObjectID id, Controller handler, LevelLoader loader) {
        super(x, y, id, handler, loader);
        idleImage = images.block[6];
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
