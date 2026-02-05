package newfeatures.v11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Java11 {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 3.1. New String Methods
        // Java 11 adds a few new methods to the String class: isBlank, lines, strip, stripLeading, stripTrailing, and repeat.
        String multilineString = "Baeldung helps \n \n developers \n explore Java.";
        List<String> lines = multilineString.lines()
                .filter(line -> !line.isBlank())
                .map(String::strip)
                .collect(Collectors.toList());
        // assertThat(lines).containsExactly("Baeldung helps", "developers", "explore Java.");

        // 3.2. New File Methods
        // We can use the new readString and writeString static methods from the Files class:
        String tempDir = System.getProperty("java.io.tmpdir");
        Path filePath = Files.writeString(Files.createTempFile(Path.of(tempDir), "demo", ".txt"), "Sample text");
        String fileContent = Files.readString(filePath);
        // assertThat(fileContent).isEqualTo("Sample text");

        // 3.3. Collection to an Array
        // The java.util.Collection interface contains a new default toArray method which takes an IntFunction argument.
        //This makes it easier to create an array of the right type from a collection:
        List sampleList = Arrays.asList("Java", "Kotlin");
        String[] sampleArray = (String[]) sampleList.toArray(String[]::new);
        // assertThat(sampleArray).containsExactly("Java", "Kotlin");

        // 3.4. The Not Predicate Method
        // A static not method has been added to the Predicate interface. We can use it to negate an existing predicate, much like the negate method:
        List<String> sampleList2 = Arrays.asList("Java", "\n \n", "Kotlin", " ");
        List withoutBlanks = sampleList2.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());
        //assertThat(withoutBlanks).containsExactly("Java", "Kotlin");

        // 3.5. Local-Variable Syntax for Lambda
        // Support for using the local variable syntax (var keyword) in lambda parameters was added in Java 11.
        List<String> sampleList3 = Arrays.asList("Java", "Kotlin");
        String resultString = sampleList3.stream()
                .map((var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));
        //assertThat(resultString).isEqualTo("JAVA, KOTLIN");

        // 3.6. HTTP Client
        // The new HTTP client from the java.net.http package was introduced in Java 9. It has now become a standard feature in Java 11.
        int port = 8000;
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:" + port))
                .build();
        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        //assertThat(httpResponse.body()).isEqualTo("Hello from the server!");

        // A major change in this version is that we don’t need to compile the Java source files with javac explicitly anymore:
        /*
            $ javac HelloWorld.java
            $ java HelloWorld
            Hello Java 8!
        */
        // Instead, we can directly run the file using the java command:
        /*
            $ java HelloWorld.java
            Hello Java 11!
        */
    }
}
