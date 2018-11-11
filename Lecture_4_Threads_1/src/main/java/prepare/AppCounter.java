package prepare;



// Create custom Manageable Thread to handle interface (reference)
// Shared resource between threads
// Before execute ask what is expected output and total sum

import prepare.util.Util;

public class AppCounter {

    public static class CounterThread implements Runnable {

        private final String name;
        private final Counter counter;
        private final int total;

        public CounterThread(final String name, final Counter counter, int total) {
            this.name = name;
            this.counter = counter;
            this.total = total;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            int i = 0;
            while (i <= total) {
                i++;

                counter.inc();
                System.out.println(name + "; counter = " + counter.getCounter());

                Util.threadSleep(500);
            }
        }
    }


    public static class Counter {

        private Integer counter;

        public Counter(Integer counter) {
            this.counter = counter;
        }

        public void inc() {
            counter++;
        }

        public Integer getCounter(){
            return counter;
        }
    }



    public static void main( String[] args ) throws InterruptedException {

        Counter counter = new Counter(0);
        Thread thread1 = new Thread(new CounterThread("Thread - 1", counter, 10));
        Thread thread2 = new Thread(new CounterThread("Thread - 2", counter, 10));

        thread1.start();
        thread2.start();
    }
}
