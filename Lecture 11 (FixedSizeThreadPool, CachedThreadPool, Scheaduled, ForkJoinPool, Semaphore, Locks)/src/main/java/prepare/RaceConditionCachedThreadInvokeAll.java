package prepare;

import prepare.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RaceConditionCachedThreadInvokeAll {

    private static class Counter {
        int count = 0;

        public Counter(int i) {
            count = 0;
        }

        public Integer inc() {
            return ++count;
        }

        public Integer dec() {
            return --count;
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();

        Counter counter = new Counter(0);

        List<Callable<Integer>> callable = new ArrayList();

        IntStream.range(0, 1000).forEach((i) -> {
                    callable.add(counter::inc);
                }
        );

        service.invokeAll(callable);
        //service.awaitTermination(10000, TimeUnit.SECONDS);

        Util.threadSleep(1000);

        // !!! If threads are started then we need to enforce it and shut it down
        //
        System.out.println("Counter = " + counter.count);
        System.out.println("The process is started and completed");

        putDown(service, 2);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
