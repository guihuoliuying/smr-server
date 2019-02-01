package zws.space;

import java.util.concurrent.CountDownLatch;

public interface Cell {

    void tick(int frameId, CountDownLatch latch);

}
