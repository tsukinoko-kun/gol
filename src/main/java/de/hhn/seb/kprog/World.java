package de.hhn.seb.kprog;

import java.util.Arrays;
import java.util.Random;

/**
 * The world contains all cells.
 */
public class World implements IRules {
    public final int size;
    public final int length;
    private final char[] cells;
    private final char[] oldCells;
    private final DrawFunction alive;
    private final DrawFunction dead;

    public World(int size) {
        this(size, null, null);
    }

    public World(int size, DrawFunction alive, DrawFunction dead) {
        this.size = size;
        this.length = size * size;
        this.alive = alive;
        this.dead = dead;

        this.cells = new char[this.length];
        this.oldCells = new char[this.length];

        Random random = new Random(0x12345678);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (random.nextBoolean()) {
                    this.setCell(x, y);
                }
            }
        }
    }

    public boolean isAlive(int x, int y) {
        return (this.oldCells[y * this.size + x] & 0x01) == 0x01;
    }

    public int getAliveNeighbours(int x, int y) {
        return this.oldCells[y * this.size + x] >> 1;
    }

    public void setCell(int x, int y) {
        int i = y * this.size + x;


        int xLeft, xRight, yUp, yDown;

        if (x == 0) {
            xLeft = this.size - 1;
        } else {
            xLeft = -1;
        }

        if (x == this.size - 1) {
            xRight = -(this.size - 1);
        } else {
            xRight = 1;
        }

        if (y == 0) {
            yUp = this.length - this.size;
        } else {
            yUp = -this.size;
        }

        if (y == this.size - 1) {
            yDown = -(this.length - this.size);
        } else {
            yDown = this.size;
        }

        // set 0th bit to 1, to indicate that the cell is alive
        this.cells[i] |= 0x01;

        /*
         * Increment neighbours counters
         * Starting from bit index 1 (not 0) because the first bit is used to store the state of the cell.
         */

        // top left
        this.cells[i + xLeft + yUp] += 0b10;
        // top
        this.cells[i + yUp] += 0b10;
        // top right
        this.cells[i + xRight + yUp] += 0b10;
        // left
        this.cells[i + xLeft] += 0b10;
        // right
        this.cells[i + xRight] += 0b10;
        // bottom left
        this.cells[i + xLeft + yDown] += 0b10;
        // bottom
        this.cells[i + yDown] += 0b10;
        // bottom right
        this.cells[i + xRight + yDown] += 0b10;
    }

    public void clearCell(int x, int y) {
        int i = y * this.size + x;

        int xLeft, xRight, yUp, yDown;

        if (x == 0) { // left edge
            xLeft = this.size - 1;
            xRight = 1;
        } else if (x == this.size - 1) { // right edge
            xLeft = -1;
            xRight = -(this.size - 1);
        } else { // no x edge
            xLeft = -1;
            xRight = 1;
        }

        if (y == 0) { // top edge
            yUp = this.length - this.size;
            yDown = this.size;
        } else if (y == this.size - 1) { // bottom edge
            yUp = -this.size;
            yDown = -(this.length - this.size);
        } else { // no y edge
            yUp = -this.size;
            yDown = this.size;
        }

        // set 0th bit to 0, to indicate that the cell is dead
        this.cells[i] &= 0b11111110;

        /*
         * Decrement neighbours counters
         * Starting from bit index 1 (not 0) because the first bit is used to store the state of the cell.
         */

        // top left
        this.cells[i + xLeft + yUp] -= 0b10;
        // top
        this.cells[i + yUp] -= 0b10;
        // top right
        this.cells[i + xRight + yUp] -= 0b10;
        // left
        this.cells[i + xLeft] -= 0b10;
        // right
        this.cells[i + xRight] -= 0b10;
        // bottom left
        this.cells[i + xLeft + yDown] -= 0b10;
        // bottom
        this.cells[i + yDown] -= 0b10;
        // bottom right
        this.cells[i + xRight + yDown] -= 0b10;
    }

    /**
     * Initialize a world state for testing.
     */
    public void setState(boolean[][] state) {
        Arrays.fill(this.cells, (char) 0);

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if (state[y][x]) {
                    this.setCell(x, y);
                }
            }
        }

        this.update();
    }

    /**
     * Copy values {@link #cells} to {@link #oldCells}.
     */
    public void update() {
        System.arraycopy(this.cells, 0, this.oldCells, 0, this.length);
    }

    @Override
    public void tick() {
        int aliveNeighbours;

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                // aliveNeighbours is stored in bits 1-4
                aliveNeighbours = this.getAliveNeighbours(x, y);

                // check if the cell is alive at bit 0
                if (this.isAlive(x, y)) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                        this.clearCell(x, y);
                        if (this.dead != null) this.dead.draw(x, y);
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        this.setCell(x, y);
                        if (this.alive != null) this.alive.draw(x, y);
                    }
                }
            }
        }

        this.update();
    }

    @FunctionalInterface
    public interface DrawFunction {
        void draw(int x, int y);
    }
}
