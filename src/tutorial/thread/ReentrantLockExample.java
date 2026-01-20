package tutorial.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // Creating two threads that will try to access the critical section
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());

        thread1.start();
        thread2.start();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                // Trying to acquire the lock
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " has acquired the lock.");

                // Simulating some work with the lock held
                Thread.sleep(1000);

                System.out.println(Thread.currentThread().getName() + " is releasing the lock.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Releasing the lock in the finally block to ensure it's always released
                lock.unlock();
            }
        }
    }
}
