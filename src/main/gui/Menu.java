package main.gui;

import main.GameWorld;
import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;
import main.objects.Player;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {

    private int mx = 0;
    private int my = 0;
    private GameWorld game;
    private Controller handler;
    private LevelLoader levelLoader;
    private HUD hud;
    private Player player;
    private boolean helpState = false;

    public Menu(GameWorld game, Controller handler, LevelLoader levelLoader, HUD hud){
        this.game = game;
        this.handler = handler;
        this.levelLoader = levelLoader;
        this.hud = hud;
        findPlayer();
    }

    private void findPlayer(){
        for(int i = 0; i < handler.getHandler().size(); i++){
            if(handler.getHandler().get(i).getId().equals(ObjectID.Player)){
                player = (Player) handler.getHandler().get(i);
            }
        }
    }

    public void draw(Graphics g){
        if(game.isPaused()){
            if(!helpState){
                Font fnt2 = new Font("arial", 1, 30);
                g.setColor(Color.WHITE);
                g.setFont(fnt2);
                g.drawString("Paused...", GameWorld.WIDTH/2-30, 70);
                g.drawRect(GameWorld.WIDTH/2-50, 150, 200, 64);
                g.drawString("Resume", GameWorld.WIDTH/2-10, 190);
                g.drawRect(GameWorld.WIDTH/2-50, 250, 200, 64);
                g.drawString("Help", GameWorld.WIDTH/2+5, 290);
                g.drawRect(GameWorld.WIDTH/2-50, 350, 200, 64);
                g.drawString("Quit", GameWorld.WIDTH/2+5, 390);
            }
            else{
                Font fnt2 = new Font("arial", 1, 30);
                g.setColor(Color.white);
                g.setFont(fnt2);
                g.drawRect(20,GameWorld.HEIGHT - 140,200,64);
                g.drawString("Back",70,GameWorld.HEIGHT - 100);
                g.setColor(Color.black);
                g.drawString("Controls: ",100,100);
                g.drawString("W -> Jump",100,145);
                g.drawString("A -> Left",100,190);
                g.drawString("D -> Right",100,235);
                g.drawString("Space Bar -> Shoot",100,280);
                g.drawString("How to Play:",450,100);
                g.drawString("Just run and gun! :)",450,145);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        if(player.getLifeLeft() == -1 || levelLoader.getLevel() == 4){
            if(mx > 400 && mx < 600 && my > GameWorld.HEIGHT - 140 && my < GameWorld.HEIGHT - 140 + 64){
                System.exit(1);
            }
            // this part is buggy and needs to be fixed
            // technically works but need to keep spam clicking the retry button
            else if(mx > 20 && mx < 220 && my > GameWorld.HEIGHT - 140 && my < GameWorld.HEIGHT - 140 + 64){
                levelLoader.setLevel(0);
                levelLoader.switchLevel();
                this.findPlayer();
                hud.findPlayer();
                Player.setFinalHit(0);
                Player.setBulletsHit(0);
            }
        }
        else if(game.isPaused() && helpState){
            if(mx > 20 && mx < 220 && my > GameWorld.HEIGHT - 140 && my < GameWorld.HEIGHT - 140 + 64){
                helpState = false;
            }
        }
        else if(game.isPaused() && !helpState){
            if(mx > GameWorld.WIDTH/2-50 && mx < GameWorld.WIDTH/2+150 && my > 150 && my < 214){
                game.setPaused(!game.isPaused());
            }
            else if(mx > GameWorld.WIDTH/2-50 && mx < GameWorld.WIDTH/2+150 && my > 250 && my < 314){
                helpState = true;
            }
            else if(mx > GameWorld.WIDTH/2-50 && mx < GameWorld.WIDTH/2+150 && my > 350 && my < 414){
                System.exit(1);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
