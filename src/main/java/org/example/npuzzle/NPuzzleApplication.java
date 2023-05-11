package org.example.npuzzle;


import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.strategy.impl.DFS;
import org.example.npuzzle.util.ArrayUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NPuzzleApplication implements GlobalConstants {

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
                    System.out.println("Total State checked: " + State.forkedTimes.get());
                    // 不一定有解
                    if (endState != null) {
                        System.out.println("Solution: " + endState.tracePath());
                        System.out.println("End State (just for check): ");
                        System.out.println(ArrayUtils.toString(endState.getGrid()));
                    } else {
                        System.out.println("NO SOLUTION");
                    }
                    System.out.println("==================================================");
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        // 初始化
        NPuzzle.initRandomGame();

        List<Class<? extends NPuzzle>> algorithms = ImmutableList.of(DFS.class, DFS.class);

        // 让每个算法都在一个独立的线程中运行，用于统一运行、比较
//        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Class<? extends NPuzzle> algorithm : algorithms) {
//            threadPool.submit(new RunAndStatisticsJob(algorithm));
            new RunAndStatisticsJob(algorithm).run();
        }
    }

}
