package prepare;

import prepare.util.Util;

import java.util.Date;
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

/**
 * Inefficient sequent read operation with ReentrackLock (2 reads should be performed independently right away with blocking each other)
 */
public class RaceConditionSReentrantLock {


    private static class Cache {
        String name;
        ReentrantLock lock = new ReentrantLock();

        public String getName() {
            lock.lock();
            System.out.println("Read is locked");
            try {
                Util.threadSleep(3000);
                return name;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Read is unlocked");
                lock.unlock();
            }
            return null;
        }

        public void setName(String name) {
            lock.lock();
            System.out.println("Write is locked");
            try {
                Util.threadSleep(1000);
                this.name = name;
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            cache.setName("Value ### 3000 ### ");
        });

        Util.threadSleep(100);

        // blocked 2 reads !!! Avoid it
        service.submit(() -> {
            final String name = cache.getName();
            System.out.println("Got = " + name + " Time = " + new Date(System.currentTimeMillis()));
        });
        service.submit(() -> {
            final String name = cache.getName() + " Time = " + new Date((System.currentTimeMillis()));
            System.out.println("Got = " + name);
        });


        putDown(service, 5);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        service.shutdown();
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdown();
        }
    }
}
