package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.AStarState;


/**
 * Hamming Distance, A.K.A, Misplaced Tiles
 */
public class HammingDistanceAStar extends AStar {
    @Override
    public int calculateHeuristic(AStarState state) {
        // returns the number of tiles that are not in their final position.
        // we don’t consider the blank space as a tile while calculating this heuristic value.
        int ans = 0;

        int[][] grid = state.getGrid();
        int m = state.getGrid().length;
        int n = state.getGrid()[0].length;

        for (int i = 0, k = 1; i < m; i++) {
            for (int j = 0; j < n; j++, k++) {
                if (grid[i][j] == 0) continue;
                if (grid[i][j] != k) {
                    ans++;
                }
            }
        }

        return ans;
    }
}
