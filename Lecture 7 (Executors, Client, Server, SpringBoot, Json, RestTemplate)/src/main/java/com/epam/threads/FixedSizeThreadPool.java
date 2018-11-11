package com.epam.threads;

import com.epam.threads.util.Util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedSizeThreadPool {
    FixedSizeThreadPool() {
    }

    public static Callable<String> callable() {
        return () -> {
            Util.sleep(1000);
            System.out.println("Callable is executed");
            return "Result";
        };
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.submit(callable());
        service.submit(callable());
        service.submit(callable());
        System.out.println("Exiting...");

        // wait while all tasks are completed
        shutdownWithDelay(service, 2);
    }

    private static void shutdownWithDelay(ExecutorService service, int timeout) {

        try {
            if (!service.awaitTermination(timeout, TimeUnit.SECONDS))
            {
                System.out.println("Cannot shutdown");
            }
                //service.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
            service.shutdownNow();
        }
    }
}
