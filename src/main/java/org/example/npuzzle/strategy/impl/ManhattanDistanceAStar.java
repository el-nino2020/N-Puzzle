package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.AStarState;
import org.example.npuzzle.entity.State;


/**
 * Manhattan Distance, A.K.A, Taxicab geometry
 */
public class ManhattanDistanceAStar extends AStar {
    @Override
    public int calculateHeuristic(AStarState state) {
        // Manhattan Distance of a tile is the distance or the
        // number of slides/tiles away it is from its goal state.
        // Thus, for a certain state the Manhattan distance will be
        // the sum of the Manhattan distances of all the tiles
        // except the blank tile.
        int ans = 0;

        int[][] grid = state.getGrid();
        int m = State.M;
        int n = State.N;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                if (val == 0) continue;
                ans += Math.abs(i - ((val - 1) / n)) + Math.abs(j - ((val - 1) % n));
            }
        }

        return ans;
    }
}
