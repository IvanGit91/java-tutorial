package tutorial.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    private static final int MAX_AVAILABLE = 3;
    private final Semaphore semaphore = new Semaphore(MAX_AVAILABLE);

    public static void main(String[] args) {
        SemaphoreExample example = new SemaphoreExample();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> example.accessResource()).start();
        }
    }

    public void accessResource() {
        try {
            semaphore.acquire(); // Acquire a permit
            System.out.println(Thread.currentThread().getName() + " is accessing the resource.");
            Thread.sleep(1000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release(); // Release the permit
            System.out.println(Thread.currentThread().getName() + " has released the resource.");
        }
    }
}