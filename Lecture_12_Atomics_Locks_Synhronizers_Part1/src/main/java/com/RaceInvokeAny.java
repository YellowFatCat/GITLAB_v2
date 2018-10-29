package com;

import prepare.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class RaceInvokeAny {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

        ScheduledFuture future = service.scheduleAtFixedRate(() -> {
            System.out.print("Ping ... ");
        }, 1, 1, TimeUnit.SECONDS);

        service.schedule(() -> {
            // executed with 1 sec lag between tasks
//            service.scheduleWithFixedDelay(runnable("Executed delayed for 200 ms at 1 sec rate between tasks", 200), 1, 1, TimeUnit.SECONDS);
            System.out.println("Cancel periodic Task Executor");
            future.cancel(true);
            putDown(service);
        }, 5, TimeUnit.SECONDS);

    }

    private static void putDown(ExecutorService executorService) {
        executorService.shutdown();
    }
}
