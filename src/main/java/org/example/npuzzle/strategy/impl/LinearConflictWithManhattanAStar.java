package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.AStarState;
import org.example.npuzzle.entity.State;

import java.util.function.ToIntFunction;


/**
 * Linear Conflict + Manhattan Distance
 *
 * @see ManhattanDistanceAStar
 */
public class LinearConflictWithManhattanAStar extends AStar {

    private static final ToIntFunction<AStarState> manhattanDistance = new ManhattanDistanceAStar()::calculateHeuristic;

    @Override
    public int calculateHeuristic(AStarState state) {
        // Two tiles ‘a’ and ‘b’ are in a linear conflict if
        // they are in the same row or column,
        // also their goal positions are in the same row or column
        // and the goal position of one of the tiles is blocked by the other tile in that row.
        // we do not consider the blank tile when calculating linear conflicts.
        int conflictCount = 0;

        int[][] grid = state.getGrid();
        int m = State.M;
        int n = State.N;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                if (val == 0) continue;
                // 当前数字的位置 与 其最终的位置是否在同一行或者同一列
                boolean flag = (i == ((val - 1) / n)) || (j == ((val - 1) % n));
                if (!flag) continue;

                // 与同行右边的格子相比
                for (int k = j + 1; k < n; k++) {
                    int val2 = grid[i][k];
                    if (val2 == 0) continue;
                    // val2也应该满足：该数字的位置 与 其最终的位置在同一行或者同一列
                    flag = (i == ((val2 - 1) / n)) || (k == ((val2 - 1) % n));
                    // val 和 val2 应该在同一行或同一列
                    flag &= (((val2 - 1) / n) == ((val - 1) / n)) || (((val2 - 1) % n) == ((val - 1) % n));
                    if (!flag) continue;
                    // 一个格子右边的数字应该更大，如果不是，则形成 linear conflict
                    if (val > val2)
                        conflictCount++;
                }

                // 与同列下边的格子相比
                for (int k = i + 1; k < m; k++) {
                    int val2 = grid[k][j];
                    if (val2 == 0) continue;

                    // val2也应该满足：该数字的位置 与 其最终的位置在同一行或者同一列
                    flag = (k == ((val2 - 1) / n)) || (j == ((val2 - 1) % n));
                    // val 和 val2 应该在同一行或同一列
                    flag &= (((val2 - 1) / n) == ((val - 1) / n)) || (((val2 - 1) % n) == ((val - 1) % n));
                    if (!flag) continue;
                    // 一个格子下边的数字应该更大，如果不是，则形成 linear conflict
                    if (val > val2)
                        conflictCount++;

                }
            }
        }

        // h = Manhattan distance + 2 * number of linear conflicts
        return manhattanDistance.applyAsInt(state) + 2 * conflictCount;
    }
}
