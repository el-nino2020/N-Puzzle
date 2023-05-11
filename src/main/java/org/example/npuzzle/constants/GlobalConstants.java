package org.example.npuzzle.constants;


public interface GlobalConstants {
    /**
     * PUZZLE 的 边长
     */
    int PUZZLE_LENGTH = 3;

    /**
     * PUZZLE 包含的总<b>有效</b>格子数
     * @see GlobalConstants#PUZZLE_LENGTH
     */
    int PUZZLE_SIZE = PUZZLE_LENGTH * PUZZLE_LENGTH - 1;
}
