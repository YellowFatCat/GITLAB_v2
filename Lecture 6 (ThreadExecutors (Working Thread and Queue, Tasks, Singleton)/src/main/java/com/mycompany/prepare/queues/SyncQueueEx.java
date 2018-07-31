package com.epam.LABSpringBoot.prepare.queues;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

public class SyncQueueEx {


    public static void main(String... args) {
        SynchronousQueue<String> windows = new SynchronousQueue<>();
        Cook cook = new Cook(windows);
        Waiter waiter = new Waiter(windows);

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(cook);
        service.execute(waiter);
        service.execute(waiter);
    }
}
