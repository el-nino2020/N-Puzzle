package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.State;
import org.example.npuzzle.enums.Direction;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.util.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;

public class BidirectionalBFS extends NPuzzle {

    /**
     * helper method for BFSAdvance()
     */
    private void setStateVisited(State state, Map<String, State> visitedStates) {
        visitedStates.put(ArrayUtils.toString(state.getGrid()), state);
    }

    /**
     * helper method for BFSAdvance()
     */
    private boolean isVisited(State state, Map<String, State> visitedStates) {
        return visitedStates.containsKey(ArrayUtils.toString(state.getGrid()));
    }

    /**
     * 取出 BFS 队列的头部节点，处理该节点。<br/>
     * 对于原本的BFS算法，该方法可以视作其中的一步：<br/>
     * <pre>
     * {@code
     *  ArrayDeque<State> queue = new ArrayDeque<>();
     *  Map<String, State> visitedStates visitedStates = new HashMap<>();
     *
     *  while (!que.isEmpty()) {
     *      State state = BFSAdvance(queue, visitedStates);
     *      if (state.isGoal()) {
     *          return state;
     *      }
     *  }
     *
     *  return null;
     * } </pre>
     *
     * @param queue         BFS 队列
     * @param visitedStates 已经访问过的状态集合
     * @return 取出的头节点；如果BFS队列为空，则返回 null
     */
    private State BFSAdvance(ArrayDeque<State> queue, Map<String, State> visitedStates) {
        if (queue.isEmpty()) return null;

        State state = queue.pollFirst();

        int x = state.getPoint().x;
        int y = state.getPoint().y;

        for (Direction nextDir : Direction.values()) {
            int dx = nextDir.dx, dy = nextDir.dy;

            // 下一步在边界范围内
            if (0 <= dx + x && dx + x < state.getGrid().length && 0 <= dy + y && dy + y < state.getGrid()[0].length) {
                State nextState = state.fork();

                nextState.setLastDirection(nextDir);
                ArrayUtils.swap(nextState.getGrid(), x, y, dx + x, dy + y);
                Point point = nextState.getPoint();
                point.x += dx;
                point.y += dy;

                // just mark it
                setStateVisited(nextState);

                if (isVisited(nextState, visitedStates)) continue;
                else setStateVisited(nextState, visitedStates);

                queue.addLast(nextState);
            }
        }

        return state;
    }

    /**
     * create a goal state based on the initial state
     */
    public State createGoalState(State initialState) {
        State ans = initialState.fork();

        int[][] grid = ans.getGrid();

        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0, k = 1; i < m; i++) {
            for (int j = 0; j < n; j++, k++) {
                if (i == m - 1 && j == n - 1) {
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = k;
                }
            }
        }

        ans.getPoint().x = m - 1;
        ans.getPoint().y = n - 1;
        ans.setParent(null);

        return ans;
    }


    @Nullable
    @Override
    protected State solveGame(State initialState) {
        // 正向BFS用
        ArrayDeque<State> startQueue = new ArrayDeque<>();
        Map<String, State> startVisitedStates = new HashMap<>();
        startQueue.addLast(initialState);
        setStateVisited(initialState, startVisitedStates);

        // 反向BFS用
        ArrayDeque<State> endQueue = new ArrayDeque<>();
        Map<String, State> endVisitedStates = new HashMap<>();
        State goalState = createGoalState(initialState);
        endQueue.addLast(goalState);
        setStateVisited(goalState, endVisitedStates);

        while ((!startQueue.isEmpty()) && (!endQueue.isEmpty())) {
            State state1 = BFSAdvance(startQueue, startVisitedStates);
            State state2 = BFSAdvance(endQueue, endVisitedStates);

            // 问题无解
            if (state1 == null || state2 == null) {
                return null;
            }

            // before: a <- b <- c <- ... <- f <- state1 = state2 -> m -> ... -> x -> y -> z
            // after : a <- b <- c <- ... <- f <- state2 <- m <- ... <- x <- y <- z
            // 类似于【反转链表】这道算法题，但是把 state1 节点抛弃了
            if (isVisited(state1, endVisitedStates) || isVisited(state2, startVisitedStates)) {
                if (isVisited(state1, endVisitedStates))
                    state2 = endVisitedStates.get(ArrayUtils.toString(state1.getGrid()));
                else if (isVisited(state2, startVisitedStates))
                    state1 = startVisitedStates.get(ArrayUtils.toString(state2.getGrid()));

                State prev = state1.getParent();
                while (state2 != null) {
                    State next = state2.getParent();
                    state2.setParent(prev);
                    state2.setLastDirection(Direction.calculateDirection(prev.getPoint(), state2.getPoint()));
//                    Objects.requireNonNull(state2.getLastDirection(), "实现有问题");

                    prev = state2;
                    state2 = next;
                }

                return prev;
            }
        }
        return null;
    }
}
