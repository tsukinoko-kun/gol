package de.hhn.seb.kprog;

import java.util.BitSet;
import java.util.Random;

/**
 * The world contains all cells.
 */
public class World {
    public final int size;
    private final BitSet cells;
    private final BitSet nextCells;

    public World(int size) {
        this.size = size;

        this.cells = new BitSet(size * size);
        this.nextCells = new BitSet(size * size);

        Random random = new Random(0x12345678);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int i = y * size + x;
                this.cells.set(i, random.nextBoolean());
            }
        }
        this.nextCells.or(this.cells);
    }

    /**
     * Initialize a world state for testing.
     */
    public void setState(boolean[][] state) {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                int i = y * this.size + x;
                this.cells.set(i, state[y][x]);
                this.nextCells.set(i, state[y][x]);
            }
        }
    }

    public boolean isAlive(int x, int y) {
        return this.cells.get(y * this.size + x);
    }

    /**
     * Set the next state of the cell to alive or dead without applying the state.
     * Call {@link #update()} to apply the state.
     */
    public void setAlive(int x, int y, boolean alive) {
        int i = y * this.size + x;
        this.nextCells.set(i, alive);
    }

    /**
     * Apply the next state of all cells set by {@link #setAlive(int, int, boolean)}.
     */
    public void update() {
        this.cells.clear();
        this.cells.or(this.nextCells);
    }
}
