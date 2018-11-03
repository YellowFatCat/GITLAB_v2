package prepare.queues.blocking;

import prepare.queues.blocking.Cook;
import prepare.queues.blocking.Waiter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html
 * SyncQueueSize is always = 0 !!!
 */
public class SyncQueueEx {


    public static void main(String... args) {
        BlockingQueue<String> windows = new SynchronousQueue<>();
        Cook cook = new Cook(windows);
        Waiter waiter = new Waiter(windows);

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(cook);
        service.execute(waiter);
//        service.execute(waiter);

        service.shutdown();
    }
}
