package tutorial.thread;

// 1. Extending the Thread Class
class MyThread1 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

// 2. Implementing the Runnable Interface
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

public class WaysToStartThread {
    public static void main(String[] args) {
        // 1
        MyThread1 thread = new MyThread1();
        thread.start();

        // 2
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();

        // 3. Using Lambda Expressions
        Thread thread3 = new Thread(() -> System.out.println("Thread is running"));
        thread3.start();

        // Since Java 21
        // 4. Virtual thread
        Thread.startVirtualThread(() -> {
            // code to run in thread
        });

        Thread.ofVirtual().start(() -> {
            // code to run in thread
        });
    }
}
