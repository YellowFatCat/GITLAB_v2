package prepare;

import java.util.stream.IntStream;

/**
 * Agenda:
 * <p>
 * Thread name
 * Thread Group
 * Start a new thread
 * run() method, direct call
 * Sequence of Exec
 * join
 * yield
 * sleep
 * wait
 * Visual VM or Mission Control (ManagedOperations)
 * <p>
 * Topics:
 * Thread Stack, Heap
 * Daemon thread
 *
 * @link {#AppSimpleJMXAgent} - managed operations
 *
 */
public class App {
    public App() {
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.testOne();
        app.testTwo();
        app.testThree();
        app.holdOn(1000);
    }

    private static class Counter {
        public int count;

        public void printResult() {
            System.out.println("Counter = " + count);
        }

        public void printResult(String msg) {
            System.out.println(msg + "; Counter = " + count);
        }
    }

    private static class MyThread implements Runnable {
        private final int MAX_NUMBER;
        public Counter counter;

        public MyThread(Counter counter) {
            this(counter, 10000);
        }

        public MyThread(Counter counter, final int maxNumber) {
            this.counter = counter;
            MAX_NUMBER = maxNumber;
        }

        @Override
        public void run() {
            IntStream.range(0, MAX_NUMBER).forEach(x -> counter.count++);
            printProfile();
        }

        public void printProfile() {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
            System.out.println("Thread Group: " + Thread.currentThread().getThreadGroup());
        }
    }

    /**
     * ###########################################################################################
     * ### Create shared object 'Counter' and place variable shared between two or more threads
     * ###########################################################################################
     *
     *
     */

    /**
     * Case 1: name, group, join()
     *
     * @throws InterruptedException
     */
    private void testOne() throws InterruptedException {

        final Counter counter = new Counter();

        MyThread myThread1 = new MyThread(counter);
        MyThread myThread2 = new MyThread(counter);

        ThreadGroup group = new ThreadGroup("Sergey Thread Group");

        Thread thread1 = new Thread(group, myThread1);
        Thread thread2 = new Thread(group, myThread2);

        // set name and group
        thread1.setName("MyThread Sergey 1");
        thread2.setName("MyThread Sergey 2");

        thread1.getThreadGroup();

        counter.printResult();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        counter.printResult("Join applied");

        // executed in main thread
        myThread1.printProfile();
        myThread2.printProfile();
    }

    /**
     * Use Case 2: yield(), sleep(), isAlive()
     *
     * @throws InterruptedException
     */
    private void testTwo() throws InterruptedException {

        final Counter counter = new Counter();

        MyThread myThread1 = new MyThread(counter);
        MyThread myThread2 = new MyThread(counter);

        ThreadGroup group = new ThreadGroup("Sergey Thread Group");

        Thread thread1 = new Thread(group, myThread1);
        Thread thread2 = new Thread(group, myThread2);

        // set name and group
        thread1.setName("MyThread Sergey 1");
        thread2.setName("MyThread Sergey 2");

        // sleep for a moment
        thread1.start();
        // check state of the thread
        isThreadAlive(thread1);
        thread1.sleep(1000);
        // sequence threads
        thread1.join();


        thread2.start();
        thread2.join();

        // check state of the thread
        isThreadAlive(thread1);
        isThreadAlive(thread2);

        counter.printResult("Sleep is applied to thread 1");
    }

    private static void isThreadAlive(Thread thread) {
        System.out.println("Thread " + thread.getName() + " isAlive() = " + thread.isAlive());
    }


    /**
     * Use Case 2: wait() (vs sleep), Daemon, Thread State, Visual VM, Mission Control
     *
     * @throws InterruptedException
     */
    private void testThree() throws InterruptedException {

        final Counter counter = new Counter();

        MyThread myThread1 = new MyThread(counter);
        MyThread myThread2 = new MyThread(counter);

        ThreadGroup group = new ThreadGroup("Sergey Thread Group");

        Thread thread1 = new Thread(group, myThread1);

        // set name and group
        thread1.setName("MyThread Sergey 1");

        // sleep for a moment
        thread1.start();
        // check state of the thread
        isThreadAlive(thread1);
        // !!! uncomment this
        //  thread1.wait(1000);
        // sequence threads
        thread1.join();

        // Create a new Deamon Thread and put it on hold
        Thread daemon = new Thread(() -> {
            IntStream.range(0, 10).forEach((x) -> {
                try {
                    System.out.println("Daemon count: " + x);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        daemon.setDaemon(true); // daemon threads are completing automatically gracefully after all other non-daemon threads completed
        daemon.start();

        Thread.sleep(1000);
        System.out.println("Main thread exited");
    }


    public void holdOn(final int N) throws InterruptedException {
        System.out.println("Wait sec: " + N);
        IntStream.range(0, N).forEach((x) -> {
            try {
                System.out.println("Sleep: " + (N - x) + " sec more");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
