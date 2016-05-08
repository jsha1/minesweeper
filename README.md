# minesweeper 

The original challange was here https://gist.github.com/jmtame/4885c2e4584e7784f040 and originally intended for screening purposes. However, I didn't (and don't plan on) making any submission myself.

With a 10x10 board and 10 mines, the solve rate is 74.54% (100k games). There is certainly room for some improvement.

## Main points from the original challenge:

### What will you be building?

We're going to implement Minesweeper. Please familiarize yourself with the rules and gameplay [here](https://en.wikipedia.org/wiki/Microsoft_Minesweeper). Try playing the game [here](http://minesweeperonline.com/).

There are two parameters that you should ask from the user when you setup a Minesweeper board:

- The size of the grid to use (NxN square grid).
- The number of mines to be placed.

Some considerations to keep in mind when you're building:

- The game is lost when the user clicks on a cell the contains a mine.
- The game is won when all the covered cells are cells containing mines.
- The mines should be randomly placed on the grid.
- You don't need to build support for flagging cells. The game should end when all covered cells are cells containing mines.

# Challenge B: Ruby, Python, or Java

Implement Minesweeper using Ruby, Python, or Java. Your Minesweeper application should run in a terminal and accept user input.

Represent the various states of the cells in the following way:

- A covered, untouched cell should be an X or this unicode character: ◼.
- An uncovered, empty cell should be a period.
- A cell with a mine should be an 'M'.

These aren't strict requirements. You're free to use your own creativity or represent the board in a different way if you feel inclined.

Once you've completed implementing Minesweeper, proceed to building a solver (see below).

# Building a solver

Once your Minesweeper implementation is complete, write a solver that attempts to solve your Minesweeper puzzles 100,000 times with the following parameters:

- The size of the board should be 10x10.
- The number of mines should be 10.

The output should be:

- The number of puzzles successfully solved out of 100,000 attempts.
- The total time to complete the 100,000 attempts.
