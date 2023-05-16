package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.AStarState;


/**
 * Euclidean Distance
 */
public class EuclideanDistanceAStar extends AStar {
    @Override
    public int calculateHeuristic(AStarState state) {
        // Euclidean Distance of a tile is the distance from its goal state.
        // Thus, for a certain state the Euclidean distance will be
        // the sum of the Euclidean distances of all the tiles
        // except the blank tile.
        double ans = 0;

        int[][] grid = state.getGrid();
        int m = state.getGrid().length;
        int n = state.getGrid()[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                if (val == 0) continue;

                int x = (val - 1) / n, y = (val - 1) % n;
                ans += Math.sqrt((i - x) * (i - x) + (j - y) * (j - y));
            }
        }

        // 忽略小数，问题应该不大
        return (int) ans;
    }
}
