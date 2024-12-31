package com.bleak.graphics.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectDirection;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;
import com.bleak.graphics.test.SFX;

public class Bullet extends GameObject {

    private final int width = 16;
    private final int height = 16;
    protected ObjectDirection direction;
    private final Handler handler;
    Texture tex = Game.getInstance();
    private final long creationTime = System.currentTimeMillis();

    public Bullet(float x, float y, float velX, float velY, Handler handler, ObjectId id, ObjectDirection direction) {
        super(x, y, id);

        this.handler = handler;
        this.velX = velX;
        this.velY = velY;
        this.id = id;
        this.damage = 1;
        this.direction = direction;
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        if (System.currentTimeMillis() - creationTime > 5000) {
            handler.removeObject(this);
            return;
        }

        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {
        boolean isRemoved = false;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ObjectId.Block) {
                    SFX.EXPLODE.play();

                    System.out.println("Bullet collided with a block with type " + tempObject.type);

                    if (tempObject.type == BlockType.Brick.getId()) {
                        handler.removeObject(tempObject);
                    }

                    isRemoved = true;

                    handler.addObject(
                        new Explosion(
                            this.getX() - this.width / 2.0f,
                            this.getY() - this.height / 2.0f,
                            handler,
                            1
                        )
                    );
                }

                if (tempObject instanceof Tank && tempObject.getId() != ObjectId.Player) {
                    int healthRemains = tempObject.health - this.damage;

                    if (healthRemains > 0) {
                        tempObject.health = healthRemains;
                        SFX.RICHCHET.play();
                    } else {
                        SFX.EXPLODE.play();
                        handler.removeObject(tempObject);

                        handler.addObject(
                            new Explosion(
                                this.getX() - this.width / 2.0f,
                                this.getY() - this.height / 2.0f,
                                handler,
                                1
                            )
                        );
                    }

                    isRemoved = true;
                }
            }
        }

        if (isRemoved) {
            handler.removeObject(this);
        }
    }

    public void render(Graphics g) {
        switch (this.direction) {
            case Up:
                g.drawImage(tex.bullet[0], (int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height, null);
                break;
            case Down:
                g.drawImage(tex.bullet[2], (int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height, null);
                break;
            case Left:
                g.drawImage(tex.bullet[1], (int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height, null);
                break;
            case Right:
                g.drawImage(tex.bullet[3], (int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height, null);
                break;
            default:
                break;
        }
    }

    public void renderDebug(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
    }
}
