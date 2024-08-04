package de.hhn.seb.kprog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesTest {
    private World world;

    @BeforeEach
    public void setUp() {
        this.world = new World(8);
    }

    @Test
    public void world() {
        this.world.setState(new boolean[][]{
                {false, true, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {true, true, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        });

        this.worldShouldBe(new boolean[][]{
                {false, true, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {true, true, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        });

        assertEquals(1, this.world.getAliveNeighbours(3, 3));
    }

    @Test
    public void glider() {
        this.world.setState(new boolean[][]{
                {false, true, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {true, true, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        });

        this.world.tick();

        this.worldShouldBe(new boolean[][]{
                {false, false, false, false, false, false, false, false},
                {true, false, true, false, false, false, false, false},
                {false, true, true, false, false, false, false, false},
                {false, true, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        });

        this.world.tick();

        this.worldShouldBe(new boolean[][]{
                {false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {true, false, true, false, false, false, false, false},
                {false, true, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        });

        this.world.tick();

        this.worldShouldBe(new boolean[][]{
                {false, false, false, false, false, false, false, false},
                {false, true, false, false, false, false, false, false},
                {false, false, true, true, false, false, false, false},
                {false, true, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        });
    }

    @Test
    public void torusWorld() {
        this.world.setState(new boolean[][]{
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, true},
                {false, false, false, false, false, true, true, true},
        });

        this.world.tick();

        this.worldShouldBe(new boolean[][]{
                {false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, true, false, true},
                {false, false, false, false, false, false, true, true},
        });

        this.world.tick();

        this.worldShouldBe(new boolean[][]{
                {false, false, false, false, false, false, true, true},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, true},
                {false, false, false, false, false, true, false, true},
        });

        this.world.tick();

        this.worldShouldBe(new boolean[][]{
                {false, false, false, false, false, false, true, true},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, true, false},
                {true, false, false, false, false, false, false, true},
        });
    }

    private void worldShouldBe(boolean[][] expectedState) {
        for (int y = 0; y < this.world.size; y++) {
            for (int x = 0; x < this.world.size; x++) {
                if (expectedState[y][x]) {
                    assertTrue(this.world.isAlive(x, y), "Cell at (" + x + ", " + y + ") should be alive");
                } else {
                    assertFalse(this.world.isAlive(x, y), "Cell at (" + x + ", " + y + ") should be dead");
                }
            }
        }
    }
}