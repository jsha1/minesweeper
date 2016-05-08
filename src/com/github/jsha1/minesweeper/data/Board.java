package com.github.jsha1.minesweeper.data;

import com.github.jsha1.minesweeper.common.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Board implements DisplayBoard{

    private final Cell[][] cells;
    private final int length;
    private final int numMines;
    private int numCovered;

    public Board(final int length, final int numMines) {
        this.cells = new Cell[length][length];
        this.length = length;
        this.numMines = numMines;
        this.numCovered = length * length;
        initializeBoard(cells, this.length, numMines);
    }

    private void initializeBoard(final Cell[][] board, final int length, final int numMinesToPlace) {

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                board[x][y] = new Cell(x, y);
            }
        }

        // place the mines
        int minesLeftToPlace = numMinesToPlace;
        while (minesLeftToPlace > 0) {
            final List<Cell> allCells = getAllCells();

            final int randomCellIndex = (int) Math.floor(Math.random() * allCells.size());
            final Cell cell = allCells.get(randomCellIndex);
            cell.uncover();
            if (!cell.isMine()) {
                final MineCell mineCell = new MineCell(cell.getX(),cell.getY());
                setCell(mineCell);
                updateMineProximityCountOfSurroundingCells(mineCell);
                minesLeftToPlace--;
            }
            cell.cover();
        }
    }

    private void updateMineProximityCountOfSurroundingCells(final MineCell cell) {

        final Set<Cell> surroundingCells = getSurroundingCells(cell.getX(),cell.getY());
        surroundingCells.parallelStream().forEach(Cell::updateMineProximityCount);
    }


    public Cell getCell(final int x, final int y) {
        if (isInvalidCoordinate(x, y, length)) {
            throw new IllegalArgumentException("Invalid cell coordinates: " + x + " " + y);
        }
        return cells[x][y];

    }

    void setCell(final Cell cell) {
        cells[cell.getX()][cell.getY()] = cell;
    }

    private boolean isInvalidCoordinate(final int x, final int y, final int length) {
        return (x < 0 || y < 0 || x >= length || y >= length);
    }

    public void uncoverCells(final int x, final int y){
        uncoverCells(x,y,new HashSet<>());
    }

    /**
     * Recursively uncover all blank cells surrounding given coordinates and any cells surrounding the blank cells
     */
    private void uncoverCells(final int x, final int y, final Set<Pair<Integer,Integer>> cellsAlreadyVisited) {

        if (x < 0 || y < 0 || x >= getLength() || y >= getLength() || cellsAlreadyVisited.contains(new Pair<>(x, y))) {
            return;
        }

        final Cell cell = getCell(x, y);
        if (cell.isCovered()) {
            cell.uncover();
            this.numCovered--;
        }

        cellsAlreadyVisited.add(new Pair<>(x, y));

        if (cell.isBlank()) {
            //if cell is blank, recursively uncover all surrounding cells
            getSurroundingCells(cell).forEach(c -> uncoverCells(c.getX(), c.getY(),cellsAlreadyVisited));
        }
    }

    @Override
    public void printBoard() {

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                System.out.print(cells[x][y].getDisplayValue());
            }
            System.out.println();
        }
    }

    @Override
    public void printUncoveredBoard() {

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                System.out.print(cells[x][y].getUncoveredDisplayString());
            }
            System.out.println();
        }
    }

    public int getNumCovered() {
        return numCovered;
    }

    public int getNumMines() {
        return numMines;
    }

    private List<DisplayCell> getAllCells(Predicate<DisplayCell> predicate){
        final List<DisplayCell> filteredCells = new ArrayList<>();

        for (final DisplayCell[] cells : this.cells) {
            for (final DisplayCell c : cells) {
                if (predicate.test(c)){
                    filteredCells.add(c);
                }
            }
        }
        return filteredCells;
    }


    @Override
    public List<DisplayCell> getAllCoveredCells() {
        return getAllCells(DisplayCell::isCovered);
    }

    @Override
    public List<DisplayCell> getAllUncoveredCells() {
        return getAllCells(cell -> !cell.isCovered());
    }

    @Override
    public void uncoverCell(DisplayCell cell) {
        if(cell.isCovered()){
            cell.uncover();
            numCovered--;
        }

    }

    public List<Cell> getAllCells() {
        return Arrays.stream(cells).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    @Override
    public boolean onlyMinesLeft() {

        System.out.println("onlyMinesLeft: " + getNumCovered() + " " + getNumMines());

        return getNumCovered() == getNumMines();
    }

    @Override
    public Set<DisplayCell> getSurroundingCells(DisplayCell cell) {
        return getSurroundingCells(cell.getX(), cell.getY()).stream().collect(Collectors.toSet());
    }

    private Set<Cell> getSurroundingCells(final int x, final int y) {

        final Set<Cell> cells = new HashSet<>();

        if(!isInvalidCoordinate(x - 1, y, length)) {
            addCell(cells, x - 1, y);
        }
        if(!isInvalidCoordinate(x + 1, y, length)) {
            addCell(cells, x + 1, y);
        }

        if(!isInvalidCoordinate(x, y - 1, length)) {
            addCell(cells, x, y - 1);
        }

        if(!isInvalidCoordinate(x, y + 1, length)) {
            addCell(cells, x, y + 1);
        }

        if(!isInvalidCoordinate(x + 1, y + 1, length)) {
            addCell(cells, x + 1, y + 1);
        }

        if(!isInvalidCoordinate(x - 1, y - 1, length)) {
            addCell(cells, x - 1, y - 1);
        }

        if(!isInvalidCoordinate(x - 1, y + 1, length)) {
            addCell(cells, x - 1, y + 1);
        }

        if(!isInvalidCoordinate(x + 1, y - 1, length)) {
            addCell(cells, x + 1, y - 1);
        }

        return cells;
    }

    private void addCell(final Set<Cell> set, final int x, final int y) {

        Cell cell = getCell(x, y);
        set.add(cell);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public DisplayCell getDisplayCell(int x, int y) {
        return getCell(x,y);
    }
}
