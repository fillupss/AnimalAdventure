package main.gui;

import main.GameWorld;
import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;
import main.objects.Images;
import main.objects.Player;

import java.awt.*;


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

    private void findPlayer(){
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
            g.setColor(Color.WHITE);
            g.drawString("Game Over!!", GameWorld.WIDTH/8 * 3, GameWorld.HEIGHT/4);
            g.setFont(new Font("arial",1,18));
            g.drawString("Player score: " + scoreP1, GameWorld.WIDTH/4, GameWorld.HEIGHT/2);
        }
        else {
            // displaying revives left
            for(int i = 1; i < p.getLifeLeft() + 1; i++){
                g.setColor(Color.black);
                g.drawString("Revives Left:",0,16);
                g.drawImage(images.dogPlayer[30], 22 * i + 64, 2, 22,32, null);
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
        }
    }
}

