package org.example.npuzzle.util;


public class NPuzzleUtils {
    private NPuzzleUtils() {
    }

    /**
     * 检查初始的grid是否合法
     */
    public static boolean checkValidness(int[][] grid) {
        int m = grid.length;
        if (m == 0) return false;
        int n = grid[0].length;
        if (n == 0) return false;

        // 检查grid是否为矩阵——每一行长度相同
        for (int[] row : grid) {
            if (row.length != n)
                return false;
        }

        int size = m * n;
        boolean[] visited = new boolean[size];

        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                int val = row[j];
                if (!(0 <= val && val < size))
                    return false;
                visited[val] = true;
            }
        }

        // 每个数字都要出现一次
        for (boolean b : visited) {
            if (!b) return false;
        }

        return true;
    }

    /**
     * 检查初始的grid是否有解
     */
    public static boolean checkSolvability(int[][] grid) {
        if (!checkValidness(grid))
            return false;

        int m = grid.length, n = grid[0].length;
        // 仅支持check方阵
        if (m != n) {
            System.out.println("This method only supports checking square matrix.");
            return false;
        }

        int[] arr = new int[m * n];
        for (int i = 0, k = 0; i < m; i++) {
            for (int j = 0; j < n; j++, k++) {
                arr[k] = grid[i][j];
            }
        }

        int inverseCount = 0;
        // 可以写一个类似merge-sort的计算inverses的算法，其时间复杂度为 O(nlog(n))
        // 力扣：剑指 Offer 51. 数组中的逆序对
        // 由于这里的n较小，故懒得写了
        for (int i = 0; i < n * n; i++) {
            for (int j = i + 1; j < n * n; j++) {
                if (arr[j] != 0 && arr[i] != 0 && arr[i] > arr[j])
                    inverseCount++;
            }
        }


        // 1. If N is odd, then puzzle instance is solvable if number of inversions
        // is even in the input state.
        if (n % 2 == 1 && inverseCount % 2 == 0)
            return true;
        // 2. If N is even, puzzle instance is solvable if
        //   - the blank is on an even row counting from the bottom (second-last, fourth-last, etc.)
        //   and number of inversions is odd.
        //   - the blank is on an odd row counting from the bottom (last, third-last, fifth-last, etc.)
        //   and number of inversions is even.
        if (n % 2 == 0) {
            int[] point = ArrayUtils.find(grid, 0);
            int rowCountFromBottom = n - point[0];
            if (rowCountFromBottom % 2 == 0 && inverseCount % 2 == 1)
                return true;
            else if (rowCountFromBottom % 2 == 1 && inverseCount % 2 == 0)
                return true;
        }

        // 3. For all other cases, the puzzle instance is not solvable.
        return false;
    }
}