package org.example.npuzzle;


import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.example.npuzzle.config.NPuzzleConfig;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.strategy.impl.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class NPuzzleApplication {
    private static final List<Class<? extends NPuzzle>> algorithms = ImmutableList.of(
            DepthFirstSearch.class,
            BreathFirstSearch.class,
            ManhattanDistanceAStar.class,
            HammingDistanceAStar.class,
            LinearConflictWithManhattanAStar.class,
            EuclideanDistanceAStar.class,
            BidirectionalBFS.class
    );

    /**
     * 运行一个算法，并输出统计信息
     */
    @AllArgsConstructor
    public static class RunAndStatisticsJob implements Runnable {

        private Class<? extends NPuzzle> algorithm;

        @Override
        public void run() {
            try {
                NPuzzle game = algorithm.newInstance();
                String algorithmName = algorithm.getSimpleName();

                Thread.currentThread().setName(algorithmName + "Thread");

                State endState = game.runGame();

                // 确保以下内容完整地输出
                synchronized (NPuzzleApplication.class) {
                    System.out.println("==================================================");
                    System.out.println(algorithmName);
//                    System.out.println("Total State checked: " + State.forkedTimes.get());
                    System.out.println("Total State checked: " + NPuzzle.stateCount());
                    // 不一定有解
                    if (endState != null) {
                        System.out.println("Solution Size: " + endState.tracePath().size());
                        if (NPuzzleConfig.PRINT_SOLUTION_PATH)
                            System.out.println("Solution: " + endState.tracePath());
//                        System.out.println("End State (just for check): ");
//                        System.out.println(ArrayUtils.toString(endState.getGrid()));
                    } else {
                        System.out.println("NO SOLUTION");
                    }
//                    System.out.println("==================================================");
                }
                barrier.await();
            } catch (InstantiationException | IllegalAccessException | BrokenBarrierException |
                     InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static CyclicBarrier barrier;

    private static void initCyclicBarrier(int count) {
        barrier = new CyclicBarrier(count + 1, () -> {
            System.out.println("======================");
            System.out.println("Current round finished");
        });
    }


    /**
     * 解析命令行参数，并将一些对象存入域中。最终返回该域对象
     *
     * @param args 命令行参数
     * @return 域对象
     */
    private static HashMap<String, Object> parseCommandLineArgs(String[] args) {
        HashMap<String, Object> model = new HashMap<>();
        File problems = null;

        // 1. 获取参数
        try {
            for (String arg : args) {
                if (arg.startsWith("--random")) {
                    NPuzzleConfig.RANDOM_GAME = Boolean.parseBoolean(arg.substring("--random".length()));
                } else if (arg.startsWith("--round")) {
                    NPuzzleConfig.GAME_ROUND = Integer.parseInt(arg.substring("--round".length()));
                } else if (arg.startsWith("--problems")) {
                    problems = new File(arg.substring("--problems".length()));
                    model.put("problems", problems);
                } else if (arg.startsWith("--row")) {
                    NPuzzleConfig.PUZZLE_ROW_COUNT = Integer.parseInt(arg.substring("--row".length()));
                } else if (arg.startsWith("--column")) {
                    NPuzzleConfig.PUZZLE_COLUMN_COUNT = Integer.parseInt(arg.substring("--column".length()));
                } else if (arg.startsWith("--auto-run")) {
                    NPuzzleConfig.AUTO_RUN_ALL_INSTANCES = Boolean.parseBoolean(arg.substring("--auto-run".length()));
                } else if (arg.startsWith("--print-sol")) {
                    NPuzzleConfig.PRINT_SOLUTION_PATH = Boolean.parseBoolean(arg.substring("--print-sol".length()));
                }
            }
        } catch (Exception e) {
            throw new Error("参数输入有误", e);
        }

        // 2. 参数校验
        if (!NPuzzleConfig.RANDOM_GAME) {
            if (problems == null || !problems.exists() || !problems.isFile()) {
                throw new Error("--random = false, a file of problems must be given");
            }
        }
        if (!(NPuzzleConfig.GAME_ROUND >= 1)) {
            throw new Error("--round >= 1");
        }
        if (!(NPuzzleConfig.PUZZLE_ROW_COUNT >= 2)) {
            throw new Error("--row >= 2");
        }
        if (!(NPuzzleConfig.PUZZLE_COLUMN_COUNT >= 2)) {
            throw new Error("--column >= 2");
        }

        // 3. 输出初始化参数
        System.out.println("====================================================");
        System.out.println("Initial Config Report:");
        System.out.println("Random Game? " + NPuzzleConfig.RANDOM_GAME);
        if (NPuzzleConfig.RANDOM_GAME) {
            System.out.println("Game Rounds: " + NPuzzleConfig.GAME_ROUND);
            System.out.format("Problem size: %d * %d\n", NPuzzleConfig.PUZZLE_ROW_COUNT, NPuzzleConfig.PUZZLE_COLUMN_COUNT);
        }
        System.out.println("====================================================");

        return model;
    }

    public static void main(String[] args) throws IOException, BrokenBarrierException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        // 初始化
        HashMap<String, Object> model = parseCommandLineArgs(args);

        // 让每个算法都在一个独立的线程中运行，用于统一运行、比较
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        initCyclicBarrier(algorithms.size());

        BufferedReader reader = null;
        if (!NPuzzleConfig.RANDOM_GAME) {
            reader = new BufferedReader(new InputStreamReader(Files.newInputStream(((File) model.get("problems")).toPath())));
        }

        for (int round = 1; ; round++) {
            System.out.println("Instance No. " + round + "\n");

            // 初始化当前N-Puzzle实例
            if (NPuzzleConfig.RANDOM_GAME) {
                if (round - 1 >= NPuzzleConfig.GAME_ROUND) {
                    System.out.println("No Instance No. " + round + " ~~~~");
                    break;
                }
                NPuzzle.initRandomGame();
            } else {
                boolean result = NPuzzle.initCustomGame(reader);
                if (!result) {
                    reader.close();
                    System.out.println("No Instance No. " + round + " ~~~~");
                    break;
                }
            }


            for (Class<? extends NPuzzle> algorithm : algorithms) {
                threadPool.submit(new RunAndStatisticsJob(algorithm));
            }

            barrier.await();

            if (!NPuzzleConfig.AUTO_RUN_ALL_INSTANCES) {
                System.out.print("Press ENTER key to move to the next instance: ");
                scanner.nextLine();
            }
            System.out.println("=========================\n\n");
        }

        System.out.println("Program finished");
        threadPool.shutdown();
    }

}
