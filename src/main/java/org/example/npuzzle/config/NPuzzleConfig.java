package org.example.npuzzle.config;


import org.example.npuzzle.entity.State;

public class NPuzzleConfig {
    /**
     * 是否随机初始化N-puzzle实例
     */
    public static boolean RANDOM_GAME = true;

    /**
     * 程序一次运行多少次N-puzzle实例，该值的有效性取决于 randGame设置为true
     *
     * @see NPuzzleConfig#RANDOM_GAME
     */
    public static int GAME_ROUND = 1;


    /**
     * PUZZLE 有几行，该值的有效性取决于 randGame设置为true
     *
     * @see NPuzzleConfig#RANDOM_GAME
     */
    public static int PUZZLE_ROW_COUNT = 3;

    /**
     * PUZZLE 有几列，该值的有效性取决于 randGame设置为true
     *
     * @see NPuzzleConfig#RANDOM_GAME
     */
    public static int PUZZLE_COLUMN_COUNT = 3;

    /**
     * 是否连续运行所有 N-puzzle 实例，实例与实例之间不暂停
     */
    public static boolean AUTO_RUN_ALL_INSTANCES = true;

    /**
     * 是否打印每个算法得出的 solution path
     * @see State#tracePath()
     */
    public static boolean PRINT_SOLUTION_PATH = false;

    /**
     * PUZZLE 包含的总<b>有效</b>格子数，该值的有效性取决于 randGame设置为true
     *
     * @see NPuzzleConfig#RANDOM_GAME
     * @see NPuzzleConfig#PUZZLE_ROW_COUNT
     * @see NPuzzleConfig#PUZZLE_COLUMN_COUNT
     */
    public static int PUZZLE_SIZE = PUZZLE_ROW_COUNT * PUZZLE_COLUMN_COUNT - 1;


}
