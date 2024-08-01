package de.hhn.seb.kprog;

public class Rules implements IRules {
    private final World world;

    public Rules(World world) {
        this.world = world;
    }

    @Override
    public void tick() {
        for (int y = 0; y < this.world.size; y++) {
            for (int x = 0; x < this.world.size; x++) {
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

                int neighbourX = ((x + deltaX) + this.world.size) % this.world.size;
                int neighbourY = ((y + deltaY) + this.world.size) % this.world.size;
                if (this.world.isAlive(neighbourX, neighbourY)) {
                    count++;
                }
            }
        }
        return count;
    }
}
