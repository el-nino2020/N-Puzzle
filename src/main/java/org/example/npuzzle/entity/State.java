package org.example.npuzzle.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.enums.Direction;
import org.example.npuzzle.util.ArrayUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PUZZLE 状态
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State implements GlobalConstants {
    /**
     * 记录一共调用过多少次 fork()方法， <br/>
     * 该字段可以用来评定某一种算法的优劣与否 <br/>
     * 考虑到使用多线程同时运行不同算法，使用 ThreadLocal存放某一算法的fork次数
     *
     * @see State#fork()
     */
    public static final ThreadLocal<Long> forkedTimes = ThreadLocal.withInitial(() -> 0L);

    /**
     * 将puzzle表示为一个二维数组
     */
    private int[][] grid;

    /**
     * grid有m行
     */
    private int m;

    /**
     * grid有n列
     */
    private int n;

    /**
     * parent state
     */
    private State parent;

    /**
     * 记录  parent state 到 current state 空格的移动方向
     */
    private Direction lastDirection;

    /**
     * 空格的坐标，grid中使用数字 0 表示该空格
     */
    private Point point;


    /**
     * 创建一个子节点, 将子节点的parent字段指向自己，<br/>
     * 深拷贝其他字段，例外如下：<br/>
     * lastDirection = null, 该字段需要调用者手动处理 <br/>
     *
     * @return 子节点
     */
    public State fork() {
        forkedTimes.set(forkedTimes.get() + 1);
        State ans = new State();

        ans.parent = this;
        ans.grid = ArrayUtils.copyArray(this.grid);
        ans.m = this.m;
        ans.n = this.n;
        ans.point = new Point(this.point.x, this.point.y);
        ans.lastDirection = null;

        return ans;
    }

    /**
     * 当前状态是否已经满足 N-puzzle 的终止条件
     *
     * @return boolean
     */
    public final boolean isGoal() {
        for (int i = 0, k = 1; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++, k++) {
                if (i == grid.length - 1 && j == grid[i].length - 1) {
                    return grid[i][j] == 0;
                }
                if (grid[i][j] != k) {
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * 追溯游戏开始状态到目前状态 空格的移动路径
     *
     * @return path
     */
    public List<String> tracePath() {
        ArrayList<String> ans = new ArrayList<>();

        State ptr = this;
        while (ptr.lastDirection != null) {
            ans.add(ptr.lastDirection.name());
            ptr = ptr.parent;
        }

        // 反向追溯，因而要reverse
        Collections.reverse(ans);

        return ans;
    }

    @Override
    public String toString() {
        return "State{" +
                "lastDirection=" + lastDirection +
                ", forkedTimes=" + forkedTimes.get() +
                "===================================" +
                ArrayUtils.toString(grid) +
                "===================================" +
                '}';
    }
}
