package com.github.jsha1.minesweeper.solver;

import com.github.jsha1.minesweeper.data.DisplayBoard;
import com.github.jsha1.minesweeper.data.DisplayCell;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A solver implementation
 *
 * For each turn
 *
 * 1) pass through each cell and figure out which covered fields are mines. We can tell if a covered cell is a mine if the proximity count of a surrounding uncovered cell
 * equals the number of covered cell it touches.
 *
 * 2) in the second pass find any "for sure not a mine" covered cell. This is done by checking for cells where the proximity count is fully accounted for in the found mines(from step 1).
 * Each additional surrounding blank cell is assumed to not be a mine.
 *
 * 3) If no "for sure mine" from step 2 is found, return a random pick from the covered cells that are not marked as a mine from step 1. TODO this could probably be optimized
 * further giving weight to covered cells based on their surrounding cells
 */
public class TwoPassSolver implements Solver {

    private final boolean[][] foundMines;

    public TwoPassSolver(final int length) {
        foundMines = new boolean[length][length];
    }

    @Override
    public DisplayCell findNextCellToSelect(final DisplayBoard board) {

        findKnownMines(board);

        final Optional<DisplayCell> forSureSelection = findForSureSelection(board);

        if (forSureSelection.isPresent()) {
            System.out.println("for sure selection found");
            return forSureSelection.get();
        } else {
            System.out.println("no for sure selection found");
            return pickRandomCellNotIdentifiedAsAMine(board);
        }
    }

    Optional<DisplayCell> findForSureSelection(final DisplayBoard board) {

        //find known mines, do 1 pass for now. Possibly need to do multiple
        List<DisplayCell> allUncoveredCells = board.getAllUncoveredCells();
        List<DisplayCell> cells = allUncoveredCells.stream().filter(DisplayCell::isNumberCell).collect(Collectors.toList());
        for(DisplayCell cell : cells){
            //find surrounding cells for more uncovered cells if number of uncovered cells == number
            final Set<DisplayCell> surroundingCells = board.getSurroundingCells(cell);
            if (numFoundMines(surroundingCells) == cell.getMineProximityCount()) {
                final Optional<DisplayCell> coveredNotMine = surroundingCells.stream().filter(DisplayCell::isCovered).filter(c -> !foundMines[c.getX()][c.getY()]).findAny();
                if(coveredNotMine.isPresent()) {
                    return coveredNotMine;
                }
            }
        }

        return Optional.empty();
    }

    protected DisplayCell pickRandomCellNotIdentifiedAsAMine(final DisplayBoard board) {

        final List<DisplayCell> candidateCoveredCells = board.getAllCoveredCells().stream().filter(c -> !foundMines[c.getX()][c.getY()]).collect(Collectors.toList());
        final int randomIndex = (int) Math.floor(Math.random() * candidateCoveredCells.size());
        return candidateCoveredCells.get(randomIndex);
    }

    private long numFoundMines(final Set<DisplayCell> cells) {
        return cells.stream().filter(c -> foundMines[c.getX()][c.getY()]).count();
    }

    private boolean[][] findKnownMines(final DisplayBoard board) {

        final List<DisplayCell> allCells = board.getAllUncoveredCells();
        //find known mines, do 1 pass for now. Possibly need to do multiple
        allCells.stream().filter(DisplayCell::isNumberCell).forEach(cell -> {
            //find surrounding cells for more uncovered cells
            //if number of uncovered cells == number
            final List<DisplayCell> coveredCells = board.getSurroundingCells(cell).stream().filter(DisplayCell::isCovered).collect(Collectors.toList());
            if (coveredCells.size() == cell.getMineProximityCount()) {

                for (final DisplayCell mineCell : coveredCells) {
                    final int mineCellX = mineCell.getX();
                    final int mineCellY = mineCell.getY();
                    if(!foundMines[mineCellX][mineCellY]) {
                        System.out.println("found mine at " + mineCellX + " " + mineCellY);
                        foundMines[mineCellX][mineCellY] = true;
                    }
                }
            }
        });
        return foundMines;
    }

}
