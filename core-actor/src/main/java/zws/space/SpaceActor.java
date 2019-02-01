package zws.space;

import zws.actor.method.MethodActor;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class SpaceActor extends MethodActor implements Space {

    private ConcurrentMap<Integer, Cell> cellMap;

    @Override
    public void tick(int frameId) {
        try {
            // 各自运算
            CountDownLatch latch = new CountDownLatch(cellMap.size());
            for (Cell cell : cellMap.values()) {
                cell.tick(0, latch);
            }
            latch.await();
            // 同步状态
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
    }

}
