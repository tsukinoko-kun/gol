package de.hhn.seb.kprog;

public interface IRules {
    /**
     * Game of Life rules are applied to each cell, generating the next generation.
     */
    void tick();
}
