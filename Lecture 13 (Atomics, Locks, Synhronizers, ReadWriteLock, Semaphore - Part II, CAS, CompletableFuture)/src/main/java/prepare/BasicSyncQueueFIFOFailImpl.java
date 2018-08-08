package prepare;

import java.util.ArrayDeque;
import java.util.Queue;

public class BasicSyncQueueFIFOFailImpl<T extends Object> {

    private final Queue<T> stack = new ArrayDeque<>();
    private final static int CAPACITY = 10;

    Object readLock = new Object();
    Object writeLock = new Object();

    public T pop() throws InterruptedException {
        synchronized (readLock) {
            try {
                if (stack.size() == 0) {
                    readLock.wait();
                }
                return stack.poll();
            } finally {
                readLock.notifyAll();
            }
        }
    }

    public void push(T obj) throws InterruptedException {
        synchronized (writeLock) {
            try {
                if (stack.size() == CAPACITY) {
                    writeLock.wait();
                }
                stack.offer(obj);
            } finally {
                writeLock.notifyAll();
            }
        }
    }
}
