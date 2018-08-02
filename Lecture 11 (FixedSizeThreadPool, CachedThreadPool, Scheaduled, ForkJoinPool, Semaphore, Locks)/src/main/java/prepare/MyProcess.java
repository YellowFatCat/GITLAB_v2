package prepare;



import prepare.util.Util;
import java.io.IOException;

// 1. - what is the difference between Threads and Processes
public class MyProcess {


    public static void main( String[] args ) throws IOException {
        Process process = new ProcessBuilder().command("notepad ").start();
        Util.threadSleep(3000);
        process.destroy();

        System.out.println("Start new Job when all workers has been completed");
    }
}
