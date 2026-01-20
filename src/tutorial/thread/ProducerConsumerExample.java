package tutorial.thread;

class SharedResource {
    private int counter = 0;

    // Method for the producer thread
    public synchronized void produce() throws InterruptedException {
        while (counter >= 1) {
            wait();  // Wait if the counter is already 1 (or greater)
        }
        counter++;
        System.out.println("Produced: " + counter);
        notify();  // Notify the consumer thread to consume
    }

    // Method for the consumer thread
    public synchronized void consume() throws InterruptedException {
        while (counter <= 0) {
            wait();  // Wait if the counter is 0 or less
        }
        counter--;
        System.out.println("Consumed: " + counter);
        notify();  // Notify the producer thread to produce
    }
}

public class ProducerConsumerExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    resource.produce();
                    Thread.sleep(1000); // Simulating work
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    resource.consume();
                    Thread.sleep(1000); // Simulating work
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start the threads
        producer.start();
        consumer.start();
    }
}
