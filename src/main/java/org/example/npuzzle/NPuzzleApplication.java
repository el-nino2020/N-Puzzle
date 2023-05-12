package org.example.npuzzle;


import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.strategy.impl.BreathFirstSearch;
import org.example.npuzzle.strategy.impl.DepthFirstSearch;

import java.util.List;
import java.util.concurrent.CountDownLatch;
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
                        // path 可能非常长，不适合全部打印出来
                        System.out.println("Solution Size: " + endState.tracePath().size());
//                        System.out.println("End State (just for check): ");
//                        System.out.println(ArrayUtils.toString(endState.getGrid()));
                    } else {
                        System.out.println("NO SOLUTION");
                    }
//                    System.out.println("==================================================");
                }
                latch.countDown();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 用于阻塞主线程shutdown线程池的操作
    private static CountDownLatch latch;

    public static void initLatch(int count) {
        latch = new CountDownLatch(count);
    }

    public static void main(String[] args) throws InterruptedException {
        List<Class<? extends NPuzzle>> algorithms = ImmutableList.of(
                DepthFirstSearch.class,
                BreathFirstSearch.class);


        // 初始化
        NPuzzle.initRandomGame();
        initLatch(algorithms.size());
        // 让每个算法都在一个独立的线程中运行，用于统一运行、比较
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Class<? extends NPuzzle> algorithm : algorithms) {
            threadPool.submit(new RunAndStatisticsJob(algorithm));
        }

        latch.await();
        threadPool.shutdown();
    }

}
