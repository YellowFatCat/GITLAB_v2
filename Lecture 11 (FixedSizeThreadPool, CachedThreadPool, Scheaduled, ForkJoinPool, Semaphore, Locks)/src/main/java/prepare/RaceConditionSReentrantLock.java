package prepare;

import prepare.util.Util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RaceConditionSReentrantLock {


    private static class Cache {
        String name;
        ReentrantLock lock = new ReentrantLock();

        public String getName() {
            lock.lock();
            System.out.println("Read is locked");
            try {
                Util.threadSleep(1000);
                return name;
            } finally {
                System.out.println("Read is unlocked");
                lock.unlock();
            }
        }

        public void setName(String name) {
            lock.lock();
            System.out.println("Write is locked");
            try {
                Util.threadSleep(1000);
                this.name = name;
            } finally {
                System.out.println("Write is unlocked");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(3);
        Cache cache = new Cache();

        service.submit(() -> {
            cache.setName("Value 1000");
        });

        Util.threadSleep(100);

        service.submit(() -> {
            final String name = cache.getName();
            System.out.println("Got = " + name);
        });
        service.submit(() -> {
            final String name = cache.getName();
            System.out.println("Got = " + name);
        });


        putDown(service, 5);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
