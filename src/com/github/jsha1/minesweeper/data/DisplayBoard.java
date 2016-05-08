package com.github.jsha1.minesweeper.data;

import java.util.List;
import java.util.Set;

public interface DisplayBoard {

    DisplayCell getDisplayCell(int x, int y);

    void uncoverCells(int x, int y);

    /**
     * All of the covered cells are mines
     * @return true ; false otherwise
     */
    boolean onlyMinesLeft();

    void printUncoveredBoard();

    void printBoard();

    List<DisplayCell> getAllCoveredCells();

    List<DisplayCell> getAllUncoveredCells();

    int getLength();

    Set<DisplayCell> getSurroundingCells(DisplayCell cell);

    void uncoverCell(DisplayCell cell);
}
