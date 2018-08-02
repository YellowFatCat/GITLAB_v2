package prepare;

import prepare.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RaceConditionFixedThreadPool {

    private static class Counter {
        int count = 0;

        public Counter(int i) {
            count = 0;
        }

        public void inc() {
            count++;
        }

        public void dec() {
            count--;
        }
    }

    public static class WorkerThread<Integer> implements Callable<java.lang.Integer> {


        private final Counter counter;

        public WorkerThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public java.lang.Integer call() {
            Util.threadSleep(new Random().nextInt(10));

            // race condition
            counter.inc();

            System.out.println("Worker Thread completed");
            return java.lang.Integer.valueOf(1);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(3);

        Counter counter = new Counter(0);
        WorkerThread<Integer> callable = new WorkerThread<Integer>(counter);


        List<Future<Integer>> results = IntStream.range(0, 1000).mapToObj((i) ->
                service.submit(callable)
        ).collect(Collectors.toList());

        service.shutdown();
        service.awaitTermination(10000, TimeUnit.SECONDS);

        //
        System.out.println("Counter = " + counter.count);
        System.out.println("The process is started and completed");
    }
}
