package org.example.npuzzle.strategy.impl;

import org.example.npuzzle.entity.AStarState;
import org.example.npuzzle.entity.State;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.ToIntFunction;


public class LinearConflictWithManhattanAStarTest {

    private final ToIntFunction<AStarState> linearConflict = new LinearConflictWithManhattanAStar()::calculateHeuristic;

    @Test
    public void calculateHeuristic() {


        int[][] grid = {
                {4, 2, 5},
                {1, 0, 6},
                {3, 8, 7}};
        AStarState state = new AStarState(new State(grid, null, null, null));

        int result = linearConflict.applyAsInt(state);

        Assert.assertEquals(14, result);

    }
}