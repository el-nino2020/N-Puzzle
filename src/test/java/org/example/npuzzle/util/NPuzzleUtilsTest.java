package org.example.npuzzle.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.example.npuzzle.util.NPuzzleUtils.*;

public class NPuzzleUtilsTest {


    @Test
    public void checkSolvabilityTest() {
        int[][] puzzle;

        puzzle = new int[][]{
                {1, 8, 2},
                {0, 4, 3},
                {7, 6, 5}
        };
        assertTrue(checkSolvability(puzzle));

        puzzle = new int[][]{
                {13, 2, 10, 3},
                {1, 12, 8, 4},
                {5, 0, 9, 6},
                {15, 14, 11, 7}
        };
        assertTrue(checkSolvability(puzzle));


        puzzle = new int[][]{
                {6, 13, 7, 10},
                {8, 9, 11, 0},
                {15, 2, 12, 5},
                {14, 3, 1, 4}
        };
        assertTrue(checkSolvability(puzzle));


        puzzle = new int[][]{
                {3, 9, 1, 15},
                {14, 11, 4, 6},
                {13, 0, 10, 12},
                {2, 7, 8, 5},
        };
        assertFalse(checkSolvability(puzzle));

    }
}