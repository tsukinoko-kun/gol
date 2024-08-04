package de.hhn.seb.kprog;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class RulesBenchmark {
    private World world;

    @Param({"64", "512"})
    private int n;

    @Setup(Level.Trial)
    public void setup() {
        this.world = new World(this.n);

        this.world.tick();
        this.world.tick();
        this.world.tick();
        this.world.tick();
        this.world.tick();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5, time = 3)
    @Measurement(iterations = 5, time = 3)
    @Fork(value = 3)
    public void tick() {
        this.world.tick();
    }
}
