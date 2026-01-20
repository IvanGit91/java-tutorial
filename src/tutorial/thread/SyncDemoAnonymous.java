package tutorial.thread;

// Java Program to synchronized method by using an anonymous class

class Test {
    synchronized void test_function(int n) {
        // synchronized method
        for (int i = 1; i <= 3; i++) {
            System.out.println(n + i);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

// Driver Class
public class SyncDemoAnonymous {
    // Main function
    public static void main(String[] args) {
        // only one object
        final Test obj = new Test();

        Thread a = new Thread() {
            public void run() {
                obj.test_function(15);
            }
        };

        Thread b = new Thread() {
            public void run() {
                obj.test_function(30);
            }
        };

        a.start();
        b.start();
    }
}