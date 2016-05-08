package com.github.jsha1.minesweeper.data;

public class MineCellMock extends MineCell {
    public MineCellMock(int x, int y) {
        super(x, y);
    }

    public MineCellMock(int x, int y, int number, boolean isCovered) {
        super(x, y);
        setMineProximityCount(number);
        this.covered = isCovered;
    }
}
