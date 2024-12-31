package com.bleak.graphics.objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bleak.graphics.framework.*;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;

public class Enemy extends Tank {
    private static final Texture instance = Game.getInstance();
    private static final BufferedImage[] texture = instance.enemy;
    protected float velX = 5;
    protected float velY = 5;

    public Enemy(float x, float y, Handler handler, ObjectId id) {
        super(x, y, handler, id, texture);
        this.direction = ObjectDirection.Down;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        super.tick(object);

        GameObject target = findClosestTarget(object);
        if (target != null) {
            // 1. Check if we have clear line-of-sight:
            if (hasClearView(target, object)) {
                moveToTarget(target);
                shoot();
            } else {
                // 2. Build collision grid from blocks
                Handler handler = new Handler();
                boolean[][] collisionGrid = buildCollisionGrid(handler);

                // 3. Convert Enemy’s (x, y) to row, col
                int startCol = (int)(this.x / Game.TILE_SIZE);
                int startRow = (int)(this.y / Game.TILE_SIZE);

                // 4. Convert Target’s (x, y) to row, col
                int targetCol = (int)(target.getX() / Game.TILE_SIZE);
                int targetRow = (int)(target.getY() / Game.TILE_SIZE);

                // 5. Get path from A*
                List<Node> path = AStarNode.aStarPath(startRow, startCol, targetRow, targetCol, collisionGrid);

                // 6. Follow the path
                if (!path.isEmpty()) {
                    followPath(path);
                } else {
                    // if no path found, maybe default to your old logic or idle
                }
            }
        }
    }

    private boolean[][] buildCollisionGrid(Handler handler) {
        // Dimensions: for example, MAP_ROWS x MAP_COLS
        boolean[][] grid = new boolean[Game.MAP_ROWS][Game.MAP_COLS];

        // Go through every game object in the Handler
        for (GameObject obj : handler.object) {
            // If it's a collidable object that should block movement (like a Block)
            if (obj instanceof Block) {
                int col = (int) (obj.getX() / Game.TILE_SIZE);
                int row = (int) (obj.getY() / Game.TILE_SIZE);

                // Mark the cell as blocked, unless it's a special type you consider passable.
                // For example, if type == 1 is a bush that doesn't block movement:
                if (obj.type != 1) {
                    grid[row][col] = true;  // 'true' = blocked
                }
            }
            // You can also mark other collidable objects here if you want them to block pathfinding.
        }

        return grid;
    }

    /**
     * Moves the Enemy one step along the given path.
     * You can adapt this to move continuously or tile by tile.
     */
    private void followPath(List<Node> path) {
        // The first Node in 'path' is your current tile, so the second is the next tile
        if (path.size() > 1) {
            Node nextNode = path.get(1);  // 0 is current, 1 is next
            float nextX = nextNode.col * Game.TILE_SIZE;
            float nextY = nextNode.row * Game.TILE_SIZE;

            float deltaX = nextX - this.x;
            float deltaY = nextY - this.y;

            // Move in x or y direction, or both if you allow diagonal
            if (Math.abs(deltaX) > 2) {
                setVelX(deltaX > 0 ? 1.0f : -1.0f);
            } else {
                setVelX(1.0f);
            }
            if (Math.abs(deltaY) > 2) {
                setVelY(deltaY > 0 ? 1.0f : -1.0f);
            } else {
                setVelY(1.0f);
            }

            // Update direction based on velocities
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                this.direction = (deltaX >= 0) ? ObjectDirection.Right : ObjectDirection.Left;
            } else {
                this.direction = (deltaY >= 0) ? ObjectDirection.Down : ObjectDirection.Up;
            }
        } else {
            // If path has only 1 node, it means we're basically on the goal
            setVelX(1.0f);
            setVelY(1.0f);
        }
    }

    private GameObject findClosestTarget(LinkedList<GameObject> objects) {
        GameObject closest = null;
        double minDistance = Double.MAX_VALUE;

        for (GameObject obj : objects) {
            if (obj instanceof Player || obj instanceof Eagle) {
                double distance = Math.hypot(obj.getX() - x, obj.getY() - y);

                if (distance < minDistance) {
                    closest = obj;
                    minDistance = distance;
                }
            }
        }

        return closest;
    }

    private boolean hasClearView(GameObject target, LinkedList<GameObject> objects) {
        float startX = x + width / 2.0f;
        float startY = y + height / 2.0f;
        float targetX = target.getX() + target.getBounds().width / 2.0f;
        float targetY = target.getY() + target.getBounds().height / 2.0f;

        float dx = targetX - startX;
        float dy = targetY - startY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Avoid dividing by zero
        if (distance == 0) {
            return true;
        }

        float stepX = dx / distance;
        float stepY = dy / distance;

        // Walk along the line in small increments
        for (float i = 0; i < distance; i += 5) {
            float checkX = startX + stepX * i;
            float checkY = startY + stepY * i;

            // Check collisions with each object
            Handler handler = new Handler();
            for (GameObject obj : new ArrayList<>(handler.object)) {
                // Skip self and the intended targets (Player/Eagle)
                if (obj == this || obj instanceof Player || obj instanceof Eagle) {
                    continue;
                }

                // If any other object is in the path
                if (obj.getBounds().contains(checkX, checkY)) {
                    // If it’s any other block or obstacle => line of sight is blocked
                    return false;
                }
            }
        }

        // If we never encountered a blocking object
        return true;
    }

    private void moveToTarget(GameObject target) {
        float deltaX = target.getX() - x;
        float deltaY = target.getY() - y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            direction = (deltaX >= 0) ? ObjectDirection.Right : ObjectDirection.Left;
            this.velX = ((deltaX > 0) ? 1 : -1);
            this.velY = (1);
        } else {
            direction = (deltaY >= 0) ? ObjectDirection.Down : ObjectDirection.Up;
            this.velY = ((deltaY > 0) ? 1 : -1);
            this.velX = (1);
        }
    }
}
