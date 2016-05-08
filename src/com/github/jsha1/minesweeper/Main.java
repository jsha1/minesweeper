package com.github.jsha1.minesweeper;

import com.github.jsha1.minesweeper.data.Game;
import com.github.jsha1.minesweeper.solver.Solver;
import com.github.jsha1.minesweeper.solver.TwoPassSolver;

import java.text.NumberFormat;

public class Main {

    public static final int NUM_GAMES = 100000;
    public static final int BOARD_LENGTH = 10;

    public static void main(String[] args) {

        playNGamesWithSolver(NUM_GAMES);
    }

    private static void playNGamesWithSolver(int numGames) {

        int win = 0;
        for (int i = 0; i < numGames; i++) {

            Game game = new Game(BOARD_LENGTH);
            Solver solver = new TwoPassSolver(BOARD_LENGTH);
            System.out.println("UncoveredBoard");
            game.printUncoveredBoard();
            System.out.println("end UncoveredBoard");
            while (!game.isFinished()) {

                game.printBoard();
                System.out.println("--------");
                game.selectCell(solver);
            }

            if (game.isWin()) {
                System.out.println("WIN");
                win++;
            } else if (game.isLose()) {
                System.out.println("LOSE");
            }

        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        System.out.println("Win %: " + numberFormat.format(win / (double) NUM_GAMES * 100));
    }
}
