package de.hhn.seb.kprog;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Semaphore;

/**
 * Measure the performance of the running application.
 * Don't change this class.
 */
public class Benchmark {
    public static final int MEASUREMENTS = 10;
    private final static Benchmark INSTANCE = new Benchmark();
    private final long[] times = new long[MEASUREMENTS];
    private final Semaphore mutex = new Semaphore(1);
    private String text = "Collecting measurements...";
    private int i = 0;
    private Instant start;

    private Benchmark() {
    }

    public static Benchmark getInstance() {
        return Benchmark.INSTANCE;
    }

    public void start() {
        this.mutex.acquireUninterruptibly();
        this.start = Instant.now();
    }

    public void stop() {
        {
            Instant end = Instant.now();
            Duration duration = Duration.between(this.start, end);
            long nanos = duration.toNanos();
            this.times[this.i] = nanos;
            this.i++;
        }

        if (this.i == MEASUREMENTS) {
            long[] sortedTimes = this.times.clone();
            java.util.Arrays.sort(sortedTimes);
            long nanos = sortedTimes[MEASUREMENTS / 2];
            if (nanos <= 1_000) {
                this.text = String.format("x̃ = %d ns", nanos);
            } else if (nanos <= 1_000_000) {
                long micros = nanos / 1000;
                this.text = String.format("x̃ = %d µs", micros);
            } else {
                long micros = nanos / 1000;
                long millis = micros / 1000;
                this.text = String.format("x̃ = %d ms", millis);
            }

            this.i = 0;
        }

        this.mutex.release();
    }

    public String getText() {
        return this.text;
    }
}
