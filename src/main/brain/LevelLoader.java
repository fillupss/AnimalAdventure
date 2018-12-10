package main.brain;

import main.GameWorld;
import main.objects.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public class LevelLoader {

    private Player savePlayer;
    private Controller handler;
    private Camera cam;
    private Images images;
    private int level;
    private Random r = new Random();

    public LevelLoader(Controller handler, Camera cam){
        this.handler = handler;
        this.cam = cam;
        this.images = GameWorld.getImages();
        this.level = 0;
    }

    private void LoadImageLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        for(int xx = 0; xx < h; xx++){
            for(int yy = 0; yy < w; yy++){
                // get the current pixel on the image
                // then classify the pixel based on RGB scale
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                // check if the pixel is white
                if(red == 255 && green == 255 && blue == 255)
                    handler.addObject(new Block(xx * 32, yy*32, ObjectID.Block, handler, this));

                // check if the pixel is blue
                else if(red == 0 && green == 0 && blue == 255){
                    if(level == 0){
                        savePlayer = new Player(xx * 32, yy*32, ObjectID.Player, handler, this);
                    }
                    else{
                        savePlayer.setX(xx*32);
                        savePlayer.setY(yy*32);
                    }
                    handler.addObject(savePlayer);
                    //handler.addObject(new Player(xx * 32, yy*32, ObjectID.Player, handler, this));
                }

                // check if the pixel is gray
                else if(red == 128 && green == 128 && blue == 128)
                    handler.addObject(new Grass(xx * 32, yy*32, ObjectID.Block, handler, this));

                // check if the pixel is yellow
                else if(red == 255 && green == 216 && blue == 0)
                    handler.addObject(new Flag(xx * 32, yy*32, ObjectID.Flag, handler));

                // check if the pixel is green
                else if(red == 76 && green == 255 && blue == 0){
                    int random = r.nextInt(2);
                    //handler.addObject(new BulletBillPowerUp(xx * 32, yy * 32, ObjectID.PowerUp, handler));
                    if(random == 0)
                        handler.addObject(new BulletBillPowerUp(xx * 32, yy * 32, ObjectID.PowerUp, handler));
                    else
                        handler.addObject(new DoubleBulletPowerUp(xx * 32, yy * 32, ObjectID.PowerUp, handler));
                }

                // check if the pixel is red
                else if(red == 255 && green == 0 && blue == 0){
                    handler.addObject(new Zombie(xx*32,yy*32,ObjectID.Enemy, handler));
                }

                // check if the pixel is orange
                else if(red == 255 && green == 106 && blue == 0){
                    handler.addObject(new Sheep(xx*32,yy*32,ObjectID.Enemy, handler));
                }

                // check if the pixel is teal
                else if(red == 0 && green == 255 && blue == 255){
                    handler.addObject(new Robot(xx * 32, yy* 32, ObjectID.Enemy, handler));
                }

                // check if the pixel is pink
                else if(red == 255 && green == 127 && blue == 127){
                    handler.addObject(new Sand(xx*32,yy*32, ObjectID.SandBlock, handler, this));
                }

                // check if the pixel is purple
                else if(red == 178 && green == 0 && blue == 255){
                    handler.addObject(new Cat(xx*32,yy*32,ObjectID.Enemy,handler));
                }
            }
        }
    }

    public void switchLevel(){
        clearLevel();
        cam.setX(0);
        switch(level){
            case 0:
                LoadImageLevel(images.levels[2]);
                break;
            case 1:
                LoadImageLevel(images.levels[1]);
                break;
            case 2:
                LoadImageLevel(images.levels[2]);
                break;
        }
        level++;
    }

    private void clearLevel(){
        handler.removeAll();
    }

    public int getLevel(){
        return level;
    }


}
