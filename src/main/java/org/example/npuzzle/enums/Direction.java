package org.example.npuzzle.enums;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    public final int dx;
    public final int dy;

    /**
     * 返回 direction 相对的方向
     *
     * @param direction
     * @return 相对的方向
     */
    public static Direction reverseDirection(Direction direction) {
        switch (direction) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        throw new RuntimeException("UNREACHABLE");
    }

    public static Direction calculateDirection(Point from, Point to) {
        if (from.x == to.x) {
            if (from.y - 1 == to.y) return LEFT;
            if (from.y + 1 == to.y) return RIGHT;
        }
        if (from.y == to.y) {
            if (from.x - 1 == to.x) return UP;
            if (from.x + 1 == to.x) return DOWN;
        }
        return null;
    }
}