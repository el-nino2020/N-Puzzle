package org.example.npuzzle.enums;

import lombok.AllArgsConstructor;

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
}