package main.objects;

import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends MovingGameObjects {

    protected float damage;
    protected float maxDistance;
    protected float currentDistance;

    public Bullet(float x, float y, ObjectID id, Controller handler, boolean faceDirection) {
        super(x, y, id, handler);
        this.idleImage = images.playerBullet[0];
        this.damage = 15.0f;
        maxDistance = 300;
        currentDistance = 0;
        if(faceDirection){
            velX = -10;
        }
        else if(!faceDirection){
            velX = 10;
        }

    }

    @Override
    public void tick() {
        x += velX;
        y += 0;
        currentDistance += Math.abs(velX);
        collision();
        if(currentDistance >= maxDistance){
            handler.removeObject(this);
        }
    }

    @Override
    public void draw(Graphics g) {
        if(velX > 0)
            g.drawImage(idleImage,(int)x,(int)y,null);
        else{
            AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
            rotation.rotate(Math.toRadians(180), this.idleImage.getWidth()/2.0, this.idleImage.getHeight()/2.0);
            Graphics2D ge = (Graphics2D) g;
            ge.drawImage(this.idleImage, rotation, null);
        }

    }

    @Override
    public void collision() {
        for(int i = 0; i < handler.getHandler().size(); i++){
            GameObject tempObj = handler.getHandler().get(i);
            if(((tempObj.getId().equals(ObjectID.Enemy)) || (tempObj.getId().equals(ObjectID.EnemyBoss))) && this.id.equals(ObjectID.PlayerBullet)){
                if(this.getCollisionBounds().intersects(tempObj.getCollisionBounds())){
                    if(tempObj.getId().equals(ObjectID.EnemyBoss)){
                        Cat cat = (Cat) tempObj;
                        cat.setHurting(true);
                    }
                    Player.setBulletsHit(Player.getBulletsHit() + 1);
                    this.dealDamage(tempObj);
                    handler.removeObject(this);
                }
            }
            else if(tempObj.getId().equals(ObjectID.Player) && this.id.equals(ObjectID.EnemyBullet)){
                if(this.getCollisionBounds().intersects(tempObj.getCollisionBounds())){
                    this.dealDamage(tempObj);
                    handler.removeObject(this);
                }
            }
        }
    }

    public void dealDamage(GameObject obj){
        if(obj.getId().equals(ObjectID.Enemy) || obj.getId().equals(ObjectID.EnemyBoss)){
            Enemy temp = (Enemy) obj;
            temp.setHealth(temp.getHealth() - damage);
            temp.isDead();
        }
        else if(obj.getId().equals(ObjectID.Player)){
            Player temp = (Player) obj;
            if(!temp.getInvincible()){
                temp.setInvincible(true);
                temp.getHurtLeft().setIndex(0);
                temp.getHurtRight().setIndex(0);
                temp.setHurting(true);
                temp.setHealth(temp.getHealth() - damage);
                temp.isDead();
            }
        }
    }

    @Override
    public void shoot() {
        // do nothing
    }

    @Override
    public void isDead() {
        // do nothing
    }
}
