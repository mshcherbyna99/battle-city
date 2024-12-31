package com.bleak.graphics.objects;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectDirection;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;
import com.bleak.graphics.test.SFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class Tank extends GameObject {
    public static int spriteWidth = 16;
    public static int spriteHeight = 16;
    public static int width = 32;
    public static int height = 32;
    protected Texture instance;
    private final BufferedImage[] texture;
    protected Animation moveUp;
    protected Animation moveDown;
    protected Animation moveLeft;
    protected Animation moveRight;
    private final Handler handler;

    private long lastShotTime = 0;
    private static final long SHOOT_INTERVAL = 2000;

    public Tank(float x, float y, Handler handler, ObjectId id, BufferedImage[] texture) {
        super(x, y, id);

        this.instance = Game.getInstance();
        this.texture = texture;

        this.moveUp = new Animation(5, texture[1], texture[2]);
        this.moveDown = new Animation(5, texture[5], texture[6]);
        this.moveLeft = new Animation(5, texture[3], texture[4]);
        this.moveRight = new Animation(5, texture[7], texture[8]);

        this.handler = handler;
        this.direction = ObjectDirection.Up;
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        Collision(object);

        moveUp.runAnimation();
        moveDown.runAnimation();
        moveLeft.runAnimation();
        moveRight.runAnimation();
    }

    private void Collision(LinkedList<GameObject> object) {
        for (GameObject tempObject : handler.object) {
            if ((tempObject instanceof Block) || (tempObject instanceof Enemy)) {
                Rectangle blockBounds = tempObject.getBounds();

                if (getBounds().intersects(blockBounds)) {
                    if (velX != 0) {
                        if (velX > 0) {
                            x = x - 1;
                        } else if (velX < 0) {
                            x = x + 1;
                        }
                        velX = 0;
                    }

                    if (velY != 0) {
                        if (velY > 0) {
                            y = y - 1;
                        } else if (velY < 0) {
                            y = y + 1;
                        }
                        velY = 0;
                    }
                }
            }

            if (tempObject.getId() == ObjectId.Coin) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                }
            }
        }
    }


    @Override
    public void render(Graphics g) {
        if (velX != 0) {
            if (velX > 0) {
                moveRight.drawAnimation(g, (int) x, (int) y, width, height);
                this.direction = ObjectDirection.Right;
            } else {
                moveLeft.drawAnimation(g, (int) x, (int) y, width, height);
                this.direction = ObjectDirection.Left;
            }
        } else if (velY != 0) {
            if (velY > 0) {
                moveDown.drawAnimation(g, (int) x, (int) y, width, height);
                this.direction = ObjectDirection.Down;
            } else {
                moveUp.drawAnimation(g, (int) x, (int) y, width, height);
                this.direction = ObjectDirection.Up;
            }
        } else {
            switch (this.direction) {
                case Up:
                    g.drawImage(texture[1], (int) x, (int) y, width, height, null);
                    break;
                case Down:
                    g.drawImage(texture[5], (int) x, (int) y, width, height, null);
                    break;
                case Left:
                    g.drawImage(texture[3], (int) x, (int) y, width, height, null);
                    break;
                case Right:
                    g.drawImage(texture[7], (int) x, (int) y, width, height, null);
                    break;
                default:
                    g.drawImage(texture[0], (int) x, (int) y, width, height, null);
                    break;
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public void shoot() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShotTime >= SHOOT_INTERVAL) {
            lastShotTime = currentTime;
            float bulletX = x + (width / 2.0f);
            float bulletY = y + (height / 2.0f);
            float bulletVelX = 0, bulletVelY = 0;

            switch (direction) {
                case Up:
                    bulletVelY = -4;
                    bulletY = y - (height / 2.0f);
                    break;
                case Down:
                    bulletVelY = 4;
                    bulletY = y + height + (height / 2.0f);
                    break;
                case Left:
                    bulletVelX = -4;
                    bulletX = x - (width / 2.0f);
                    break;
                case Right:
                    bulletVelX = 4;
                    bulletX = x + width + (width / 2.0f);
                    break;
                default:
                    break;
            }

            handler.addObject(new Bullet(bulletX, bulletY, bulletVelX, bulletVelY, handler, ObjectId.Bullet, direction));
            SFX.SHOOT.play();
        }
    }
}
