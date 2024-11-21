package gameoflife;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GridManager {

    public final static List<NeighbourOffset> NEIGHBOURS_OFFSETS = Arrays.asList(
            new NeighbourOffset(-1, -1),
            new NeighbourOffset(-1, 0),
            new NeighbourOffset(-1, 1),
            new NeighbourOffset(0, -1),
            new NeighbourOffset(0, 1),
            new NeighbourOffset(1, -1),
            new NeighbourOffset(1, 0),
            new NeighbourOffset(1, 1)
    );

    public Cell[][] calculateNewState(Cell[][] grid) {
        Cell[][] newGrid = initializeNewGrid(grid);
        updateGridStates(grid, newGrid);
        return newGrid;
    }

    private Cell[][] initializeNewGrid(Cell[][] grid) {
        Cell[][] newGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = new Cell(false);
            }
        }
        return newGrid;
    }

    private void updateGridStates(Cell[][] oldGrid, Cell[][] newGrid) {
        for (int row = 0; row < oldGrid.length; row++) {
            for (int col = 0; col < oldGrid[0].length; col++) {
                int neighbours = countNeighbours(oldGrid, row, col);
                newGrid[row][col].setAlive((neighbours == 2) ?
                        oldGrid[row][col].isAlive() : (neighbours == 3));
            }
        }
    }

    public int countNeighbours(Cell[][] grid, int row, int col) {
        validateCoordinates(row, col);

        AtomicInteger numberOfNeighbours = new AtomicInteger();
        NEIGHBOURS_OFFSETS.forEach(neighbourOffset -> numberOfNeighbours.addAndGet(incrementNeighbourCount(grid, row, col, neighbourOffset.rowOffset(), neighbourOffset.colOffset())));

        return numberOfNeighbours.get();
    }

    private int incrementNeighbourCount(Cell[][] grid, int row, int col, int rowOffset, int colOffset) {
        return isAliveNeighbour(grid, row + rowOffset, col + colOffset) ? 1 : 0;
    }

    private void validateCoordinates(int row, int col) {
        if (row < 0) {
            throw new IllegalArgumentException("Row index cannot be negative");
        }
        if (col < 0) {
            throw new IllegalArgumentException("Column index cannot be negative");
        }
    }

    private boolean isAliveNeighbour(Cell[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col].isAlive();
    }
}