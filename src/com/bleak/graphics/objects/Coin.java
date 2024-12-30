package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;

public class Coin extends GameObject {
    Texture tex = Game.getInstance();
    private Animation coinRotating = new Animation(20, tex.coin[0], tex.coin[1], tex.coin[2], tex.coin[3], tex.coin[4], tex.coin[5], tex.coin[6], tex.coin[7], tex.coin[8]);

    public Coin(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {
        coinRotating.runAnimation();
    }

    public void render(Graphics g) {
        coinRotating.drawAnimation(g, (int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }
}
