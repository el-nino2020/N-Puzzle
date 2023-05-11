package org.example.npuzzle.util;


import java.util.Random;

public class RandomUtils {
    private RandomUtils() {
    }

    public static Random currentTimeRandom() {
        return new Random(System.currentTimeMillis());
    }

}
