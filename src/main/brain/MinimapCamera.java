package main.brain;
import main.GameWorld;
import main.objects.GameObject;

public class MinimapCamera {

    private float x, y;

    // only for "x" position
    // positive offset -> push every object to the right
    // left offset -> push every object to the left
    public MinimapCamera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject player){
        // x and y is the offset of the original origin
        // where the offset will be Game.Width/2 for every object
        // but the camera will always point to the player
        // divide by 4 is the scale between the main screen to minimap
        x = -player.getX()/4 + GameWorld.WIDTH/4/2;
        y = 0;

    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

}

