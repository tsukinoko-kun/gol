package de.hhn.seb.kprog;

/**
 * The rules of the game of life.
 */
public class Rules {
    private final World world;
    private final int size;
    private final int sizeMinusOne;

    public Rules(World world) {
        this.world = world;
        this.size = world.size;
        this.sizeMinusOne = this.size - 1;
    }

    public void tick() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                boolean isAlive = this.world.isAlive(x, y);
                int aliveNeighbours = this.countAliveNeighbours(x, y);
                if (isAlive) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                        this.world.setAlive(x, y, false);
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        this.world.setAlive(x, y, true);
                    }
                }
            }
        }
        this.world.update();
    }

    private int countAliveNeighbours(int x, int y) {
        int count = 0;

        int neighbourXMinusOne = ((x -1) + this.size) & this.sizeMinusOne;
        int neighbourYMinusOne = ((y -1) + this.size) & this.sizeMinusOne;
        int neighbourXPlusOne = (x + 1) & this.sizeMinusOne;
        int neighbourYPlusOne = (y + 1) & this.sizeMinusOne;

        // y = -1

        // -1, -1
        if (this.world.isAlive(neighbourXMinusOne, neighbourYMinusOne)) {
            count++;
        }

        // 0, -1
        if (this.world.isAlive(x, neighbourYMinusOne)) {
            count++;
        }

        // +1, -1
        if (this.world.isAlive(neighbourXPlusOne, neighbourYMinusOne)) {
            count++;
        }

        // y = 0

        // -1, 0
        if (this.world.isAlive(neighbourXMinusOne, y)) {
            count++;
        }

        // 0, 0
        // Do not count the cell itself.

        // +1, 0
        if (this.world.isAlive(neighbourXPlusOne, y)) {
            count++;
        }

        // y = +1

        // -1, +1
        if (this.world.isAlive(neighbourXMinusOne, neighbourYPlusOne)) {
            count++;
        }

        // 0, +1
        if (this.world.isAlive(x, neighbourYPlusOne)) {
            count++;
        }

        // +1, +1
        if (this.world.isAlive(neighbourXPlusOne, neighbourYPlusOne)) {
            count++;
        }

        return count;
    }
}
