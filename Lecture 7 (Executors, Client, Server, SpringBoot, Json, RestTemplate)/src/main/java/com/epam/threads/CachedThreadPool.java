package com.epam.threads;

import com.epam.threads.util.Util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CachedThreadPool {
    CachedThreadPool() {
    }

    public static Callable<String> callable(String name) {
        return () -> {
            Util.sleep(1000);
            System.out.println("Callable is executed");
            return "Result: " + name;
        };
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();


        Future<String> result1 = service.submit(callable("Thread 1"));
        Future<String> result2 = service.submit(callable("Thread 2"));
        Future<String> result3 = service.submit(callable("Thread 3"));

        System.out.println(result1.get());
        System.out.println(result2.get());
        System.out.println(result3.get());


        System.out.println("Exiting...");

        shutdownWithDelay(service, 2);
    }

    private static void shutdownWithDelay(ExecutorService service, int timeout) {

        try {
            if (!service.awaitTermination(timeout, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            service.shutdownNow();
        }
    }
}
