package com.github.jsha1.minesweeper.data;

class MineCell extends Cell {

    public MineCell(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isMine() {
        if(isCovered()){
            throw new NotUncoveredException();
        }

        return true;
    }

    @Override
    public String getUncoveredDisplayString() {
        return "M";
    }
}
