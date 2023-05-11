package org.example.npuzzle.strategy.impl;


import org.example.npuzzle.entity.State;
import org.example.npuzzle.enums.Direction;
import org.example.npuzzle.strategy.NPuzzle;
import org.example.npuzzle.util.ArrayUtils;

import java.awt.*;
import java.util.ArrayDeque;

import static org.example.npuzzle.enums.Direction.*;

public class DepthFirstSearch extends NPuzzle {
    @Override
    protected State solveGame(State initialState) {
        int m = initialState.getM(), n = initialState.getN();

        ArrayDeque<State> stk = new ArrayDeque<>();
        stk.add(initialState);

        while (!stk.isEmpty()) {
            State state = stk.pollLast();

            if (state.isGoal()) {
                return state;
            }

            int x = state.getPoint().x;
            int y = state.getPoint().y;

            Direction reverseDir = state.getLastDirection() == null ? null :
                    reverseDirection(state.getLastDirection());

            for (Direction nextDir : values()) {
                int dx = nextDir.dx, dy = nextDir.dy;

                // 没必要往回移动
                if (nextDir.equals(reverseDir)) continue;

                // 下一步在边界范围内
                if (0 <= dx + x && dx + x < m && 0 <= dy + y && dy + y < n) {
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
