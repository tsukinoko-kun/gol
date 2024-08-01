package de.hhn.seb.kprog;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class RulesBenchmark {
    private Rules rules;

    @Param({"16", "32", "64"})
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
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 2)
    @Measurement(iterations = 5, time = 2)
    @Fork(value = 2)
    public void tick() {
        this.rules.tick();
    }
}
