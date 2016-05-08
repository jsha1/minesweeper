package com.github.jsha1.minesweeper.data;

import com.github.jsha1.minesweeper.solver.TwoPassSolver;

public class TwoPassSolverMock extends TwoPassSolver {

    public int randomPick;

    public TwoPassSolverMock(int length) {
        super(length);
    }

    @Override
    public DisplayCell findNextCellToSelect(DisplayBoard board) {
        return super.findNextCellToSelect(board);
    }




    @Override
    public DisplayCell pickRandomCellNotIdentifiedAsAMine(DisplayBoard board) {
        randomPick++;
        return super.pickRandomCellNotIdentifiedAsAMine(board);
    }


}
