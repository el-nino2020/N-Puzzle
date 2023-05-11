package org.example.npuzzle;


import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.entity.State;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.npuzzle.util.RandomUtils.*;

public class NPuzzleApplication implements GlobalConstants {

    public State newGame() {
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

        State ans = new State(grid, null, point);


        return ans;
    }

    public static void main(String[] args) {
        NPuzzleApplication application = new NPuzzleApplication();
        State state = application.newGame();
        System.out.println(state.gridToString());
        System.out.println(state.getPoint());
    }

}
