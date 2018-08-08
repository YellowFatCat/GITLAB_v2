package com;

import prepare.util.Util;

import java.io.File;
import java.io.IOException;

public class ProcessExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder process = new ProcessBuilder().command("notepad");
        process.redirectOutput(new File("file.log"));
        process.start();
        Util.threadSleep(2000);
        System.out.println("Exit...");
    }
}
