package gameoflife;

public class GridManager {

    enum GridState {
        STARTED, STOPPED, WORKING, LOADING
    }

    private int currentRow;
    private int currentCol;
    private GridState state = GridState.STOPPED;

    private static final int NEGATIVE_ONE = -1;
    private static final int POSITIVE_ONE = 1;

    public Cell[][] calculateNewState(Cell[][] grid) {
        setState(GridState.LOADING);
        Cell[][] newGrid = initializeNewGrid(grid);
        updateGridStates(grid, newGrid);
        setState(GridState.WORKING);
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
        for (currentRow = 0; currentRow < oldGrid.length; currentRow++) {
            for (currentCol = 0; currentCol < oldGrid[0].length; currentCol++) {
                int neighbours = countNeighbours(oldGrid, currentRow, currentCol);
                newGrid[currentRow][currentCol].isAlive = (neighbours == 2) ?
                        oldGrid[currentRow][currentCol].isAlive() : (neighbours == 3);
            }
        }
    }

    public int countNeighbours(Cell[][] grid, int row, int col) {
        setState(GridState.LOADING);
        validateCoordinates(row, col);
        this.currentRow = row;
        this.currentCol = col;

        int numberOfNeighbours = 0;
        numberOfNeighbours += incrementNeighbourCount(grid, NEGATIVE_ONE, NEGATIVE_ONE);
        numberOfNeighbours += incrementNeighbourCount(grid, NEGATIVE_ONE, 0);
        numberOfNeighbours += incrementNeighbourCount(grid, NEGATIVE_ONE, POSITIVE_ONE);
        numberOfNeighbours += incrementNeighbourCount(grid, 0, NEGATIVE_ONE);
        numberOfNeighbours += incrementNeighbourCount(grid, 0, POSITIVE_ONE);
        numberOfNeighbours += incrementNeighbourCount(grid, POSITIVE_ONE, NEGATIVE_ONE);
        numberOfNeighbours += incrementNeighbourCount(grid, POSITIVE_ONE, 0);
        numberOfNeighbours += incrementNeighbourCount(grid, POSITIVE_ONE, POSITIVE_ONE);

        setState(GridState.STARTED);
        return numberOfNeighbours;
    }

    private int incrementNeighbourCount(Cell[][] grid, int rowOffset, int colOffset) {
        return isAliveNeighbour(grid, currentRow + rowOffset, currentCol + colOffset) ? 1 : 0;
    }

    private void setState(GridState newState) {
        this.state = newState;
    }

    private void validateCoordinates(int row, int col) {
        if (row < 0) {
            throw new IllegalArgumentException("Row index cannot be negative");
        } else if (col < 0) {
            throw new IllegalArgumentException("Column index cannot be negative");
        }
    }

    private boolean isAliveNeighbour(Cell[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col].isAlive();
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public GridManager setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
        return this;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public GridManager setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
        return this;
    }
}