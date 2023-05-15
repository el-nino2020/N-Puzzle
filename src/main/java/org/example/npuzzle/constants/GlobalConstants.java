package org.example.npuzzle.constants;


public interface GlobalConstants {
    /**
     * PUZZLE 有几行
     */
    int PUZZLE_ROW_COUNT = 3;

    /**
     * PUZZLE 有几列
     */
    int PUZZLE_COLUMN_COUNT = 3;

    /**
     * PUZZLE 包含的总<b>有效</b>格子数
     *
     * @see GlobalConstants#PUZZLE_ROW_COUNT
     * @see GlobalConstants#PUZZLE_COLUMN_COUNT
     */
    int PUZZLE_SIZE = PUZZLE_ROW_COUNT * PUZZLE_COLUMN_COUNT - 1;
}
