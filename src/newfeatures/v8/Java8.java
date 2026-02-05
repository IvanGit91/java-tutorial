package newfeatures.v8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

interface Vehicle {
    // Abstract method
    String brand();

    // Default method
    default void turnAlarmOn() {
        System.out.println("The vehicle alarm is now on.");
    }

    // Another default method
    default void turnAlarmOff() {
        System.out.println("The vehicle alarm is now off.");
    }
}

public class Java8 {
    public static void main(String[] args) {
        // Lambda Expressions
        List<String> languages = Arrays.asList("Java", "Python", "JavaScript", "C++");
        System.out.println("Languages which starts with 'J':");
        filter(languages, (str) -> str.startsWith("J"));

        // Method References
        List<String> words = Arrays.asList("Java", "Stream", "Method", "References");
        // Static Method Reference: Converting all strings to uppercase
        List<String> upperCaseWords = words.stream()
                .map(String::toUpperCase) // static method reference
                .collect(Collectors.toList());
        System.out.println("Uppercase Words: " + upperCaseWords);
        // Instance Method Reference of an Arbitrary Object of a Particular Type
        System.out.println("Printing each word:");
        words.forEach(System.out::println); // instance method reference
        // Constructor Reference: Creating new instances
        Supplier<List<String>> listSupplier = ArrayList::new; // constructor reference
        List<String> newList = listSupplier.get();
        newList.addAll(words);
        System.out.println("New List: " + newList);
        // Additional Example: Using Function Interface for Constructor Reference
        Function<String, Integer> stringToInteger = Integer::new; // constructor reference
        Integer number = stringToInteger.apply("100");
        System.out.println("String to Integer: " + number);

        // Functional Interface
        // Functional interfaces are also known as Single Abstract Method Interfaces (SAM Interfaces).
        // Using the Converter functional interface with a lambda expression
        Converter<String, Integer> stringToInteger2 = Integer::valueOf;
        // Applying the converter to convert a string to an integer
        int convertedValue = stringToInteger2.convert("123");
        System.out.println("Converted Value: " + convertedValue);
        // Another example, converting case of a string
        Converter<String, String> upperCaseConverter = String::toUpperCase;
        String convertedString = upperCaseConverter.convert("java");
        System.out.println("Converted String: " + convertedString);

        // Optional
        String[] str = new String[10]; // Initialize an array of strings with default null values.
        str[5] = "Hello, Optional!"; // Uncomment this line to test with a non-null value.
        // Create an Optional object from the value of str[5].
        Optional<String> checkNull = Optional.ofNullable(str[5]);
        // Check if the Optional object contains a value.
        if (checkNull.isPresent()) {
            // Convert the string to lowercase if it's not null.
            String word = str[5].toLowerCase();
            System.out.println(word); // Print the lowercase string.
        } else {
            System.out.println("string is null"); // Indicate that the string is null.
        }

        // forEach
        // Create a map of Integer keys and String values
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");
        // Use forEach to iterate over the map and print each key-value pair
        map.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        // Date/Time API
        // java.time: Core classes for dates, times, combined date and time, instants, durations, periods, and clocks using the ISO-8601 system.
        // java.time.chrono: Supports non-ISO calendar systems with predefined and custom chronologies.
        // java.time.format: For formatting and parsing date-time objects.
        // java.time.temporal: Advanced features for date-time manipulation, aimed at library developers.
        // java.time.zone: Handles time zones, offsets, and rules.
        // Current Date
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        // Adding 5 days
        LocalDate futureDate = today.plusDays(5);
        System.out.println("Future Date: " + futureDate);
        // Formatting the future date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = futureDate.format(formatter);
        System.out.println("Formatted Future Date: " + formattedDate);
        // Parsing a date string
        String dateString = "25-12-2024";
        LocalDate parsedDate = LocalDate.parse(dateString, formatter);
        System.out.println("Parsed Date: " + parsedDate);

        // IO Enhancements
        // Java 8 introduced several enhancements to the Input/Output (IO) and New Input/Output (NIO) frameworks, focusing primarily on improving the ease of use and efficiency of file and stream handling.

        // Type and Repeating Annotations
        //@NonNull String str;
        //List<@NonNull String> str;

        // Repeating Annotations
        // Java 8 introduced the concept of Repeating Annotations, allowing you to apply the same annotation multiple times to a single element in your code. This feature is particularly useful for situations where you need to repeatedly annotate an element with the same annotation to convey multiple pieces of information or apply multiple settings.

        // Default Methods
        // Java provides a facility to create default methods inside the interface. Methods which are defined inside the interface and tagged with default keyword are known as default methods. These methods are non-abstract methods and can have method body.
        Vehicle myCar = new Car("Tesla");
        System.out.println("Brand: " + myCar.brand());
        myCar.turnAlarmOn(); // Overridden method
        myCar.turnAlarmOff(); // Inherited default method

        // Nashorn JavaScript Engine
        // Create a script engine manager
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        // Obtain a Nashorn script engine instance
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
            // Evaluate JavaScript code from String
            nashorn.eval("print('Hello, Nashorn');");
            // Evaluate JavaScript code that returns a value
            Object result = nashorn.eval("10 + 2");
            System.out.println("Result of 10 + 2: " + result);
            // Define a JavaScript function and call it from Java
            nashorn.eval("function sum(a, b) { return a + b; }");
            Object sumResult = nashorn.eval("sum(10, 15);");
            System.out.println("Result of sum(10, 15): " + sumResult);
        } catch (ScriptException e) {
            System.err.println("ScriptException: " + e.getMessage());
        }

