package com.epam.LABSpringBoot.queue;

import java.util.concurrent.*;

public class AppRest {
    public static void main(String[] args) {

        SynchronousQueue<String> windows = new SynchronousQueue<>();

        Cook cook = new Cook(windows);
        Waiter waiter = new Waiter(windows);

        Executor executor = Executors.newCachedThreadPool();

        executor.execute(cook);

        executor.execute(waiter);
        executor.execute(waiter);
    }
}
