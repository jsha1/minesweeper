package com.github.jsha1.minesweeper.data;

import com.github.jsha1.minesweeper.solver.Solver;

public class Game {


    private DisplayBoard board;
    private State state = State.RUNNING;

    public Game(int length) {
        this.board = new Board(length, length);
    }


    public void selectCell(Solver solver) {
        selectCell(solver.findNextCellToSelect(board));
    }

    public void selectCell(DisplayCell cell) {
        selectCell(cell.getX(), cell.getY());
    }

    public void selectCell(int x, int y) {

        System.out.println("Selecting cell " + x + " " + y);

        DisplayCell cell = board.getDisplayCell(x, y);
        board.uncoverCell(cell);

        if (cell.isMine()) {
            state = State.LOSE;
            return;
        }
        board.uncoverCells(x, y);
        if (board.onlyMinesLeft()) {
            state = State.WIN;
            return;
        }

        state = State.RUNNING;
    }

    public void printBoard() {
        board.printBoard();
    }

    public void printUncoveredBoard() {
        board.printUncoveredBoard();
    }

    public boolean isFinished() {
        return state != State.RUNNING;
    }

    public boolean isWin() {
        return state == State.WIN;
    }

    public boolean isLose() {
        return state == State.LOSE;
    }

    public enum State {
        RUNNING,
        LOSE,
        WIN
    }


}
