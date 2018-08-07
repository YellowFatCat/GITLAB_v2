package practice.com.task;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestThreadWaitNotifyAnswer {

    AtomicInteger counter = new AtomicInteger(0);

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {
        Thread thread1 = createThread(() -> {
            synchronized (counter) {
                try {
                    counter.wait();
                    counter.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = createThread(() -> {
            synchronized (counter) {
                try {
                    counter.wait();
                    counter.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(100);

        // ensure WAITING
        // ensure WAITING
        assertEquals(thread1.getState(), Thread.State.WAITING);
        assertEquals(thread2.getState(), Thread.State.WAITING);

        // TODO: notify thread
        // TODO: notify thread
        synchronized (counter) {
            counter.notify();
            counter.notify();
        }

        // delay

        Thread.sleep(1000);
        assertEquals(counter.get(), 2);
    }

    private Thread createThread() {
        final Thread thread = new Thread();
        return thread;
    }

    private Thread createThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        return thread;
    }

}