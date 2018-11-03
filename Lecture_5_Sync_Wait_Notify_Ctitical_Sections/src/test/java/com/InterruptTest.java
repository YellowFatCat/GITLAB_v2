package com;

import com.mycompany.prepare.utils.Utils;
import org.junit.Test;

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertEquals;

// TODO: fix the test useing only volatile
public class InterruptTest {
    static boolean flag = true;

    private boolean exec() {
        while (flag) {
            int counter = 0;
            counter++;
        }
        return true;
    }

    @Test
    public void testSync() {
        AtomicReference<Boolean> ref = new AtomicReference<>();
        ref.set(false);

        new Thread(() -> {
            ref.set(exec());
        }).start();

        new Thread(() -> {
            int counter = 0;
            while (true) {
                counter++;
                if (flag)
                    flag = false;
            }
        }).start();

        Utils.sleep(2000);

        assertEquals(true, ref.get());
    }

}
