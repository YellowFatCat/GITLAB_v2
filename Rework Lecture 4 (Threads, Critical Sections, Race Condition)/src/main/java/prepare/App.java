package prepare;

/**
 * Thread name
 * Thread Group
 * Start a new thread
 * Sequence of Exec
 * sleep
 * join
 * Visual VM
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException {
         Thread thread = new Thread(() -> {
            try {
                System.out.println("Thread name: " + Thread.currentThread().getName());
                System.out.println("Thread group: " + Thread.currentThread().getThreadGroup());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();

        System.out.println("Main Thread: " + Thread.currentThread().getName());
        System.out.println("Main Thread: " + Thread.currentThread().getThreadGroup());
        Thread.sleep(10000);

        for(int i = 0; i < 10000000; i++){
            i++;
        }

    }
}
