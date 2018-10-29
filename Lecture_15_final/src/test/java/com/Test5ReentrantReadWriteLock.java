package com;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.Assert.assertArrayEquals;

public class Test5ReentrantReadWriteLock {


    private static class Cache {
        String name;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        public String getName() {
            // TODO: Fix it with lock
            try {
                Thread.sleep(500); // Do not change it
                return name;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // TODO: Fix it with lock
            }
            return null;
        }

        public void setName(String name) {
            // TODO: fix it with lock
            try {
                Thread.sleep(1000); // Do not change it
                this.name = name;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // TODO: fix it with lock
            }
        }
    }

    /**
     * Add Read and Write locks to make read/write operations synchronized
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testReentrantLock() throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(3);
        Cache cache = new Cache();

        service.submit(() -> {
            cache.setName("Catch me");
        });

        Thread.sleep(100); // Do not change it

        List<String> resultList = new ArrayList<String>();

        // parallel access
        System.out.println("Read in parallel now !!!");
        service.submit(() -> {
            String name = cache.getName(); // delayed!!!
            final String result = name + " Time = " + new Date((System.currentTimeMillis()));
            System.out.println("Got = " + result);
            resultList.add(name);

        });

        service.submit(() -> {
            String name = cache.getName(); // delayed!!!
            final String result = name + " Time = " + new Date((System.currentTimeMillis()));
            System.out.println("Got = " + result);
            resultList.add(name);
        });


        putDown(service, 4);
        System.out.println(Arrays.toString(resultList.toArray()));
        assertArrayEquals(Arrays.asList("Catch me", "Catch me").toArray(), resultList.toArray());
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
