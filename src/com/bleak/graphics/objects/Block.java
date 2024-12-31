package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;

public class Block extends GameObject {
    public static int spriteWidth = 16;
    public static int spriteHeight = 16;
    public static int width = 32;
    public static int height = 32;
    public final int type;
    Texture tex = Game.getInstance();
    private final Animation waterAnimation = new Animation(
        30,
        tex.water[0],
        tex.water[1],
        tex.water[2]
    );

    public Block(float x, float y, int type, ObjectId id) {
        super(x, y, id, type);

        this.type = type;
    }

    public void tick(LinkedList<GameObject> object) {
        if (type == BlockType.Water.getId()) {
            waterAnimation.runAnimation();
        }
    }

    public void render(Graphics g) {
        if (this.type != BlockType.Water.getId()) {
            g.drawImage(tex.block[this.type], (int)x, (int)y, width, height, null);
        } else {
            waterAnimation.drawAnimation(g, (int)x, (int)y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}
