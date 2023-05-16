package org.example.npuzzle.strategy;


import org.example.npuzzle.config.NPuzzleConfig;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.util.ArrayUtils;
import org.example.npuzzle.util.NPuzzleUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.npuzzle.util.RandomUtils.currentTimeRandom;

/**
 * 使用模板方法模式(Template Method Pattern)，抽象具体的算法
 *
 * @see org.example.npuzzle.strategy.impl
 */
public abstract class NPuzzle {

    private static volatile State initialState;

    /**
     * 记录执行某算法时先前访问过的状态
     */
    private static final ThreadLocal<Set<String>> visitedState = new ThreadLocal<>();

    /**
     * 标记当前线程执行某算法时，state已经被创建过了
     */
    protected final void setStateVisited(State state) {
        visitedState.get().add(ArrayUtils.toString(state.getGrid()));
    }

    /**
     * 当前线程在执行该算法期间，state是否已经被创建过了
     *
     * @return boolean
     */
    protected final boolean isVisited(State state) {
        return visitedState.get().contains(ArrayUtils.toString(state.getGrid()));
    }

    /**
     * 返回当前线程在执行某个算法时创建的不同 state 的个数
     *
     * @return 当前线程在执行某个算法时创建的不同 state 的个数
     */
    public static int stateCount() {
        return visitedState.get().size();
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
     * 随机初始化本轮 N-Puzzle，所有算法将基于这一次初始化的结果
     */
    public synchronized static void initRandomGame() {
        State.forkedTimes.set(0L);
        visitedState.set(new HashSet<>());

        int m = NPuzzleConfig.PUZZLE_ROW_COUNT, n = NPuzzleConfig.PUZZLE_COLUMN_COUNT;

        int[][] grid = new int[m][n];

        Point point = new Point(currentTimeRandom().nextInt(m),
                currentTimeRandom().nextInt(n));

        List<Integer> list = IntStream.rangeClosed(1, NPuzzleConfig.PUZZLE_SIZE).boxed().collect(Collectors.toList());
        Collections.shuffle(list, currentTimeRandom());

        for (int i = 0, k = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == point.x && j == point.y)
                    continue;
                grid[i][j] = list.get(k);
                k++;
            }
        }

        System.out.println("Initial State:");
        System.out.print(ArrayUtils.toString(grid));
        System.out.println("Solvable? " + NPuzzleUtils.checkSolvability(grid));
        initialState = new State(grid, null, null, point);
    }

    /**
     * 从文件中读取，尝试解析一个有效的N-puzzle实例
     *
     * @param reader reader of file of problems
     * @return 如果初始化成功则返回true；如果reader读完了都无法找到一个有效的N-puzzle实例，则返回false
     */
    public synchronized static boolean initCustomGame(BufferedReader reader) throws IOException {
        if (reader == null) return false;

        State.forkedTimes.set(0L);
        visitedState.set(new HashSet<>());


        String line;
        List<int[]> list = new ArrayList<>();

        while (true) {
            while (((line = reader.readLine()) != null) && (line.startsWith("---"))) {
            }
            if (line == null)
                return false;
            try {
                do {
                    list.add(ArrayUtils.toIntArray(line, "\\s"));
                } while (((line = reader.readLine()) != null) && !(line.startsWith("---")));
            } catch (Exception e) {
                // 文件格式错误，拒绝服务
                return false;
            }

            int[][] grid = list.toArray(new int[list.size()][]);

            if (NPuzzleUtils.checkValidness(grid)) {
                int[] pos = ArrayUtils.find(grid, 0);
                System.out.println("Initial State:");
                System.out.print(ArrayUtils.toString(grid));
                System.out.println("Solvable? " + NPuzzleUtils.checkSolvability(grid));
                initialState = new State(grid, null, null, new Point(pos[0], pos[1]));
                return true;
            }

        }
    }

}
