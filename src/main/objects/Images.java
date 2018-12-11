package main.objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Images {
    public BufferedImage cloud = null;
    public BufferedImage iceMountain = null;
    public BufferedImage thunderStorm = null;
    public BufferedImage flag = null;
    public BufferedImage mysteryBlock = null;
    public BufferedImage heart = null;
    public BufferedImage[] playerBullet = new BufferedImage[2];
    public BufferedImage[] missileExplosion = new BufferedImage[7];
    public BufferedImage[] levels = new BufferedImage[3]; // change when added final level
    public BufferedImage[] block = new BufferedImage[7]; // change when added final level
    public BufferedImage[] dogPlayer = new BufferedImage[58];
    public BufferedImage[] catBoss = new BufferedImage[44];
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
            dogPlayer[28] = ImageIO.read(Images.class.getResourceAsStream("/dog/Idle (1).png"));
            dogPlayer[29] = ImageIO.read(Images.class.getResourceAsStream("/dog/Idle_Left (1).png"));

            // idle position for the sheep enemy
            sheep[12] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepIdle.png"));
            sheep[13] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepIdleLeft.png"));

            // idle 'sorta' position for the zombie enemy
            zombie[14] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Walk (1).png"));
            zombie[21] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Walk_Left (1).png"));

            // idle position for the robot enemy
            robot[1] = ImageIO.read(Images.class.getResourceAsStream("/rob/Idle (1).png"));

            // idle 'sorta' position for the cat enemy
            catBoss[35] = ImageIO.read(Images.class.getResourceAsStream("/cat/Walk_Left (1).png"));
            catBoss[26] = ImageIO.read(Images.class.getResourceAsStream("/cat/Walk (1).png"));

            // set of animation images with length 3
            for(int i = 1; i <= 3; i++){
                // sheep walking left/right (file name is misleading)
                sheep[i + 13] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepWalk (" + i + ").png"));
                sheep[i + 16] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepWalkLeft (" + i + ").png"));
            }

            // set of animation images with length 4
            for(int i = 1; i <= 4; i++){
                // robot shooting
                robot[i + 9] = ImageIO.read(Images.class.getResourceAsStream("/rob/Shoot (" + i + ").png"));
            }

            // set of animation images with length 6
            for(int i = 1; i <= 6; i++){

                // sheep attack left/right (file name is misleading)
                sheep[i-1] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepAttack (" + i + ").png"));
                sheep[i + 5] = ImageIO.read(Images.class.getResourceAsStream("/sheeep/SheepAttackLeft (" + i + ").png"));

                // dog death right/left and jump right/left
                dogPlayer[i-1] = ImageIO.read(Images.class.getResourceAsStream("/dog/Dead (" + i + ").png"));
                dogPlayer[i+5] = ImageIO.read(Images.class.getResourceAsStream("/dog/Dead_Left (" + i + ").png"));
                dogPlayer[i+29] = ImageIO.read(Images.class.getResourceAsStream("/dog/Jump (" + i + ").png"));
                dogPlayer[i+35] = ImageIO.read(Images.class.getResourceAsStream("/dog/Jump_Left (" + i + ").png"));

                // cat death right/left
                catBoss[i-1] = ImageIO.read(Images.class.getResourceAsStream("/cat/Dead (" + i + ").png"));
                catBoss[i+5] = ImageIO.read(Images.class.getResourceAsStream("/cat/Dead_Left (" + i + ").png"));
            }

            // set of animation images with length 7
            for(int i = 1; i <= 7; i++){

                // all zombie animations
                zombie[i - 1] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Attack (" + i + ").png"));
                zombie[i + 6] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Attack_Left (" + i + ").png"));
                zombie[i + 13] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Walk (" + i + ").png"));
                zombie[i + 20] = ImageIO.read(Images.class.getResourceAsStream("/maleZombie/Walk_Left (" + i + ").png"));

                // cat hurting right/left
                catBoss[i+11] = ImageIO.read(Images.class.getResourceAsStream("/cat/Hurt (" + i + ").png"));
                catBoss[i+18] = ImageIO.read(Images.class.getResourceAsStream("/cat/Hurt_Left (" + i + ").png"));

                // missile explosion
                missileExplosion[i-1] = ImageIO.read(Images.class.getResourceAsStream("/missileExplosion/BulletExplosion" + i + ".png"));
            }

            // set of animation images with length 8
            for(int i = 1; i <= 8; i++){

                // robot attacking with melee
                robot[i + 1] = ImageIO.read(Images.class.getResourceAsStream("/rob/Melee (" + i + ").png"));

                // dog player hurt right/left, walking right/left
                dogPlayer[i + 11] = ImageIO.read(Images.class.getResourceAsStream("/dog/Hurt (" + i + ").png"));
                dogPlayer[i + 19] = ImageIO.read(Images.class.getResourceAsStream("/dog/Hurt_Left (" + i + ").png"));
                dogPlayer[i + 41] = ImageIO.read(Images.class.getResourceAsStream("/dog/Walk (" + i + ").png"));
                dogPlayer[i + 49] = ImageIO.read(Images.class.getResourceAsStream("/dog/Walk_Left (" + i + ").png"));
            }

            // set of animation images with length 9
            for(int i = 1; i <= 9; i++){
                // cat walk animation right/left
                catBoss[i+25] = ImageIO.read(Images.class.getResourceAsStream("/cat/Walk (" + i + ").png"));
                catBoss[i+34] = ImageIO.read(Images.class.getResourceAsStream("/cat/Walk_Left (" + i + ").png"));
            }


        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }



}
