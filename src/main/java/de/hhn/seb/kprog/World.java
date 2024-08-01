package de.hhn.seb.kprog;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The world contains all cells.
 */
public class World {
    public final int size;
    private final List<List<Cell>> cells;

    public World(int size) {
        this.size = size;

        // initialize a list of cell rows
        this.cells = new LinkedList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            // create a new row
            List<Cell> row = new LinkedList<>();
            for (int j = 0; j < size; j++) {
                row.add(new Cell(random.nextBoolean()));
            }
            // add the row to the list of rows
            this.cells.add(row);
        }
    }

    /**
     * Initialize a world state for testing.
     */
    public void setState(boolean[][] state) {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.cells.get(y).get(x).setAlive(state[y][x]);
            }
        }
        this.update();
    }

    public boolean isAlive(int x, int y) {
        return this.cells.get(y).get(x).isAlive();
    }

    /**
     * Set the next state of the cell to alive or dead without applying the state.
     * Call {@link #update()} to apply the state.
     */
    public void setAlive(int x, int y, boolean alive) {
        this.cells.get(y).get(x).setAlive(alive);
    }

    /**
     * Apply the next state of all cells set by {@link #setAlive(int, int, boolean)}.
     */
    public void update() {
        for (List<Cell> row : this.cells) {
            for (Cell cell : row) {
                cell.update();
            }
        }
    }


    private static class Cell {
        private boolean alive;
        private boolean nextAlive;

        public Cell(boolean alive) {
            this.alive = alive;
        }

        public boolean isAlive() {
            return this.alive;
        }

        /**
         * Set the next state of the cell to alive or dead without applying the state.
         * Call {@link #update()} to apply the state.
         */
        public void setAlive(boolean alive) {
            this.nextAlive = alive;
        }

        /**
         * Apply the next state of the cell set by {@link #setAlive(boolean)}.
         */
        public void update() {
            this.alive = this.nextAlive;
        }
    }
}
