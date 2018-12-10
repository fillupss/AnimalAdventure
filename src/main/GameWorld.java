package main;

import main.brain.*;
import main.gui.HUD;
import main.gui.Window;
import main.gui.MiniMap;
import main.objects.Images;
import main.objects.Robot;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameWorld extends Canvas implements Runnable {

    public final static int WIDTH = 800;
    public final static int HEIGHT = 626;
    private BufferedImage clouds = null;
    private BufferedImage icyMountains = null;
    private BufferedImage rainy = null;
    private Thread thread;
    private boolean running = false;
    static private Images images;
    private Controller handler;
    private Camera cam;
    private MinimapCamera minimapCam;
    private LevelLoader levelLoader;
    private HUD hud;
    private MiniMap minimap;

    public GameWorld(){
        new Window(WIDTH, HEIGHT, "Animal Adventure", this);
        this.start();
    }

    public void init(){

    images = new Images();
    handler = new Controller();
    this.addKeyListener(new KeyInput(handler));
    cam = new Camera(0,0);
    minimapCam = new MinimapCamera(GameWorld.WIDTH - WIDTH,0);
    levelLoader = new LevelLoader(handler, cam);
    levelLoader.switchLevel();
    hud = new HUD(handler, levelLoader);
    minimap = new MiniMap(handler, levelLoader);

    clouds = images.cloud;
    icyMountains = images.iceMountain;
    rainy = images.thunderStorm;

    }

    public void start(){
        // allocate a new thread that links to the game
        thread = new Thread(this);
        // will go to the run function of this class
        thread.start();
        running = true;
    }

    public void stop(){
        try{
            // waits for thread to stop running (no more instructions to go through)
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
        System.exit(1);

    }

    public void run(){
        init();
        this.requestFocus(); // do not need to click on screen to interact with it
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                draw();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick(){

        handler.tick();
        for(int i = 0; i < handler.getHandler().size(); i++){
            if(handler.getHandler().get(i).getId().equals(ObjectID.Player)){
                cam.tick(handler.getHandler().get(i));
                minimapCam.tick(handler.getHandler().get(i));
            }
        }
        hud.tick();
    }

    public void draw(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        minimap.drawFrame(g);
        g.translate((int)minimapCam.getX(), (int)minimapCam.getY());
        minimap.drawObjects(g);
        g.translate((int)minimapCam.getX() * -1, (int)minimapCam.getY() * -1);

        if(levelLoader.getLevel() == 1){
            g.setColor(Color.cyan);
            g.fillRect(0,0, MiniMap.X, HEIGHT);
            g.fillRect(MiniMap.X,MiniMap.HEIGHT,WIDTH - MiniMap.WIDTH,HEIGHT-MiniMap.HEIGHT);
            g.drawImage(clouds,50,50, clouds.getWidth()/2,clouds.getHeight()/2,null);
        }
        else if(levelLoader.getLevel() == 2){
            g.setColor(new Color(178,252,253));
            g.fillRect(0,0, MiniMap.X, HEIGHT);
            g.fillRect(MiniMap.X,MiniMap.HEIGHT,WIDTH - MiniMap.WIDTH,HEIGHT-MiniMap.HEIGHT);
            g.drawImage(icyMountains,0,0, icyMountains.getWidth(),icyMountains.getHeight(),null);
        }
        else if(levelLoader.getLevel() == 3){
            g.setColor(new Color(91,127,0));
            g.fillRect(0,0, MiniMap.X, HEIGHT);
            g.fillRect(MiniMap.X,MiniMap.HEIGHT,WIDTH - MiniMap.WIDTH,HEIGHT-MiniMap.HEIGHT);
            g.drawImage(rainy,50,50, rainy.getWidth(),rainy.getHeight(),null);
        }


        g.translate((int)cam.getX(), (int)cam.getY());
        handler.draw(g);
        g.translate((int)cam.getX() * -1, (int)cam.getY() * -1);

        hud.draw(g);
        g.dispose();
        bs.show();
    }

    public static Images getImages(){
        return images;
    }

    public static void main(String args[]){
        new GameWorld();
    }
}

