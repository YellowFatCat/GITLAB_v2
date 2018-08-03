package prepare;

import java.util.Stack;

public class SyncStack<T extends Object> {

    private final Stack<T> stack = new Stack<>();
    private final static int CAPACITY = 5;

    Object readLock = new Object();
    Object writeLock = new Object();

    public T pop() throws InterruptedException {
        synchronized (readLock) {
            try {
                if (stack.size() == 0) {
                    readLock.wait();
                }
                return stack.pop();
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
                stack.push(obj);
            } finally {
                writeLock.notifyAll();
            }
        }
    }
}
