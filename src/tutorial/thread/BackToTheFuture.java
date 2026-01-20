package tutorial.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class BackToTheFuture {
    private Double whatsInFuture(ExecutorService executor, Callable<Double> task) {
        try {
            return executor.submit(task).get();
        } catch (Exception ex) {
            return null;
        }
    }
}
