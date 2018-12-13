package main.gui;

import main.GameWorld;
import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;
import main.objects.Images;
import main.objects.Player;

import java.awt.*;

/*
    For my future reference
    To improve the HUD and Menu make the any player method that is needed in HUD to be static
    so it can prevent bugs
 */
public class HUD {

    private Controller handler;
    private LevelLoader levelLoader;
    private Images images;
    private Player p;
    private int scoreP1 = 0;

    public HUD(Controller handler, LevelLoader levelLoader){
        this.handler = handler;
        this.levelLoader = levelLoader;
        this.images = GameWorld.getImages();
        findPlayer();
    }

    public void findPlayer(){
        for(int i = 0; i < handler.getHandler().size(); i++){
            if(handler.getHandler().get(i).getId().equals(ObjectID.Player)){
                p = (Player) handler.getHandler().get(i);
            }
        }
    }

    public void tick(){
        scoreP1 = (Player.getBulletsHit() * 50 + Player.getFinalHit() * 100);
    }

    public void draw(Graphics g){
        if(p.getLifeLeft() == -1 || levelLoader.getLevel() == 4){
            handler.removeAll();
            g.setFont(new Font("arial",1,36));
            g.setColor(Color.BLACK);
            g.drawString("Game Over!!", GameWorld.WIDTH/8 * 3, GameWorld.HEIGHT/4);
            g.setFont(new Font("arial",1,18));
            g.drawString("Player score: " + scoreP1, GameWorld.WIDTH/8 * 3 + 30, GameWorld.HEIGHT/2);
            Font fnt2 = new Font("arial", 1, 30);
            g.setFont(fnt2);
            g.drawRect(20,GameWorld.HEIGHT - 140,200,64);
            g.drawString("Retry",70,GameWorld.HEIGHT - 100);
            g.drawRect(400,GameWorld.HEIGHT - 140,200,64);
            g.drawString("Quit",470,GameWorld.HEIGHT - 100);
        }
        else {
            // displaying revives left
            for(int i = 1; i < p.getLifeLeft() + 1; i++){
                g.setColor(Color.black);
                g.drawString("Revives Left:",0,16);
                g.drawImage(images.dogPlayer[28], 22 * i + 64, 2, 22,32, null);
            }

            // display health left
            for(int i = 1; i < p.getHealth() + 1; i++){
                g.setColor(Color.black);
                g.drawString("Health Left:",0,70);
                g.drawImage(images.heart, images.heart.getWidth() * i + 54, 54,null);
            }

            // display the score and bullet count
            g.setColor(Color.red);
            g.drawString("Score: " + scoreP1, GameWorld.WIDTH/16,GameWorld.HEIGHT-32);
            g.drawString("Bullet count: " + p.getCount() + "/" + p.getTotalCount(),
                    GameWorld.WIDTH/2, GameWorld.HEIGHT - 32);

            // display the type of bullet that is being used from the player
            if(p.getTotalCount() == 5){
                g.drawImage(images.playerBullet[0],GameWorld.WIDTH/2 - 30,GameWorld.HEIGHT - 35, null);
                g.drawImage(images.playerBullet[0],GameWorld.WIDTH/2 - 30,GameWorld.HEIGHT - 52, null);
            }
            else if(p.getTotalCount() == 2){
                g.drawImage(images.playerBullet[1], GameWorld.WIDTH/2 - 30,GameWorld.HEIGHT - 50,
                        images.playerBullet[1].getWidth()/2,images.playerBullet[1].getHeight()/2,null);
            }
            else{
                g.drawImage(images.playerBullet[0], GameWorld.WIDTH/2 - 30,GameWorld.HEIGHT - 40,null);
            }
        }
    }
}

