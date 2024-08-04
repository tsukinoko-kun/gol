package de.hhn.seb.kprog;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class RulesBenchmark {
    private Rules rules;

    @Param({"64", "512"})
    private int n;

    @Setup(Level.Trial)
    public void setup() {
        World world = new World(this.n);
        this.rules = new Rules(world);

        this.rules.tick();
        this.rules.tick();
        this.rules.tick();
        this.rules.tick();
        this.rules.tick();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5, time = 3)
    @Measurement(iterations = 5, time = 3)
    @Fork(value = 3)
    public void tick() {
        this.rules.tick();
    }
}
