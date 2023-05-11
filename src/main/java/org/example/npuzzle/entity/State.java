package org.example.npuzzle.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.npuzzle.constants.GlobalConstants;

import java.awt.*;
import java.util.Arrays;

/**
 * PUZZLE 状态
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State implements GlobalConstants {
    /**
     * 将puzzle表示为一个二维数组
     */
    private int[][] grid;

    /**
     * parent state
     */
    private State parent;

    /**
     * 空格的坐标
     */
    private Point point;

    /**
     * 创建一个子节点, 将子节点的parent字段指向自己，深拷贝其他字段
     *
     * @return 子节点
     */
    public State fork() {
        State ans = new State();

        ans.parent = this;

        ans.grid = new int[this.grid.length][];
        for (int i = 0; i < grid.length; i++) {
            ans.grid[i] = Arrays.copyOf(this.grid[i], this.grid[i].length);
        }

        ans.point = new Point(this.point.x, this.point.y);

        return ans;
    }

    public String gridToString() {
        StringBuilder ans = new StringBuilder(PUZZLE_SIZE << 2);

        for (int[] row : grid) {
            ans.append(Arrays.toString(row));
            ans.append("\n");
        }

        return ans.toString();
    }

}
