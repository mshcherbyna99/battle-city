package com.bleak.graphics.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class GameObject {
    public static int width;
    public static int height;
    protected float x;
    protected float y;
    protected float velX = 0;
    protected float velY = 0;
    protected ObjectId id;
    protected ObjectDirection direction = ObjectDirection.Undefined;
    protected boolean falling = true;
    protected boolean jumping = false;
    public int type;
    public int damage;
    public int health;

    public GameObject(float x, float y, ObjectId id, int type) {
        this(x, y, id);
        this.type = type;
    }

    public GameObject(float x, float y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object);

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public ObjectId getId() {
        return id;
    }

    public void setDirection(ObjectDirection dir) {
        this.direction = dir;
    }

    public ObjectDirection getDirection() {
        return direction;
    }
}
