package org.example.npuzzle.strategy.impl;

import org.example.npuzzle.entity.AStarState;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.enums.Direction;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.util.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Comparator;
import java.util.PriorityQueue;

//设计为抽象类，考虑到会使用到多个 heuristic 函数
public abstract class AStar extends NPuzzle {

    public abstract int calculateHeuristic(AStarState state);

    @Nullable
    @Override
    protected final State solveGame(State initialState) {
        AStarState initAStarState = new AStarState(initialState, 0, 0);

        PriorityQueue<AStarState> pq = new PriorityQueue<>(Comparator.comparingInt(AStarState::getF));

        pq.add(initAStarState);
        while (!pq.isEmpty()) {
            AStarState state = pq.poll();

            // 在之前的某一刻, pq中有这样两个 state，其grid相同，但是f值不同，f值小的state会先被访问
            if (isVisited(state)) {
                continue;
            } else {
                setStateVisited(state);
            }

            if (state.isGoal()) {
                return state;
            }

            int x = state.getPoint().x;
            int y = state.getPoint().y;

            for (Direction nextDir : Direction.values()) {
                int dx = nextDir.dx, dy = nextDir.dy;

                // 下一步在边界范围内
                if (0 <= dx + x && dx + x < state.getGrid().length && 0 <= dy + y && dy + y < state.getGrid()[0].length) {
                    AStarState nextState = state.fork();

                    nextState.setLastDirection(nextDir);
                    ArrayUtils.swap(nextState.getGrid(), x, y, dx + x, dy + y);
                    Point point = nextState.getPoint();
                    point.x += dx;
                    point.y += dy;

                    nextState.setG(nextState.getG() + 1);
                    nextState.setH(calculateHeuristic(nextState));
                    nextState.recalculateF();

                    if (isVisited(nextState)) continue;

                    pq.add(nextState);
                }
            }
        }


        return null;
    }
}
