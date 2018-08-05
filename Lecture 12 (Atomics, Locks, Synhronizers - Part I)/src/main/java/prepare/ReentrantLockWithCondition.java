package prepare;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWithCondition {
 
    Queue<String> fifoQueue = new ArrayDeque<>();
    int CAPACITY = 5;
 
    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();
 
    public void pushToStack(String item) throws InterruptedException {
        lock.lock();
        try {
            while(fifoQueue.size() == CAPACITY){
                stackFullCondition.await();
            }
            fifoQueue.offer(item);
            stackEmptyCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
 
    public String popFromStack() throws InterruptedException {
        lock.lock();
        try {
            while(fifoQueue.size() == 0){
                stackEmptyCondition.await();
            }
            return fifoQueue.poll();
        } finally {
            stackFullCondition.signalAll();
            lock.unlock();
        }
    }
}