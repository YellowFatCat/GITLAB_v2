package prepare;

/**
 * Pass Interface to start a new thread
 * extends Thread
 * thread.run()
 * When Deadlock is completed (all threads are finished) (mention busy ports in Daemon threads)
 *
 */
public class AppAnonimous
{

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread :" + Thread.currentThread().getName());
        }
    }


    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("CurrentThread=" + Thread.currentThread().getName());
        }
    }

    public static void main( String[] args ) {
        final Thread thread = new Thread(new MyRunnable());
        thread.start();
        //thread.start()


        final Thread thread2 = new Thread(new MyRunnable());
        thread2.start();

        Thread.currentThread().yield();
        Thread.currentThread().yield();
        Thread.currentThread().yield();
        Thread.currentThread().yield();
        System.out.println("MainThread=" + Thread.currentThread().getName());

        // extends Thread
        new MyThread().run();// !!!? output main
        new MyThread().start();


    }


}
