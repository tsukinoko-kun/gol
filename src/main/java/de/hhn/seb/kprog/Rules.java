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
                if (isAlive && (aliveNeighbours < 2 || aliveNeighbours > 3)) {
                    this.world.setAlive(x, y, false);
                } else if (!isAlive && aliveNeighbours == 3) {
                    this.world.setAlive(x, y, true);
                }
            }
        }
        this.world.update();
    }

    private int countAliveNeighbours(int x, int y) {
        int count = 0;
        for (int deltaX = -1; deltaX <= 1; deltaX++) {
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                if (deltaX == 0 && deltaY == 0) {
                    continue;
                }

                int neighbourX = ((x + deltaX) + this.size) & this.sizeMinusOne;
                int neighbourY = ((y + deltaY) + this.size) & this.sizeMinusOne;
                if (this.world.isAlive(neighbourX, neighbourY)) {
                    count++;
                }
            }
        }
        return count;
    }
}
