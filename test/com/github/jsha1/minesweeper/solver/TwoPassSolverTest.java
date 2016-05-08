package com.github.jsha1.minesweeper.solver;

import com.github.jsha1.minesweeper.data.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TwoPassSolverTest {


    private BoardMock almostDoneBoard;


    /**
     * A 3x3 test board that looks like this
     * 11.
     * X1.
     * X1.
     */
    @Before
    public void setUp() throws Exception {

        BoardMock testBoard = new BoardMock(3, 1);

        testBoard.setCell(new CellMock(0, 0, 1, false));
        testBoard.setCell(new CellMock(0, 1, 1, false));
        testBoard.setCell(new CellMock(0, 2, 0, false));

        testBoard.setCell(new MineCellMock(1, 0, 0, true));
        testBoard.setCell(new CellMock(1, 1, 1, false));
        testBoard.setCell(new CellMock(1, 2, 0, false));

        testBoard.setCell(new CellMock(2, 0, 1, true));
        testBoard.setCell(new CellMock(2, 1, 1, false));
        testBoard.setCell(new CellMock(2, 2, 0, false));
        almostDoneBoard = testBoard;
    }

    @Test
    public void findForSureSelection() {

        TwoPassSolverMock solver = new TwoPassSolverMock(3);
        DisplayCell nextCellToSelect = solver.findNextCellToSelect(almostDoneBoard);
        assertTrue(nextCellToSelect.getX() == 2 && nextCellToSelect.getY() == 0);
        assertEquals(0,solver.randomPick);
    }


    @Test
    public void randomSelection() {

        TwoPassSolverMock solver = new TwoPassSolverMock(3);
        almostDoneBoard.setCell(new CellMock(0, 1, 0, true));

        DisplayCell nextCellToSelect = solver.findNextCellToSelect(almostDoneBoard);
        assertNotNull(nextCellToSelect);
        assertEquals(1,solver.randomPick);

    }

}