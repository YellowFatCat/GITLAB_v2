package com;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

//        ScheduledFuture<?> result = executor.schedule(ScheduledThreadPool::healthCheck, 1, TimeUnit.SECONDS);
//        System.out.println((String)result.get());

        executor.scheduleAtFixedRate(ScheduledThreadPool::healthCheckRunnable, 1, 1, TimeUnit.SECONDS);


    }

    private static Callable<String> healthCheck() {
        return () -> {
            return "Result";
        };
    }

    private static void healthCheckRunnable() {
        {
            System.out.println("Time: " + System.currentTimeMillis());
        };
    }
}
