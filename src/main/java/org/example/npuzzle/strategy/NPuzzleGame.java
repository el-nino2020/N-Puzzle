package org.example.npuzzle.strategy;


import lombok.AllArgsConstructor;
import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.entity.State;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.npuzzle.util.RandomUtils.currentTimeRandom;

/**
 * 使用模板方法模式(Template Method Pattern)，抽象具体的算法
 *
 * @see org.example.npuzzle.strategy.impl
 */
public abstract class NPuzzleGame implements GlobalConstants {


    public final State runGame() {
        State initialState = initRandomGame();

        return solveGame(initialState);
    }

    protected abstract State solveGame(State initialState);

    private State initRandomGame() {
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

        return new State(grid, PUZZLE_LENGTH, PUZZLE_LENGTH,
                null, null, point, 0);
    }

}
