package org.example.npuzzle;


import org.example.npuzzle.constants.GlobalConstants;
import org.example.npuzzle.entity.State;
import org.example.npuzzle.strategy.NPuzzleGame;
import org.example.npuzzle.strategy.impl.DFS;

public class NPuzzleApplication implements GlobalConstants {


    public static void main(String[] args) {
        NPuzzleGame nPuzzleGame = new DFS();
        State endState = nPuzzleGame.runGame();

        System.out.println(endState.getForkedTimes());
        System.out.println(endState.tracePath());
    }

}
