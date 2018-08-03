package com;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class RaceCollector {

    private static class Counter {
        volatile int counter = 0;

        public int inc() {
            counter++;
            if (counter % 100 == 0)
                System.out.println(counter);
            return counter;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Counter counter = new Counter();


        List<Future<Integer>> tasks = IntStream.range(0, 1000).mapToObj((i) ->
                executorService.submit(counter::inc)
        ).collect(toList());

        List<Integer> results = tasks.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).collect(Collectors.toList());

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            System.out.println("Counter : " + counter.counter);
            System.out.println("Result: " + Arrays.toString(results.toArray()));
        }, 2, TimeUnit.SECONDS);

        putDown(executorService);
        putDown(service);
    }

    private static void putDown(ExecutorService executorService) throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }


}
