package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;

public abstract class Enemy extends MovingGameObjects {

    protected int currentDistance;
    protected int maxDistance; // once it traveled this much distance reverse its direction
    private int damage;

    public Enemy(float x, float y, ObjectID id, Controller handler) {
        super(x, y, id, handler);
        currentDistance = 0;
        damage = 1;
    }

    public abstract void tick();

    public abstract void draw(Graphics g);

    @Override
    public void collision() {
        for(int i = 0; i < handler.getHandler().size(); i++){
            GameObject temp = handler.getHandler().get(i);
            if(temp.getId().equals(ObjectID.Player)){
                Player p = (Player) temp;
                if(this.getCollisionBounds().intersects(temp.getCollisionBounds())){
                    this.dealDamage(p);
                    p.setVelX(p.getvelX() * -1);
                    p.setInvincible(true);
                }
            }
            else if(temp.getId().equals(ObjectID.Block)){
                if(this.getCollisionBounds().intersects(temp.getCollisionBounds())){
                    velX *= -1;
                    currentDistance = 0;
                }
            }
        }
    }

    public abstract void shoot();

    @Override
    public void isDead() {
        if(health <= 0)
            handler.removeObject(this);
    }

    public void dealDamage(Player p){
        if(!p.getInvincible()){
            p.setHealth(p.getHealth() - damage);
            p.isDead();
        }
    }


}
