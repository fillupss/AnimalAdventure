package main.brain;

import main.objects.GameObject;
import main.objects.Player;

import java.util.ArrayList;
import java.awt.Graphics;

public class Controller {

    ArrayList<GameObject> handler = new ArrayList<>();

    public void tick() {
        for (int i = 0; i < handler.size(); i++) {
            GameObject temp = handler.get(i);
            temp.tick();
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < handler.size(); i++) {
            GameObject temp = handler.get(i);
            temp.draw(g);
        }
    }


    public void addObject(GameObject obj) {
        this.handler.add(obj);
    }

    public void removeAll(){
        this.handler.clear();
    }

    public void removeObject(GameObject obj) {
        this.handler.remove(obj);
    }

    public void removeSandBlocks(){
        for (int i = 0; i < handler.size(); i++) {
            if(handler.get(i).getId().equals(ObjectID.SandBlock)){
                removeObject(handler.get(i));
                i--;
            }
        }
    }

    public void removeAllEnemies(){
        for (int i = 0; i < handler.size(); i++) {
            if(handler.get(i).getId().equals(ObjectID.Enemy)){
                removeObject(handler.get(i));
                i--;
            }
        }
    }

    public Player getPlayer(){
        for (int i = 0; i < handler.size(); i++) {
            if(handler.get(i).getId().equals(ObjectID.Player)){
                Player p = (Player)handler.get(i);
                return p;
            }
        }
        return null;
    }

    public ArrayList<GameObject> getHandler(){
        return handler;
    }
}
