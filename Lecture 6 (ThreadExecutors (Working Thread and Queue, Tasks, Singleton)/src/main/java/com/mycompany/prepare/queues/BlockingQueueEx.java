package com.epam.LABSpringBoot.prepare.queues;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueEx {


    public static void main(String... args) {
        LinkedBlockingQueue<String> windows = new LinkedBlockingQueue<>();
        Cook cook = new Cook(windows);
        Waiter waiter = new Waiter(windows);

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(cook);
        service.execute(waiter);
        service.execute(waiter);
    }
}
