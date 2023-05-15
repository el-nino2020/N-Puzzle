package org.example.npuzzle;

import org.example.npuzzle.entity.State;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.strategy.impl.BidirectionalBFS;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class NPuzzleApplicationTest {

    /**
     * test a single algorithm
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        NPuzzle algorithm = new BidirectionalBFS();
        boolean customGame = false;

        if (customGame) {
            int[][] grid = new int[][]{
                    {1, 7, 0},
                    {8, 4, 5},
                    {2, 3, 6},
            };
            Point point = new Point(0, 2);

            Class<NPuzzle> cls = NPuzzle.class;
            Field initialState = cls.getDeclaredField("initialState");
            initialState.setAccessible(true);
            initialState.set(null, new State(grid, null, null, point));
        } else {
            NPuzzle.initRandomGame();
        }

        State state = algorithm.runGame();

        System.out.println("Total State checked: " + NPuzzle.stateCount());
        if (state == null) {
            System.out.println("no solution");
        } else {
            List<String> path = state.tracePath();
            System.out.println("Solution Size: " + path.size());
            System.out.println(path);
        }
    }
}