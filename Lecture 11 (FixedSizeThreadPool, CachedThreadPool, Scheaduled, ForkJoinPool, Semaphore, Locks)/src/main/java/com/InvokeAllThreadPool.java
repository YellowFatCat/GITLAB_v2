package com;

import prepare.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InvokeAllThreadPool {
    InvokeAllThreadPool() {
    }


    public static Callable<String> callable(String name) throws ExecutionException {
        return () -> {
            try {
//                Util.threadSleep(1000);
                System.out.println("Callable is executed");
                return "Result: " + name;
            } catch (Throwable ex) {
                throw new ExecutionException(ex);
            }
        };
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // Scenario 1
//        Future<String> result1 = service.submit(callable("Thread 1"));
//        Future<String> result2 = service.submit(callable("Thread 2"));
//        Future<String> result3 = service.submit(callable("Thread 3"));
//
//        System.out.println(result1.get());
//        System.out.println(result2.get());
//        System.out.println(result3.get());

        // Scenario 2
        List<Callable<String>> tasks = Arrays.asList(
                callable("Task 1"),
                callable("Task 2"),
                callable("Task 3")
        );

        List<Future<String>> results = service.invokeAll(tasks);

        new Thread(()->{
            try {
                Thread.sleep(100);
                service.shutdownNow();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        results.stream().map(stringFuture -> {
            try {
                return stringFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (Throwable e){
                e.printStackTrace();
            }
            return null;
        }).forEach(System.out::println);

        System.out.println("Exiting...");

        shutdownWithDelay(service, 0);
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
