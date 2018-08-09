package com;

import com.model.Cook;
import com.model.Waiter;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;

/**
 */
public class Test4CountdownLatchTest {


    /**
     * ####################################################################
     * //TODO: Add ContDownLatch to complete application when 10 dishes are served
     * ####################################################################
     */
    @Test
    public void testWorkThread() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        CountDownLatch dishesServied = new CountDownLatch(10);
        CountDownLatch dishesPrepared = new CountDownLatch(10);

        Waiter waiter = new Waiter(blockingQueue, dishesServied);
        Cook cook = new Cook(blockingQueue, dishesPrepared);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            // Add Waiter thread
        });

        executorService.submit(() -> {
            // Add Cook thread
        });

        // TODO: Add more threads if necessary

        // TODO: Fix it - complete after all dishes are being served
        assertEquals(0, dishesPrepared.getCount());
        assertEquals(0, dishesPrepared.getCount());

        // TODO: Complete test and executor
    }


    /**
     * ####################################################################
     * //TODO: Add CompletableFuture
     * ####################################################################
     */
    @Test
    public void testCompletableFuture() throws InterruptedException, ExecutionException {

        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        CountDownLatch dishesServied = new CountDownLatch(10);
        CountDownLatch dishesPrepared = new CountDownLatch(10);

        Waiter waiter = new Waiter(blockingQueue, dishesServied);
        Cook cook = new Cook(blockingQueue, dishesPrepared);


        CompletableFuture futureCook = CompletableFuture.runAsync(() ->
                {
                    // TODO: Run Cook
                }
        );
        CompletableFuture futureWaiter = CompletableFuture.runAsync(() -> {
                    // TODO: Run waiter
                }
        );

        CompletableFuture completableFuture = CompletableFuture.allOf(futureCook, futureWaiter);
        // TODO: completableFuture.(); ??? - wait or receive result to proceeed

        assertEquals(dishesServied.getCount(), 0);
        assertEquals(dishesPrepared.getCount(), 0);
        System.out.println("Completed!");
    }
}
