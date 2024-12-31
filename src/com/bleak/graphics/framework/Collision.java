package com.bleak.graphics.framework;

import java.util.LinkedList;

public class Collision {
    public static boolean checkCollision(GameObject object, LinkedList<GameObject> objectList) {
        for (GameObject tempObject : objectList) {
            if (object.getBounds().intersects(tempObject.getBounds())) {
                return true;
            }
        }

        return false;
    }
}
