package main.objects;

import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;

import java.awt.*;

public class Block extends GameObject {

    protected LevelLoader loader;

    public Block(float x, float y, ObjectID id, Controller handler, LevelLoader loader) {
        super(x, y, id, handler);
        this.loader = loader;
        if(loader.getLevel() == 0){
            idleImage = images.block[0];
        }
        else if(loader.getLevel() == 1){
            idleImage = images.block[2];
        }
        else if(loader.getLevel() == 2){
            idleImage = images.block[4];
        }
    }

    @Override
    public void tick() {
        x += 0;
        y += 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(idleImage,(int)x,(int)y,null);
    }
}
