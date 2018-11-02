package com.epam.executors;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.epam.executors.TestUtil.shutdownWithDelay;
import static org.junit.Assert.assertEquals;

public class FixedSizeThreadPoolTest {

    public static Callable<String> callable() {
        return () -> {
            Thread.sleep(1000);
            System.out.println("Callable is executed");
            return "Result";
        };
    }

    // TODO: Fix - one line should be relocated
    @Test
    public void testSizedThreadPool() {
        ExecutorService service = Executors.newFixedThreadPool(1);

        Future<String> res1 = service.submit(callable());
        Future<String> res2 = service.submit(callable());

        try {
            assertEquals("Result", res1.get());
            assertEquals("Result", res2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        shutdownWithDelay(service, 1000);

        Future<String> res3 = service.submit(callable());
        try {
            assertEquals("Result", res3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Exiting...");

        // wait while all tasks are completed
    }


    // TODO: Fix - change the size of the ThreadPool
    @Test
    public void testSizedThreadPool2() {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<String> res1 = service.submit(callable());
        Future<String> res2 = service.submit(callable());
        Future<String> res3 = service.submit(callable());

        shutdownWithDelay(service, 1500);

        try {
            assertEquals("Result", res1.get());
            assertEquals("Result", res2.get());
            assertEquals("Result", res3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Exiting...");

        // wait while all tasks are completed
    }
}
