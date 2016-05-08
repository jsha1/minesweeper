package com.github.jsha1.minesweeper.data;

class Cell implements DisplayCell{

    protected boolean covered = true;
    private int mineProximityCount;
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isBlank() {
        return !isMine() && mineProximityCount == 0;
    }

    @Override
    public boolean isNumberCell() throws NotUncoveredException {

        if(isCovered()){
            throw new NotUncoveredException();
        }

        return !isBlank() && !isMine();
    }

    @Override
    public int getMineProximityCount() throws NotUncoveredException{
        if(isCovered()){
            throw new NotUncoveredException();
        }
        return mineProximityCount;
    }

    void setMineProximityCount(int mineProximityCount) {
        this.mineProximityCount = mineProximityCount;
    }

    void cover() {
        this.covered = true;
    }

    @Override
    public boolean isMine() throws NotUncoveredException{
        if(isCovered()){
            throw new NotUncoveredException();
        }
        return false;
    }

    public String getUncoveredDisplayString() {
        return mineProximityCount == 0 ? "." : String.valueOf(mineProximityCount);
    }

    @Override
    public boolean isCovered() {
        return covered;
    }

    @Override
    public void uncover() {
        covered = false;
    }

    public int updateMineProximityCount() {
        return ++mineProximityCount;
    }

    public String getDisplayValue() {
        if (isCovered()) {
            return "X";
        } else {
            return getUncoveredDisplayString();
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
