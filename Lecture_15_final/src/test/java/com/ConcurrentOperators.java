package com;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ConcurrentOperators {

    private static Map<Integer, String> map = new HashMap();
    private static Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

    @Test
    public void testConcurrentOperationFailure() throws ExecutionException, InterruptedException {
        IntStream.range(0, 100).forEach(
                (i) -> {
                    map.put(i, "O");
                }
        );

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future futureA = executorService.submit(write(map));
        Future futureB = executorService.submit(write(map));

        futureA.get();
        futureB.get();

        executorService.submit(read(map));

        if(!executorService.awaitTermination(2, TimeUnit.SECONDS))
            executorService.shutdown();

        map.clear();
    }


    @Test
    public void testConcurrentOperationSuccess() throws ExecutionException, InterruptedException {
        IntStream.range(0, 100).forEach(
                (i) -> {
                    concurrentHashMap.put(i, "O");
                }
        );

        ExecutorService executorService = Executors.newFixedThreadPool(3);

//        Future futureA = executorService.submit(write(concurrentHashMap));
//        Future futureB = executorService.submit(write(concurrentHashMap));
        Future futureA = executorService.submit(modifiedWrite(concurrentHashMap));
        Future futureB = executorService.submit(modifiedWrite(concurrentHashMap));

        futureA.get();
        futureB.get();

        executorService.submit(read(concurrentHashMap));

        if(!executorService.awaitTermination(2, TimeUnit.SECONDS))
            executorService.shutdown();

        map.clear();
    }

    private Runnable read(Map<Integer, String> map) {
        return () -> map.forEach((key, value) -> System.out.println("key = " + key + " value = " + value));
    }

    private Runnable write(Map<Integer, String> map) {
        return () -> {
            IntStream.range(0, 100).forEach((i)->{
                    String value = map.get(new Random().nextInt(99));
                    map.put(i, value + value);
            });
        };
    }

    private Runnable modifiedWrite(Map<Integer, String> map) {
        return () -> {
            IntStream.range(0, 100).forEach((i)->{
                    String value = map.get(new Random().nextInt(99));
                    map.put(i, value + value);
            });
        };
    }


    // Concurrent collections: ConcurrentMap


    @Test
    public void testConcurrent() {
        final ConcurrentMap concurrentMap = new ConcurrentHashMap();


    }
}
