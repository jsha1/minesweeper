package com.github.jsha1.minesweeper.data;

public interface DisplayCell {

    boolean isMine() throws NotUncoveredException;

    int getMineProximityCount() throws NotUncoveredException;

    boolean isCovered();

    /**
     * Is a cell that would have a number on it (!isBlank() && !mine
     */
    boolean isNumberCell() throws NotUncoveredException;

    int getX();

    int getY();

    void uncover();
}
