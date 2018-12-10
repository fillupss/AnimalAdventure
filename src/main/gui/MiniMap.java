package main.gui;

import main.GameWorld;
import main.brain.Controller;
import main.brain.LevelLoader;
import main.brain.ObjectID;
import main.objects.GameObject;

import java.awt.*;

public class MiniMap {

    public final static int WIDTH = GameWorld.WIDTH / 4;
    public final static int HEIGHT = GameWorld.HEIGHT / 4;
    public final static int X = GameWorld.WIDTH - WIDTH;
    public final static int Y = 0;
    private Controller handler;
    private LevelLoader levelLoader;

    public MiniMap(Controller handler, LevelLoader levelLoader){

        this.handler = handler;
        this.levelLoader = levelLoader;
    }

    public void drawFrame(Graphics g){
        if(levelLoader.getLevel() >= 3){
            g.setColor(new Color(182,178,0));
        }
        else{
            g.setColor(Color.cyan);
        }
        g.fillRect(X,Y, this.WIDTH, this.HEIGHT);

        // make the boarder more thick
        Graphics2D g2 = (Graphics2D)g;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(139,69,19));
        g2.drawRect(X,Y, this.WIDTH, this.HEIGHT);
        g2.setStroke(oldStroke);
    }

    public void drawObjects(Graphics g){
        // go through each object in the controller and then scale their position based on the proportion
        // of Map -> MiniMap size and place them depending on where the MiniMap is located
        for(int i = 0; i < handler.getHandler().size(); i++){
            GameObject tempObj = handler.getHandler().get(i);
            if(tempObj.getId().equals(ObjectID.Player)){
                g.setColor(Color.green);
                g.fillOval((int)tempObj.getX()/4 + this.X , (int)tempObj.getY()/4 + this.Y,6,6);
            }
            else if(tempObj.getId().equals(ObjectID.EnemyBullet)){
                g.setColor(Color.black);
                g.fillRect((int)tempObj.getX()/4 + this.X , (int)tempObj.getY()/4 + this.Y,4,4);
            }
            else if(tempObj.getId().equals(ObjectID.PlayerBullet)){
                g.setColor(Color.orange);
                g.fillRect((int)tempObj.getX()/4 + this.X , (int)tempObj.getY()/4 + this.Y,4,4);
            }
            else if(tempObj.getId().equals(ObjectID.Enemy)){
                g.setColor(Color.red);
                g.fillOval((int)tempObj.getX()/4 + this.X , (int)tempObj.getY()/4 + this.Y,6,6);
            }
            else if(tempObj.getId().equals(ObjectID.Block)){
                g.setColor(Color.gray);
                g.fillRect((int)tempObj.getX()/4 + this.X , (int)tempObj.getY()/4 + this.Y,8,8);
            }
            else if(tempObj.getId().equals(ObjectID.PowerUp)){
                g.setColor(Color.white);
                g.fillRect((int)tempObj.getX()/4 + this.X , (int)tempObj.getY()/4 + this.Y,8,8);
            }
            else if(tempObj.getId().equals(ObjectID.Flag)){
                g.setColor(Color.yellow);
                g.fillRect((int)tempObj.getX()/4 + this.X, (int)tempObj.getY()/4 + this.Y,6,6);
            }
        }
    }
}
