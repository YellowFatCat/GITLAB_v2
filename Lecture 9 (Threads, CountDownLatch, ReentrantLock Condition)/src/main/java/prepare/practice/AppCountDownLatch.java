package prepare.practice;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AppCountDownLatch {

    public static class WorkThread implements Runnable {
        final CountDownLatch startLatch;
        final CountDownLatch executeLatch;

        public WorkThread(CountDownLatch startLatch, CountDownLatch executeLatch) {
            this.startLatch = startLatch;
            this.executeLatch = executeLatch;
        }

        @Override
        public void run() {
//            System.out.println("Thread : " + Thread.currentThread().getName());
            try {
                executeLatch.await();
                executeJob();
                startLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void executeJob(){
            System.out.println("Thread:" + Thread.currentThread().getName() + " Completed.");
            try {
                Thread.currentThread().sleep(100 + new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startLatch = new CountDownLatch(5);
        final CountDownLatch executeLatch = new CountDownLatch(1);

        IntStream.range(0, 5).forEach((i) -> {
                    new Thread(new WorkThread(startLatch, executeLatch)).start();
                }
        );

        executeLatch.countDown();
        startLatch.await();
        System.out.println("All threads executed. Continue execution.");
    }
}
