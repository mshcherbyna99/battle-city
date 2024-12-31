package com.bleak.graphics.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStarNode extends Node implements Comparable<AStarNode> {
    public double g; // cost from start
    public double h; // heuristic to goal
    public double f; // g + h

    public AStarNode(int row, int col, AStarNode parent, double g, double h) {
        super(row, col);
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    @Override
    public int compareTo(AStarNode other) {
        return Double.compare(this.f, other.f);
    }

    public static List<Node> aStarPath(
        int startRow,
        int startCol,
        int goalRow,
        int goalCol,
        boolean[][] collisionGrid
    ) {
        // PriorityQueue => always pick the node with lowest f first
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>();
        boolean[][] visited = new boolean[collisionGrid.length][collisionGrid[0].length];

        // Create the start node with g=0, h = heuristic to goal
        AStarNode startNode = new AStarNode(startRow, startCol, null, 0,
            manhattanDistance(startRow, startCol, goalRow, goalCol));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            AStarNode current = openSet.poll();

            // If we've already visited, skip
            if (visited[current.row][current.col]) {
                continue;
            }

            visited[current.row][current.col] = true;

            // Check if we reached the goal
            if (current.row == goalRow && current.col == goalCol) {
                // Reconstruct the path from 'current'
                return reconstructPath(current);
            }

            // Get neighbors
            for (AStarNode neighbor : getNeighbors(current, collisionGrid, goalRow, goalCol)) {
                if (!visited[neighbor.row][neighbor.col]) {
                    openSet.add(neighbor);
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }

    private static List<Node> reconstructPath(AStarNode endNode) {
        List<Node> path = new ArrayList<>();
        AStarNode current = endNode;

        while (current != null) {
            // We'll keep them as Node or AStarNode
            path.add(new Node(current.row, current.col));
            current = (AStarNode) current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    /** Manhattan distance for grid-based pathfinding (4 directions). */
    private static double manhattanDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    private static final int[][] DIRS = {
        { 0,  1}, // Right
        { 0, -1}, // Left
        { 1,  0}, // Down
        {-1,  0}  // Up
    };

    private static List<AStarNode> getNeighbors(AStarNode current, boolean[][] grid, int goalRow, int goalCol) {
        List<AStarNode> neighbors = new ArrayList<>();

        for (int[] d : DIRS) {
            int nr = current.row + d[0];
            int nc = current.col + d[1];

            // Check boundaries
            if (nr < 0 || nr >= grid.length || nc < 0 || nc >= grid[0].length) {
                continue;
            }
            // Check collisions
            if (grid[nr][nc]) {
                // If true means blocked, skip
                continue;
            }

            double g = current.g + 1;
            double h = manhattanDistance(nr, nc, goalRow, goalCol);
            neighbors.add(new AStarNode(nr, nc, current, g, h));
        }

        return neighbors;
    }

}

