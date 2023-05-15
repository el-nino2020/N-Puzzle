package org.example.npuzzle.strategy.impl;

import org.example.npuzzle.entity.AStarState;
import org.example.npuzzle.entity.State;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.function.ToIntFunction;

public class ManhattanDistanceAStarTest {

    private final ToIntFunction<AStarState> manhattanDistance = new ManhattanDistanceAStar()::calculateHeuristic;

    @Test
    public void calculateHeuristic() {
        int[][] grid = {
                {1, 2, 5},
                {3, 0, 6},
                {7, 4, 8}};
        AStarState state = new AStarState(new State(grid, null, null, null));

        int result = manhattanDistance.applyAsInt(state);

        Assert.assertEquals(8, result);
    }
}