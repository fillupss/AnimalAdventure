package main.brain;

import java.awt.image.BufferedImage;

public class Animation {
    private long lastTime;
    private BufferedImage[] frames;
    private int index, timer, speed;

    public Animation(int speed, BufferedImage[] frames){
        this.speed = speed;
        this.frames = frames;
        this.index = 0;
        this.timer = 0;
        this.lastTime = System.currentTimeMillis();
    }

    // as the game is running, there will always be a time incrementaion (in this case its ms)
    // so if the present time minus last time recorded is greater than the
    // speed you want the animation to run at
    // then the index of the bufferedimage array will go up
    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(timer > speed){
            if(index == frames.length - 1)
                index = 0;
            else{
                index++;
            }
            timer = 0;
        }
    }


    public BufferedImage getCurrentFrame(){
        return frames[index];
    }

    public int getIndex(){
        return index;
    }

    public void setIndex( int index){
        this.index = index;
    }
}

