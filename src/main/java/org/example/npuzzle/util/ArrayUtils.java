package org.example.npuzzle.util;


import java.util.Arrays;

@SuppressWarnings({"unused"})
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static int[][] copyArray(int[][] arr) {
        int[][] ans = new int[arr.length][];

        for (int i = 0; i < ans.length; i++) {
            ans[i] = Arrays.copyOf(arr[i], arr[i].length);
        }

        return ans;
    }

    public static String toString(int[][] arr) {
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

    /**
     * find the first-appeared position of val in arr
     *
     * @return [x, y] or null if val does not exist in arr
     */
    public static int[] find(int[][] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == val)
                    return new int[]{i, j};
            }
        }
        return null;
    }

    public static int[] toIntArray(String line, String regexSeparator) {
        return Arrays.stream(line.split(regexSeparator))
                .filter(s -> !("".equals(s)))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
