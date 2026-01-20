package tutorial.thread;

import java.util.concurrent.*;

public class RunnableCallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Represents a task with no return value.
        Runnable task = () -> System.out.println("Runnable running!");
        new Thread(task).start();

        // Introduced in Java 5, it represents a task that returns a result and can throw checked exceptions.
        // It's commonly used with ExecutorService for asynchronous tasks.
        Callable<Integer> taskk = () -> {
            return 42;
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // 1) execute(): Used to submit a task for execution without expecting a result.
        // Future<Integer> future = executor.execute(taskk);
        // 2) submit(): Used to submit a task and obtain a Future object to retrieve the result or check the task's status.
        Future<Integer> future = executor.submit(taskk);
        Integer result = future.get(); // Blocks until result is available
    }
}
