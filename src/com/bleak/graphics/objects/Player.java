package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
/*import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;*/



import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectDirection;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;

public class Player extends GameObject {
    private float width = 64;
    private float height = 64;
    /*private float gravity = 0.05f;
    private final float MAX_SPEED = 10;*/
    private Handler handler;
    private Animation playerMoveUp;
    private Animation playerMoveDown;
    private Animation playerMoveLeft;
    private Animation playerMoveRight;
    Texture tex = Game.getInstance();

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);

        this.handler = handler;
        this.direction = ObjectDirection.Up;

        playerMoveUp = new Animation(5, tex.player[1], tex.player[2]);
        playerMoveDown = new Animation(5, tex.player[5], tex.player[6]);
        playerMoveLeft = new Animation(5, tex.player[3], tex.player[4]);
        playerMoveRight = new Animation(5, tex.player[7], tex.player[8]);
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        Collision(object);

        playerMoveUp.runAnimation();
        playerMoveDown.runAnimation();
        playerMoveLeft.runAnimation();
        playerMoveRight.runAnimation();
    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block) {
                //top collision
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + 32;
                }

                //bottom collision
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                }

                //right collision
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                }

                //left collision
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + 32;
                }
            }

            /*if(tempObject.getId() == ObjectId.Coin){
                if(getBoundsBody().intersects(tempObject.getBounds())){
                    handler.removeObject(tempObject);
                }
            }*/
        }
    }

    public void render(Graphics g) {
        if (velX != 0) {
            if (velX > 0) {
                playerMoveRight.drawAnimation(g, (int) x, (int) y, 64, 64);
                this.direction = ObjectDirection.Right;
            } else {
                playerMoveLeft.drawAnimation(g, (int) x, (int) y, 64, 64);
                this.direction = ObjectDirection.Left;
            }
        } else if (velY != 0) {
            if (velY > 0) {
                playerMoveDown.drawAnimation(g, (int) x, (int) y, 64, 64);
                this.direction = ObjectDirection.Down;
            } else {
                playerMoveUp.drawAnimation(g, (int) x, (int) y, 64, 64);
                this.direction = ObjectDirection.Up;
            }
        } else {
            switch (this.direction) {
                case Up:
                    g.drawImage(tex.player[1], (int) x, (int) y, 64, 64, null);
                    break;
                case Down:
                    g.drawImage(tex.player[5], (int) x, (int) y, 64, 64, null);
                    break;
                case Left:
                    g.drawImage(tex.player[3], (int) x, (int) y, 64, 64, null);
                    break;
                case Right:
                    g.drawImage(tex.player[7], (int) x, (int) y, 64, 64, null);
                    break;
                default:
                    break;
            }

            //g.drawImage(tex.player[0], (int) x, (int) y, 64, 64, null);
        }


        /*
                // IMAGE ROTATION
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        g2d.rotate(this.angle, this.getX() + width / 2, this.getY()
                + height / 2);

        g.drawImage(tex.player[0], (int) x, (int) y, 64, 64, null);

        g2d.setTransform(old);*\

        /*g.setColor(Color.blue);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());*/
    }

    public Rectangle getBounds() {
        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) ((int) y + (height / 2)), (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) y, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsBody() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
}
