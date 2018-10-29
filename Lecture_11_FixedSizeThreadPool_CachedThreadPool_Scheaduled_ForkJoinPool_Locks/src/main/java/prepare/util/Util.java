package prepare.util;

public class Util {

    public static void threadSleep(final int mil) throws InterruptedException {
        try {
            Thread.sleep(mil);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
