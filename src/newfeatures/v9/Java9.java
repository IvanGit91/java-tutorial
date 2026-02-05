package newfeatures.v9;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// 5.3. Interface Private Method
interface InterfaceWithPrivateMethods {
    private static String staticPrivate() {
        return "static private";
    }

    private String instancePrivate() {
        return "instance private";
    }

    default void check() {
        String result = staticPrivate();
        InterfaceWithPrivateMethods pvt = new InterfaceWithPrivateMethods() {
            // anonymous class
        };
        result = pvt.instancePrivate();
    }
}

public class Java9 {
    public static void main(String[] args) throws URISyntaxException {
        // 3. A New HTTP Client
        // 3.1. Quick GET Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/get"))
                .GET()
                .build();

        // HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandler.asString());

        // 4. Process API
        ProcessHandle self = ProcessHandle.current();
        long PID = self.pid();
        ProcessHandle.Info procInfo = self.info();

        Optional<String[]> argss = procInfo.arguments();
        Optional<String> cmd = procInfo.commandLine();
        Optional<Instant> startTime = procInfo.startInstant();
        Optional<Duration> cpuUsage = procInfo.totalCpuDuration();

        // 5.1. Try-With-Resources
        // old
//        MyAutoCloseable mac = new MyAutoCloseable();
//        try (mac) {
//            // do some stuff with mac
//        }
//        now
//        try (new MyAutoCloseable() { }.finalWrapper.finalCloseable) {
//            // do some stuff with finalCloseable
//        } catch (Exception ex) { }

        // 5.2. Diamond Operator Extension


        // 12.1. Immutable Set
        Set<String> strKeySet = Set.of("key1", "key2", "key3");
        // if we try to add or remove elements, an UnsupportedOperationException will be thrown.

        // 12.2. Optional to Stream
        List<Optional<String>> listOfOptionals = Collections.singletonList(Optional.of("1"));
        List<String> filteredList = listOfOptionals.stream()
                .flatMap(Optional::stream)
                .toList();
    }
}