package main.objects;

import main.brain.Animation;
import main.brain.Controller;
import main.brain.ObjectID;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BulletBill extends Bullet {

    private boolean impact = false;
    private Animation explosion;
    private BufferedImage[] bulletExplosion = new BufferedImage[7];

    public BulletBill(float x, float y, ObjectID id, Controller handler, boolean faceDirection) {
        super(x, y, id, handler, faceDirection);
        idleImage = images.playerBullet[1];
        damage = 95;

        for(int i = 0; i < 7; i++){
            bulletExplosion[i] = images.missileExplosion[i];
        }
        explosion = new Animation(200,bulletExplosion);
    }

    @Override
    public void tick() {

        super.tick();
        explosion.tick();
    }

    @Override
    public void draw(Graphics g) {
        if(impact){
            g.drawImage(explosion.getCurrentFrame(),(int)x,(int)y,null);
            if(explosion.getIndex() == bulletExplosion.length-1){
                impact = false;
                handler.removeObject(this);
            }
        }
        else if(velX > 0)
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
                if(!impact){
                    if(this.getCollisionBounds().intersects(tempObj.getCollisionBounds())){
                        velX = 0;
                        if(tempObj.getId().equals(ObjectID.EnemyBoss)){
                            Cat cat = (Cat) tempObj;
                            cat.setHurting(true);
                        }
                        Player.setBulletsHit(Player.getBulletsHit() + 1);
                        impact = true;
                        this.resetExplosion();
                        this.dealDamage(tempObj);
                        damage = 0.75f;
                    }
                }
                else{
                    if(this.getExplosionBounds().intersects(tempObj.getCollisionBounds())){
                        velX = 0;
                        if(tempObj.getId().equals(ObjectID.EnemyBoss)){
                            Cat cat = (Cat) tempObj;
                            cat.setHurting(true);
                        }
                        this.dealDamage(tempObj);
                    }
                }
            }
        }
    }

    private void resetExplosion(){
        explosion.setIndex(0);
    }

    private Rectangle getExplosionBounds(){
        return new Rectangle((int)x, (int)y, explosion.getCurrentFrame().getWidth(), explosion.getCurrentFrame().getHeight());
    }
}
