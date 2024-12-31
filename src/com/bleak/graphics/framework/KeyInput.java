package com.bleak.graphics.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.bleak.graphics.objects.Bullet;
import com.bleak.graphics.objects.Tank;
import com.bleak.graphics.test.Handler;
import com.bleak.graphics.test.SFX;

public class KeyInput extends KeyAdapter {
    Handler handler;
    private final Set<Integer> pressedKeys = new HashSet<>();

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys.add(key);

        for (GameObject tempObject : new ArrayList<>(handler.object)) {
            if (tempObject.getId() == ObjectId.Player && tempObject instanceof Tank player) {
                if (key == KeyEvent.VK_D) {
                    player.setVelX(1);
                    player.setVelY(0);
                    player.direction = ObjectDirection.Right;
                } else if (key == KeyEvent.VK_A) {
                    player.setVelX(-1);
                    player.setVelY(0);
                    player.direction = ObjectDirection.Left;
                } else if (key == KeyEvent.VK_S) {
                    player.setVelY(1);
                    player.setVelX(0);
                    player.direction = ObjectDirection.Down;
                } else if (key == KeyEvent.VK_W) {
                    player.setVelY(-1);
                    player.setVelX(0);
                    player.direction = ObjectDirection.Up;
                }

                if (key == KeyEvent.VK_SPACE) {
                    player.shoot();
                }
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                if (!pressedKeys.contains(KeyEvent.VK_D) && !pressedKeys.contains(KeyEvent.VK_A)) {
                    tempObject.setVelX(0);
                }
                if (!pressedKeys.contains(KeyEvent.VK_W) && !pressedKeys.contains(KeyEvent.VK_S)) {
                    tempObject.setVelY(0);
                }
            }
        }
        pressedKeys.remove(key);
    }
}
