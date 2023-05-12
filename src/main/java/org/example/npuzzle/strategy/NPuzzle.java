package org.example.npuzzle.strategy;


import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.util.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.npuzzle.util.RandomUtils.currentTimeRandom;

/**
 * 使用模板方法模式(Template Method Pattern)，抽象具体的算法
 *
 * @see org.example.npuzzle.strategy.impl
 */
public abstract class NPuzzle implements GlobalConstants {

    private static volatile State initialState;

    /**
     * 记录先前访问过的状态
     */
    private static final ThreadLocal<Set<String>> visitedState = new ThreadLocal<>();

    protected final void setStateVisited(State state) {
        visitedState.get().add(ArrayUtils.toString(state.getGrid()));
    }

    protected final boolean isVisited(State state) {
        return visitedState.get().contains(ArrayUtils.toString(state.getGrid()));
    }

    public final State runGame() {
        State forkedState = initialState.fork();

        State.forkedTimes.set(0L);
        visitedState.set(new HashSet<>());

        return solveGame(forkedState);
    }

    /**
     * 求解 N-Puzzle
     *
     * @param initialState 初始状态
     * @return 如果有解，返回终止状态；否则返回null
     */
    @Nullable
    protected abstract State solveGame(State initialState);

    /**
     * 初始化本轮 N-Puzzle，所有算法将基于这一次初始化的结果
     */
    public synchronized static void initRandomGame() {
        State.forkedTimes.set(0L);
        visitedState.set(new HashSet<>());

        int[][] grid = new int[PUZZLE_LENGTH][PUZZLE_LENGTH];

        Point point = new Point(currentTimeRandom().nextInt(PUZZLE_LENGTH),
                currentTimeRandom().nextInt(PUZZLE_LENGTH));

        List<Integer> list = IntStream.rangeClosed(1, PUZZLE_SIZE).boxed().collect(Collectors.toList());
        Collections.shuffle(list, currentTimeRandom());

        for (int i = 0, k = 0; i < PUZZLE_LENGTH; i++) {
            for (int j = 0; j < PUZZLE_LENGTH; j++) {
                if (i == point.x && j == point.y)
                    continue;
                grid[i][j] = list.get(k);
                k++;
            }
        }

        System.out.println("Initial State:");
        System.out.print(ArrayUtils.toString(grid));

        initialState = new State(grid, PUZZLE_LENGTH, PUZZLE_LENGTH,
                null, null, point);
    }

}
