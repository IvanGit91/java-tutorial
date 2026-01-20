package tutorial.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureTest {

    public static void main(String[] args) {
        CompletableFutureTest c = new CompletableFutureTest();
        try {
            Future<String> f = c.calculateAsync();

            while (!f.isDone())
                System.out.println("DONE: " + f.isDone());
            System.out.println("DONE: " + f.isDone());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            return completableFuture.complete("Hello");
        });

        return completableFuture;
    }

}
