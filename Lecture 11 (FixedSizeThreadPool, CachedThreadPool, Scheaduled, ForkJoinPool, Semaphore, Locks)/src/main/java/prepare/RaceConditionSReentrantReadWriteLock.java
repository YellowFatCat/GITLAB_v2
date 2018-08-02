package prepare;

import prepare.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RaceConditionSReentrantReadWriteLock {


    private static class Cache {
        String name;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public String getName() {
            lock.readLock().lock();
            System.out.println("Read is locked");
            try {
                Util.threadSleep(1000);
                return name;
            } finally {
                System.out.println("Read is unlocked");
                lock.readLock().unlock();
            }
        }

        public void setName(String name) {
            lock.writeLock().lock();
            System.out.println("Write is locked");
            try {
                Util.threadSleep(1000);
                this.name = name;
            } finally {
                System.out.println("Write is unlocked");
                lock.writeLock().unlock();
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

        // parallel access
        service.submit(() -> {
            final String name = cache.getName();
            System.out.println("Got = " + name);
        });
        service.submit(() -> {
            final String name = cache.getName();
            System.out.println("Got = " + name);
        });


        putDown(service, 4);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
