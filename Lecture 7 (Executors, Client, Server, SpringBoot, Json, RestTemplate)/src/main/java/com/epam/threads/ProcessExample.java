package com.epam.threads;

import com.epam.threads.util.Util;

import java.io.File;
import java.io.IOException;

public class ProcessExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder process = new ProcessBuilder().command("notepad");
        process.redirectOutput(new File("file.log"));
        process.start();
        Util.sleep(2000);
        System.out.println("Exit...");
    }
}
