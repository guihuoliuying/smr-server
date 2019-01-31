package zws.actor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GuavaCacheWithWeakValues {

    public static void main(String[] args) throws InterruptedException {

        Cache<Integer, Object> cache = CacheBuilder
                .newBuilder()
                .weakValues()
//                .softValues()
                .build();

        cache.put(1, new Object());

//        WeakReference<Object> ref = new WeakReference<Object>(new Object());
//        System.out.println(ref.get());
//        System.out.println(cache.size());


        GarbageCollectorMXBean mb = ManagementFactory.getGarbageCollectorMXBeans().get(0);
//        System.out.println(cache.getIfPresent(1));
//        printGC();
//        System.gc();
//        System.out.println("GC...");
//        printGC();
//        System.out.println(ref.get());
//        System.out.println(cache.getIfPresent(1));
//        System.out.println(cache.size());

        while (true) {
            for (int i = 0; i < 5120; i++) {
                int[] array = new int[256];
//                new Object();
            }
            System.out.println(cache.getIfPresent(1));
            printGC();
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    private static void printGC() {
        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
//        list.forEach((mxbean) -> {
//            System.out.println(mxbean.toString());
//        });
        for (GarbageCollectorMXBean mxbean : list) {
            System.out.println(mxbean.getName() + ":" + mxbean.getCollectionCount() + ":" + mxbean.getCollectionTime());
        }
    }

}
