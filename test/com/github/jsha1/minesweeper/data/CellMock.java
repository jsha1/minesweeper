package com.github.jsha1.minesweeper.data;

public class CellMock extends Cell {

    public CellMock(int x, int y, int number, boolean isCovered) {
        super(x, y);
        setMineProximityCount(number);
        this.covered = isCovered;
    }
}
