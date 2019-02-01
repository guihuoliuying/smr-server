package zws.space;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import zws.actor.method.MethodActor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class CellActor extends MethodActor implements Cell {

    private int width;
    private int height;
    private Grid[][] grids;

    public CellActor(int width, int height) {
        this.width = width;
        this.height = height;
        grids = new Grid[width][height];

        int id = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grids[i][j] = new Grid(++id, i, j, id, new Object());
            }
        }
    }

    public CellActor() {
        this(1024, 1024);
    }

    @Override
    public void tick(int frameId, CountDownLatch latch) {
        latch.countDown();
    }

    @Benchmark
    public void loop() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // no op
                Grid grid = grids[i][j];
                int gridId = grid.gridId;
                int x = grid.x;
                int y = grid.y;
                long mask = grid.mask;
                Object placeholder = grid.placeholder;
            }
        }
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(CellActor.class.getSimpleName())
                .forks(1)
                .warmupBatchSize(10)
                .warmupIterations(10)
                .measurementBatchSize(10)
                .measurementIterations(10)
                .jvmArgsAppend("-Xmx2048m")
                .build();

        try {
            new Runner(opt).run();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }
}
