package main.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Images {
    public BufferedImage cloud = null;
    public BufferedImage iceMountain = null;
    public BufferedImage thunderStorm = null;
    public BufferedImage flag = null;
    public BufferedImage mysteryBlock = null;
    public BufferedImage heart = null;
    public BufferedImage[] playerBullet = new BufferedImage[2];
    public BufferedImage[] levels = new BufferedImage[3]; // change when added final level
    public BufferedImage[] block = new BufferedImage[7]; // change when added final level
    public BufferedImage[] dogPlayer = new BufferedImage[60];
    public BufferedImage[] catBoss = new BufferedImage[38];
    public BufferedImage[] sheep = new BufferedImage[20];
    public BufferedImage[] zombie = new BufferedImage[28];
    public BufferedImage[] robot = new BufferedImage[14];

    public Images(){
        getImages();
    }

    private void getImages(){
        try{
            // background
            cloud = ImageIO.read(Images.class.getResourceAsStream("/background.png"));
            iceMountain = ImageIO.read(Images.class.getResourceAsStream("/background2.png"));
            thunderStorm = ImageIO.read(Images.class.getResourceAsStream("/background3.png"));
            flag = ImageIO.read(Images.class.getResourceAsStream("/flag.png"));
            mysteryBlock = ImageIO.read(Images.class.getResourceAsStream("/MysteryBox.png"));
            heart = ImageIO.read(Images.class.getResourceAsStream("/Heart.png"));

            // player bullets
            playerBullet[0] = ImageIO.read(Images.class.getResourceAsStream("/Bullet.png"));
            playerBullet[1] = ImageIO.read(Images.class.getResourceAsStream("/Missile.png"));

            // robot bullet
            robot[0] = ImageIO.read(Images.class.getResourceAsStream("/rob/Bullet.png"));

            // loading the levels
            levels[0] = ImageIO.read(Images.class.getResourceAsStream("/level1.png"));
            levels[1] = ImageIO.read(Images.class.getResourceAsStream("/level2.png"));
            levels[2] = ImageIO.read(Images.class.getResourceAsStream("/level3.png"));

            // loading the blocks
            block[0] = ImageIO.read(Images.class.getResourceAsStream("/blocks/dirtBlock.png"));
            block[1] = ImageIO.read(Images.class.getResourceAsStream("/blocks/grassDirtBlock.png"));
            block[2] = ImageIO.read(Images.class.getResourceAsStream("/blocks/iceDirtBlock.png"));
            block[3] = ImageIO.read(Images.class.getResourceAsStream("/blocks/iceStonePathBlock.png"));
            block[4] = ImageIO.read(Images.class.getResourceAsStream("/blocks/darkDirtBlock.png"));
            block[5] = ImageIO.read(Images.class.getResourceAsStream("/blocks/darkGrassDirtBlock.png"));
            block[6] = ImageIO.read(Images.class.getResourceAsStream("/blocks/sandBlock.png"));

            // idle position for the player
            int i = 1;
            dogPlayer[30] = ImageIO.read(Images.class.getResourceAsStream("/dog/Idle (1).png"));
            dogPlayer[31] = ImageIO.read(Images.class.getResourceAsStream("/dog/Idle_Left (1).png"));

            // idle position for the sheep enemy
            sheep[12] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepIdle.png"));
            sheep[13] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepIdleLeft.png"));

            // idle 'sorta' position for the zombie enemy
            zombie[14] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Walk (1).png"));
            zombie[21] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Walk_Left (1).png"));

            // idle position for the robot enemy
            robot[1] = ImageIO.read(Images.class.getResourceAsStream("/rob/Idle (1).png"));

            // idle 'sorta' position for the cat enemy
            catBoss[26] = ImageIO.read(Images.class.getResourceAsStream("/cat/Walk_Left (1).png"));
            catBoss[17] = ImageIO.read(Images.class.getResourceAsStream("/cat/Walk (1).png"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