        // StringJoiner
        // Create a StringJoiner with a delimiter, prefix, and suffix
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        // Add strings to the StringJoiner
        joiner.add("Apple");
        joiner.add("Banana");
        joiner.add("Cherry");
        joiner.add("Date");
        // Convert the StringJoiner to String and print the result
        String result = joiner.toString();
        System.out.println(result); // [Apple, Banana, Cherry, Date]

        // Collectors
        // Collectors is a final class that extends Object class. It provides reduction operations, such as accumulating elements into collections, summarizing elements according to various criteria etc.
        // Example list of people's names
        List<String> names = Arrays.asList("John", "Sara", "Mark", "Sara", "Chris", "Paula");
        // Collecting into a List
        List<String> nameList = names.stream().collect(Collectors.toList());
        System.out.println("Names List: " + nameList);
        // Grouping names by the first letter
        Map<Character, List<String>> namesByFirstLetter = names.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0)));
        System.out.println("Names Grouped by First Letter: " + namesByFirstLetter);
        // Joining names into a single string separated by commas
        String allNames = names.stream().collect(Collectors.joining(", "));
        System.out.println("All Names Joined: " + allNames);
        // Counting the distinct names
        long distinctNameCount = names.stream().distinct().count();
        System.out.println("Distinct Names Count: " + distinctNameCount);

        // Stream API
        // A list of integers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Use Stream API to filter, map, and collect operations
        List<Integer> evenSquares = numbers.stream()
                .filter(n -> n % 2 == 0) // Filter even numbers
                .map(n -> n * n) // Map to their squares
                .collect(Collectors.toList()); // Collect results into a list

        // Print the resulting list
        System.out.println(evenSquares);

        // Stream Filter
        // A list of names
        List<String> names2 = Arrays.asList("John", "Sara", "Mark", "Jennifer", "Paul", "Jane");
        // Use Stream API to filter names that start with "J"
        List<String> namesStartingWithJ = names2.stream()
                .filter(name -> name.startsWith("J")) // Filter names starting with "J"
                .collect(Collectors.toList()); // Collect results into a list
        // Print the filtered list
        System.out.println(namesStartingWithJ);

        // Java Base64 Encoding and Decoding
        // Original String
        String originalString = "Hello, World!";
        // Encode using basic encoder
        String encodedString = Base64.getEncoder().encodeToString(originalString.getBytes());
        System.out.println("Encoded String (Basic) : " + encodedString);
        // Decode the base64 encoded string
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println("Decoded String : " + decodedString);
        // URL and Filename safe encoding
        String urlEncodedString = Base64.getUrlEncoder().encodeToString(originalString.getBytes());
        System.out.println("Encoded String (URL) : " + urlEncodedString);
        // MIME encoder example
        String mimeEncodedString = Base64.getMimeEncoder().encodeToString(originalString.getBytes());
        System.out.println("Encoded String (MIME) : " + mimeEncodedString);

        // Java Parallel Array Sorting
        // Java provides a new additional feature in Arrays class which is used to sort array elements parallelly.
        // Parallel sorting for an array of primitives
        int[] numbers2 = {9, 3, 1, 5, 13, 12, 7, 4, 11, 6};
        System.out.println("Original array: " + Arrays.toString(numbers2));
        Arrays.parallelSort(numbers2);
        System.out.println("Sorted array: " + Arrays.toString(numbers2));
        // Parallel sorting for an array of objects with a custom comparator
        String[] fruits = {"Peach", "Apple", "Orange", "Banana", "Grape", "Pear"};
        System.out.println("\nOriginal array: " + Arrays.toString(fruits));
        // Using a lambda expression for the comparator to sort in reverse alphabetical order
        Arrays.parallelSort(fruits, Comparator.reverseOrder());
        System.out.println("Sorted array in reverse order: " + Arrays.toString(fruits));
    }

    public static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    // An Interface that contains only one abstract method is known as functional interface. It can have any number of default and static methods. It can also declare methods of object class.
    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    // Repeating Annotations
    //@Repeatable(Reviews.class)
    @Retention(RetentionPolicy.RUNTIME) // Make this annotation available at runtime.
    @interface Review {
        String reviewer();

        String date();

        String comment();
    }
}

record Car(String brand) implements Vehicle {

    // The class can choose to override a default method
    @Override
    public void turnAlarmOn() {
        System.out.println("The car alarm is now on.");
    }
}
