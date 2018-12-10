package main.objects;

import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;

import java.awt.*;

public class Grass extends Block {

    public Grass(float x, float y, ObjectID id, Controller handler, LevelLoader loader) {
        super(x, y, id, handler, loader);
        if(loader.getLevel() == 0){
            idleImage = images.block[1];
        }
        else if(loader.getLevel() == 1){
            idleImage = images.block[3];
        }
        else if(loader.getLevel() == 2){
            idleImage = images.block[5];
        }
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
