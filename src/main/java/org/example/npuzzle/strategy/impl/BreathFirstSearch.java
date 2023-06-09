package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.State;
import org.example.npuzzle.enums.Direction;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.util.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayDeque;


public class BreathFirstSearch extends NPuzzle {
    @Nullable
    @Override
    protected State solveGame(State initialState) {

        ArrayDeque<State> queue = new ArrayDeque<>();
        queue.add(initialState);

        while (!queue.isEmpty()) {
            State state = queue.pollFirst();

            if (state.isGoal()) {
                return state;
            }

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

                    if (isVisited(nextState)) continue;
                    else setStateVisited(nextState);

                    queue.addLast(nextState);
                }
            }
        }

        return null;
    }
}
