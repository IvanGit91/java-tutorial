package newfeatures.java18;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Arrays;

public class Java18 {
    public static void main(String[] args) throws UnknownHostException {
        // 1 UTF-8 by Default

        // 2 Simple Web Server
        // The easiest way to start the provided webserver is the jwebserver command.
        // It starts the server on localhost:8000 and provides a file browser for the current directory:
        // Can also be started with the following code:
        HttpServer server =
                SimpleFileServer.createFileServer(
                        new InetSocketAddress(8080), Path.of("\tmp"), SimpleFileServer.OutputLevel.INFO);
        server.start();

        // 3 Code Snippets in Java API Documentation
        // Before:
        // Sample with pre
        /**
         * How to write a text file with Java 7:
         *
         * <pre><b>try</b> (BufferedWriter writer = Files.<i>newBufferedWriter</i>(path)) {
         *  writer.write(text);
         *}</pre>
         */
        // And one with <pre> and {@code … }:
        /**
         * How to write a text file with Java 7:
         *
         * <pre>{@code try (BufferedWriter writer = Files.newBufferedWriter(path)) {
         *  writer.write(text);
         *}}</pre>
         */
        // NOW:
        // enhances the JavaDoc syntax with the @snippet tag
        /**
         * How to write a text file with Java 7:
         *
         * {@snippet :
         * try (BufferedWriter writer = Files.newBufferedWriter(path)) {
         *   writer.write(text);
         * }
         *}
         */

        // 4 Internet-Address Resolution SPI
        // To find out the IP address(es) for a hostname in Java, we can use InetAddress.getByName(…) or InetAddress.getAllByName(…)
        InetAddress[] addresses = InetAddress.getAllByName("www.happycoders.eu");
        System.out.println("addresses = " + Arrays.toString(addresses));
    }
}
