package prepare;

import prepare.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerApp {

    private static final AtomicInteger atomicInt = new AtomicInteger(0);
    private static int sharedObject = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        long start = System.currentTimeMillis();
        Future future1 = executorService.submit(AtomicIntegerApp::incrementAtomic);
        Future future2 = executorService.submit(AtomicIntegerApp::incrementAtomic);
        Future future3 = executorService.submit(AtomicIntegerApp::incrementAtomic);
        Future future4 = executorService.submit(AtomicIntegerApp::incrementAtomic);
        Future future5 = executorService.submit(AtomicIntegerApp::incrementAtomic);

        future1.get();
        future2.get();
        future3.get();
        future4.get();
        future5.get();

        long end = System.currentTimeMillis();
        System.out.println("Atomic Time consumed : " + (end - start));

        // start shared object test
        start = System.currentTimeMillis();
        future1 = executorService.submit(AtomicIntegerApp::incrementSharedObject);
        future2 = executorService.submit(AtomicIntegerApp::incrementSharedObject);
        future3 = executorService.submit(AtomicIntegerApp::incrementSharedObject);
        future4 = executorService.submit(AtomicIntegerApp::incrementSharedObject);
        future5 = executorService.submit(AtomicIntegerApp::incrementSharedObject);

        future1.get();
        future2.get();
        future3.get();
        future4.get();
        future5.get();

        end = System.currentTimeMillis();
        System.out.println("Shared Object Time consumed : " + (end - start));

        Util.shutdown(executorService, true);
    }


    private static void incrementSharedObject() {
        IntStream.range(0, 5000).forEach((i) -> {
            sharedObject++;
            if (i % 100 == 0)
                System.out.println(sharedObject);
        });
        System.out.println("Shared Object result: " + sharedObject);
    }

    private static void incrementAtomic() {
        IntStream.range(0, 5000).forEach((i) -> {
            atomicInt.incrementAndGet();
            if (i % 100 == 0)
                System.out.println(atomicInt.get());
        });
        System.out.println("Atomic result: " + atomicInt.get());
    }
}
