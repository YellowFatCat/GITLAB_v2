package com;

import org.junit.Test;
import prepare.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertEquals;

public class ReentrantLockSignalTest {

    private static final int OPERS = 10;

    public static class BlockingSyncQueue {
        private ReentrantLock lock = new ReentrantLock();
        private Condition readCondition = lock.newCondition();
        private Condition writeCondition = lock.newCondition();

        private String msg;

        public String readMsg() {
            lock.lock();
            try {
                Util.sleep(10);
                while (msg == null) {
                    readCondition.await();
                }
                String copy = new String(msg);
                msg = null;
                return copy;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                writeCondition.signal();
            }
            return msg;
        }

        public void writeMsg(String str) {
            lock.lock();
            try {
                Util.sleep(10);
                while (msg != null) {
                    writeCondition.await();
                }
                this.msg = str;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                readCondition.signal();
            }
        }
    }

    public static class Consumer implements Runnable {
        int reads;
        BlockingSyncQueue syncQueue;

        Collection<String> received = new ArrayList<>();

        public Consumer(int reads, BlockingSyncQueue syncQueue) {
            this.reads = reads;
            this.syncQueue = syncQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < reads; i++) {
                final String s = syncQueue.readMsg();
                received.add(s);
                System.out.println("Received:"  +s);
            }
        }
    }

    public static class Producer implements Runnable {
        int writes;
        BlockingSyncQueue syncQueue;

        public Producer(int writes, BlockingSyncQueue syncQueue) {
            this.writes = writes;
            this.syncQueue = syncQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < writes; i++) {
                String s = "Send: " + i;
                System.out.println(s);
                syncQueue.writeMsg("Send: " + i);
            }
        }
    }

    /**
     * Test on the ReentrantLock usage with await() operation instead of object.wait() in critical section
     * // TODO: Fix the CounterTest#run method only (look into finally block)
     */
    @Test
    public void testQueue() throws InterruptedException {
        BlockingSyncQueue queue = new BlockingSyncQueue();
        Consumer con = new Consumer(OPERS, queue);
        Thread threadC = new Thread(con);
        Thread threadP = new Thread(new Producer(OPERS, queue));

        threadC.start();
        threadP.start();
//
//        thread1.join();
//        thread2.join();
//        thread3.join();
//
        Thread.sleep(1000);
        assertEquals(10, con.received.size());
//        System.out.println("Main exit: " + count.count);
    }
}
