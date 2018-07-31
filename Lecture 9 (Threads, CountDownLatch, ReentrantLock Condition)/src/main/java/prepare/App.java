package prepare;



// Create custom Manageable Thread to handle interface (reference)
// Shared resource between threads
// Before execute ask what is expected output and total sum

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class App {



    public static class WorkerThread implements Runnable {

        CountDownLatch startLatch;
        CountDownLatch doneLatch;

        public WorkerThread(CountDownLatch startLatch, CountDownLatch doneLatch) {
            this.startLatch = startLatch;
            this.doneLatch = doneLatch;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                // do some valuable job
                execute();
                System.out.println("Thread has completed: " + Thread.currentThread().getName());
//                Thread.sleep(100);
                doneLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            Thread.sleep(1000 + new Random().nextInt(1000));
        }
    }


    public static void main( String[] args ) throws InterruptedException {

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new WorkerThread(startLatch, doneLatch)).start();
        }

        startLatch.countDown();
        System.out.println("Awaiting jobs...");
        doneLatch.await();
        System.out.println("Start new Job when all workers has been completed");
    }
}
