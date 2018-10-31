package com.mycompany.prepare;

import java.util.stream.IntStream;

public class SyncQueueImpl {

    public SyncQueueImpl() {
    }

    private static class Queue {

        public Object[] object = new Object[10];
        public Object lock = new Object();
        public int counter = 0;

        public Object readObject() {
            synchronized (lock) {
                try {
                    if (counter == 0) {
                        lock.wait();
                    }
                    return object[--counter];
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.notify();
                }
            }
            return object[--counter];
        }

        public void writeObject(Object object) {
            synchronized (lock) {
                try {
                    if (counter == 9) {
                        lock.wait();
                    }
                    this.object[counter++] = object;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.notify();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new SyncQueueImpl();

        Queue queue = new Queue();

        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x)->{
                String str = "" + x;
                System.out.println("Write: " + str);
                queue.writeObject(str);
            });
        }).start();

        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x)-> {
                Object str = queue.readObject();
                System.out.println("Read : " + str);
            });
        }).start();
    }
}
