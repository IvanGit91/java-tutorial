package tutorial.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    // Using ReentrantLock
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

    }

    // Using synchronized
    public synchronized void synchronizedMethod() {
        // Critical section
    }

    public void reentrantLockMethod() {
        lock.lock();
        try {
            // Critical section
        } finally {
            lock.unlock(); // Always release the lock!
        }
    }
}
