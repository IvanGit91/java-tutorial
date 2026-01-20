package tutorial.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTryLockExample {
    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantTryLockExample example = new ReentrantTryLockExample();

        Runnable task1 = () -> example.performTask("Task1");
        Runnable task2 = () -> example.performTaskWithTimeout("Task2");

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
    }

    public void performTask(String taskName) {
        if (lock.tryLock()) { // Non-blocking attempt to acquire the lock
            try {
                System.out.println(taskName + " acquired the lock.");
                // Simulate critical section
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
                System.out.println(taskName + " released the lock.");
            }
        } else {
            System.out.println(taskName + " could not acquire the lock.");
        }
    }

    public void performTaskWithTimeout(String taskName) {
        try {
            if (lock.tryLock(2, TimeUnit.SECONDS)) { // Attempt lock acquisition with timeout
                try {
                    System.out.println(taskName + " acquired the lock.");
                    // Simulate critical section
                    Thread.sleep(1000);
                } finally {
                    lock.unlock();
                    System.out.println(taskName + " released the lock.");
                }
            } else {
                System.out.println(taskName + " could not acquire the lock within timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(taskName + " was interrupted while waiting for the lock.");
        }
    }
}
