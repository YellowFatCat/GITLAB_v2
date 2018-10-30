package com.mycompany.prepare.utils;

public class Utils {
    public static void sleep(final int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
