package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.State;
import org.example.npuzzle.enums.Direction;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.util.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayDeque;


public class DepthFirstSearch extends NPuzzle {
    @Nullable
    @Override
    protected State solveGame(State initialState) {

        ArrayDeque<State> stk = new ArrayDeque<>();
        stk.add(initialState);

        while (!stk.isEmpty()) {
            State state = stk.pollLast();

            if (state.isGoal()) {
                return state;
            }

            int x = state.getPoint().x;
            int y = state.getPoint().y;

            for (Direction nextDir : Direction.values()) {
                int dx = nextDir.dx, dy = nextDir.dy;

                // 下一步在边界范围内
                if (0 <= dx + x && dx + x < State.M && 0 <= dy + y && dy + y < State.N) {
                    State nextState = state.fork();

                    nextState.setLastDirection(nextDir);
                    ArrayUtils.swap(nextState.getGrid(), x, y, dx + x, dy + y);
                    Point point = nextState.getPoint();
                    point.x += dx;
                    point.y += dy;

                    if (isVisited(nextState)) continue;
                    else setStateVisited(nextState);

                    stk.addLast(nextState);
                }
            }
        }

        return null;
    }
}
