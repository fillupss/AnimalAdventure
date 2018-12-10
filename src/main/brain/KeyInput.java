package main.brain;

import main.objects.GameObject;
import main.objects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    // going to be used to access objects that are player
    private Controller hand;

    // left, right, jump, Shoot
    boolean[] keyPressedP1 = new boolean[4];

    public KeyInput(Controller handler){
        this.hand = handler;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE){
            System.exit(1);
        }

        for(int i = 0; i < this.hand.handler.size(); i++){
            GameObject temp = hand.handler.get(i);
            if(temp.getId().equals(ObjectID.Player)){
                Player temp1 = (Player) temp;
                if(keyCode == KeyEvent.VK_A){
                    temp1.setVelX(-5);
                    keyPressedP1[0] = true;
                }
                if(keyCode == KeyEvent.VK_D){
                    temp1.setVelX(5);
                    keyPressedP1[1] = true;
                }
                if(keyCode == KeyEvent.VK_W && !temp1.getJumping()){
                    temp1.setVelY(-12);
                    temp1.setJumping(true);
                    keyPressedP1[2] = true;
                }
                if(keyCode == KeyEvent.VK_SPACE && !keyPressedP1[0] && !keyPressedP1[1]){
                    if(!keyPressedP1[3]){
                        temp1.shoot();
                        keyPressedP1[3] = true;
                    }
                }
            }
        }

    }

    public void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();
        for(int i = 0; i < this.hand.handler.size(); i++){
            GameObject temp = hand.handler.get(i);
            if(temp.getId().equals(ObjectID.Player)){
                Player temp1 = (Player) temp;
                if(keyCode == KeyEvent.VK_A){
                    keyPressedP1[0] = false;
                }
                if(keyCode == KeyEvent.VK_D){
                    keyPressedP1[1] = false;
                }
                if(keyCode == KeyEvent.VK_W){
                    keyPressedP1[2] = false;
                }
                if(keyCode == KeyEvent.VK_SPACE){
                    keyPressedP1[3] = false;
                }
                if(!keyPressedP1[0] && !keyPressedP1[1]){
                    temp1.setVelX(0);
                }
            }
        }
    }
}

