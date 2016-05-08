package com.github.jsha1.minesweeper.solver;

import com.github.jsha1.minesweeper.data.DisplayBoard;
import com.github.jsha1.minesweeper.data.DisplayCell;

public interface Solver {

    DisplayCell findNextCellToSelect(DisplayBoard board);
}
