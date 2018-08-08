package prepare.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReference1 {

    public static void main(String[] args) {
        AtomicReference reference = new AtomicReference();

        final String strBefore = "Before changes";
        final String afterChanges = "After Changes";

        reference.compareAndSet(null, strBefore);
        reference.compareAndSet(null, strBefore);

        reference.compareAndSet(strBefore, afterChanges);
        reference.compareAndSet(strBefore, "This is a bug");

        System.out.println("Result: " + reference.get());

    }
}
