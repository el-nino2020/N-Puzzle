package org.example.npuzzle.strategy.impl;

import org.example.npuzzle.entity.AStarState;
import org.example.npuzzle.entity.State;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.ToIntFunction;


public class HammingDistanceAStarTest {
    private final ToIntFunction<AStarState> hammingDistance = new HammingDistanceAStar()::calculateHeuristic;

    @Test
    public void calculateHeuristic() {
        int[][] grid = {
                {8, 4, 5},
                {3, 0, 1},
                {7, 2, 6}};
        AStarState state = new AStarState(new State(grid, null, null, null));

        int result = hammingDistance.applyAsInt(state);

        Assert.assertEquals(7, result);
    }
}