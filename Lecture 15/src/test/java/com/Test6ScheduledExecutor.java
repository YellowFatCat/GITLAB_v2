package com;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test6ScheduledExecutor {

    @Test
    public void testSheduledTask() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        System.out.println(
                "################################\n" +
                "############# TASK 1 ############\n" +
                "#################################");

        List<String> pingList = new ArrayList<>();
        // TODO: Make ping with 10 signals using ''scheduleAtFixedRate''
        // ScheduledFuture future = service.scheduleAtFixedRate(() -> {...

        Thread.sleep(1000);
        assertEquals(10, pingList.size());


        System.out.println("\nTASK 1 Completed Successfully!\n\n" +
                "################################\n" +
                "############# TASK 2 ############\n" +
                "#################################");

        // TODO: Execute TWO TIMES 'runnable' task delayed for 200 ms at 1 sec rate between buzz words
        service.schedule(() -> {

            // TODO Execute delayed for 200 ms at 1 sec rate between tasks
            // TODO Execute: Test6ScheduledExecutor::runnable(BUZZ_WORD) two times
            // ScheduledFuture future = service.scheduleWithFixedDelay(...);


            // TODO: Change DELAY that BUZZ work appears 2 times
            final int DELAY = 1000;
            try {Thread.sleep(DELAY);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("Cancel periodic Task Executor");
            //  future.cancel(true); // Cancel
            putDown(service);
        }, 3, TimeUnit.SECONDS); // Do not change 'delay' here = 3 sec

        assertFalse(service.isTerminated());
        // WAIT
        Thread.sleep(7000);
        assertEquals(buzz.get(), 2); // Check it is executed twice
        assertTrue(service.isTerminated());
        System.out.println("Exit successfully");
    }

    final static String BUZZ_WORD = "Executed delayed for 200 ms at 1 sec rate between tasks";

    private static void runnable(String s) {
        if (!s.equals(BUZZ_WORD)) {
            assertEquals(s, BUZZ_WORD);
            throw new RuntimeException("Wrong key buzz word");
        }

        System.out.println("\tMUST BE EXECUTED TWO TIMES! Executed: " + buzz.incrementAndGet());
    }

    static AtomicInteger buzz = new AtomicInteger(0);

    private static void putDown(ExecutorService executorService) {
        executorService.shutdown();
    }
}
