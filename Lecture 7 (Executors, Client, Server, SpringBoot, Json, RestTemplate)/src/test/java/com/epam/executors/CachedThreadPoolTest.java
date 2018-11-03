package com.epam.executors;

import com.epam.threads.util.Util;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.epam.executors.TestUtil.shutdownWithDelay;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CachedThreadPoolTest {

    public static Callable<String> callable(String name) {
        return () -> {
            Util.sleep(100);
            System.out.println("Callable is executed");
            return "Result " + name;
        };
    }

    private static Runnable runnable(final String name) {
        return () -> {
            Util.sleep(100);
            System.out.println("Runnable is executed: " + name);
        };
    }


    // TODO: Fix the Future usage in a test
    @Test
    public void testCallableAndFuture() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        Future<String> result1 = service.submit(callable("Thread 1"));
        Future<String> result2 = service.submit(callable("Thread 2"));
        Future<String> result3 = service.submit(callable("Thread 3"));


        assertEquals("Result Thread 1", result1);
        assertEquals("Result Thread 2", result2);
        assertEquals("Result Thread 3", result3);

        System.out.println("Exiting...");

        shutdownWithDelay(service, 1000);
    }


    // TODO: Fix the test. One line is unnecessary
    @Test
    public void testRunnable() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        Future<?> result1 = service.submit(runnable("Thread 1"));
        service.shutdown();
        Future<?> result2 = service.submit(runnable("Thread 2"));

        assertNotNull(result1);
        assertNull(result1.get());

        assertNotNull(result2);
        assertNull(result2.get());

        System.out.println("Exiting...");

        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}
