package com.bleak.graphics.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.bleak.graphics.objects.Bullet;
import com.bleak.graphics.test.Handler;
import com.bleak.graphics.test.SFX;

public class KeyInput extends KeyAdapter {
    Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Player) {
                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(1);
                    tempObject.setVelY(0);
                    //tempObject.angle = tempObject.angle + 1;
                }
                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-1);
                    tempObject.setVelY(0);
                    //tempObject.angle = tempObject.angle - 1;
                }
                if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(1);
                    tempObject.setVelX(0);
                }
                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-1);
                    tempObject.setVelX(0);
                }
                if (key == KeyEvent.VK_SPACE) {
                    switch (tempObject.direction) {
                        case Up:
                            handler.addObject(new Bullet(tempObject.getX() + 30, tempObject.getY() + 30, 0, -4, handler, ObjectId.Bullet, ObjectDirection.Up));
                            break;
                        case Down:
                            handler.addObject(new Bullet(tempObject.getX() + 30, tempObject.getY() + 30, 0, 4, handler, ObjectId.Bullet, ObjectDirection.Down));
                            break;
                        case Left:
                            handler.addObject(new Bullet(tempObject.getX() + 30, tempObject.getY() + 30, -4, 0, handler, ObjectId.Bullet, ObjectDirection.Left));
                            break;
                        case Right:
                            handler.addObject(new Bullet(tempObject.getX() + 30, tempObject.getY() + 30, 4, 0, handler, ObjectId.Bullet, ObjectDirection.Right));
                            break;
                        default:
                            break;
                    }
                    //handler.addObject(new Bullet(tempObject.getX()+32-4, tempObject.getY(), 0, -5, handler, ObjectId.Bullet));
                    //sndSystem.PlaySound(sndSystem.sndShot);
                    SFX.SHOOT.play();
                }
                if (tempObject.angle > 360) tempObject.angle = 0;
                if (tempObject.angle < 0) tempObject.angle = 360;
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
                if (key == KeyEvent.VK_D) tempObject.setVelX(0);
                if (key == KeyEvent.VK_A) tempObject.setVelX(0);
                if (key == KeyEvent.VK_S) tempObject.setVelY(0);
                if (key == KeyEvent.VK_W) tempObject.setVelY(0);

            }
        }
    }
}
