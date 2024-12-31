package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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

public class Player extends Tank {
    private static final Texture instance = Game.getInstance();
    private static final BufferedImage[] texture = instance.player;

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, handler, id, texture);
    }
}
