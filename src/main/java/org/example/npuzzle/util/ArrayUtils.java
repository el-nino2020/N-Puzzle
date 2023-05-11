package org.example.npuzzle.util;


import java.util.Arrays;

@SuppressWarnings({"unused"})
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static int[][] copy2DIntArray(int[][] arr) {
        int[][] ans = new int[arr.length][];

        for (int i = 0; i < ans.length; i++) {
            ans[i] = Arrays.copyOf(arr[i], arr[i].length);
        }

        return ans;
    }

    public static String arrayToString(int[][] arr) {
        StringBuilder ans = new StringBuilder();

        for (int[] row : arr) {
            ans.append(Arrays.toString(row));
            ans.append("\n");
        }

        return ans.toString();
    }

    /**
     * swap(arr[x1][y1], arr[x2][y2])
     */
    public static void swap(int[][] arr, int x1, int y1, int x2, int y2) {
        int temp = arr[x1][y1];
        arr[x1][y1] = arr[x2][y2];
        arr[x2][y2] = temp;
    }


}
