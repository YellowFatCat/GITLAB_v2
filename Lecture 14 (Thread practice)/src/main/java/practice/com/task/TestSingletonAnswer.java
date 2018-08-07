package practice.com.task;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;


public class TestSingletonAnswer {

    volatile static TestSingletonAnswer instance;

    public TestSingletonAnswer(){}

    public static TestSingletonAnswer getInstance(){
        if (instance == null) {
            synchronized (TestSingletonAnswer.class) {
                if (instance == null) {
                    instance = new TestSingletonAnswer();
                }
            }
        }
        return instance;
    }

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {
        final AtomicReference<TestSingletonAnswer> instance = new AtomicReference<>();

        Thread thread1 = createThread(() -> {
            instance.compareAndSet(null, TestSingletonAnswer.getInstance());
        });

        thread1.start();

        thread1.join();

        assertEquals(TestSingletonAnswer.getInstance(), instance.get());
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