package com.bleak.graphics.test;

import java.awt.Graphics;
import java.util.LinkedList;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectId;

public class Handler {
    public LinkedList<GameObject> object = new LinkedList<>();
    private GameObject tempObject;

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public int countObject(ObjectId id) {
        int count = 0;

        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);

            if (tempObject.getId() == id) {
                count++;
            }
        }

        return count;
    }
}
