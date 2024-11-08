package gameoflife;

/**
 *
 */
public class Cell {

    public boolean isAlive;

    /**
     * @param isAlive
     */
    public Cell(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * @return
     */
    public boolean isAlive() {
        return isAlive;
    }
}
