package com.bleak.graphics.objects;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

public class Eagle extends GameObject {
    public static int spriteWidth = 16;
    public static int spriteHeight = 16;
    public static int width = 32;
    public static int height = 32;

    public Eagle(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        // Do nothing
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}
