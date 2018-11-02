package com.epam.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Basic Runnable interface
//        executor.scheduleAtFixedRate(ScheduledThreadPool::healthCheckRunnable, 4, 1, TimeUnit.SECONDS);

        // Callable as a method reference lambda (one of lambda representations)
//        ScheduledFuture<String> result = executor.schedule(ScheduledThreadPool::healthCheck, 1, TimeUnit.SECONDS);
//        System.out.println(result.get());

        // Callable as a Lambda of Lambda
//        ScheduledFuture<Callable<String>> result = executor.schedule(ScheduledThreadPool::healthCheckLambda, 1, TimeUnit.SECONDS);
//        System.out.println(result.get().call());

        // Callable as a Lambda
//       ScheduledFuture<String> result = executor.schedule(healthCheckLambda(), 1, TimeUnit.SECONDS);
//        System.out.println(result.get());

    }

    private static String healthCheck() {
        return "Result";
    }

    private static Callable<String> healthCheckLambda() {
        return () -> {
            return "Result";
        };
    }

    private static void healthCheckRunnable() {
        System.out.println("Time: " + System.currentTimeMillis());
    }
}
