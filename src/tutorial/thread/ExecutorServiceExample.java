package tutorial.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        // Create a thread pool with 4 threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            // Submit Runnable tasks
            for (int i = 1; i <= 5; i++) {
                final int taskId = i;
                executorService.submit(() -> {
                    System.out.println("Runnable Task " + taskId + " is running on thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Runnable Task " + taskId + " completed.");
                });
            }

            // Submit a Callable task
            Callable<String> callableTask = () -> {
                System.out.println("Callable task is running on thread: " + Thread.currentThread().getName());
                Thread.sleep(2000); // Simulate work
                return "Callable task completed.";
            };

            Future<String> result = executorService.submit(callableTask);

            // Get result from Callable
            String res = result.get();
            System.out.println("Callable Result: " + res);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shut down the ExecutorService
            executorService.shutdown();
            System.out.println("ExecutorService is shut down.");
        }
    }
}
