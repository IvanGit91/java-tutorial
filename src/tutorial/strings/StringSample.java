package tutorial.strings;

/**
 * Demonstrates string formatting in Java.
 */
public class StringSample {
    public static void main(String[] args) {
        // String formatting examples
        String s = "hello %s!";
        s = String.format(s, "world");
        System.out.println(s);
    }
}
