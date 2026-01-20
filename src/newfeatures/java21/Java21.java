package newfeatures.java21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

enum CompassDirection implements Direction {NORTH, SOUTH, EAST, WEST}

enum VerticalDirection implements Direction {UP, DOWN}

sealed interface Direction permits CompassDirection, VerticalDirection {
}

public class Java21 {
    public static void main(String[] args) {
        // 1 Virtual Threads – JEP 444
        // When scaling server applications, threads are often a bottleneck. Their number is limited, and they often have to wait for events, such as the response of a database query or a remote call, or they are blocked by locks
        // Previous approaches, such as CompletableFuture or reactive frameworks, result in code that is extremely difficult to read and maintain.
        // Unlike reactive code, virtual threads allow programming in the familiar, sequential thread-per-request style.
        // Virtual threads feel like normal threads from a Java code perspective, but they are not mapped 1:1 to operating system threads.
        // Instead, there is a pool of so-called carrier threads onto which a virtual thread is temporarily mapped ("mounted"). As soon as the virtual thread encounters a blocking operation, the virtual thread is removed ("unmounted") from the carrier thread, and the carrier thread can execute another virtual thread (a new one or a previously blocked one).

        // 2 Sequenced Collections – JEP 431
        List<String> list = new ArrayList<>();
        var first = list.getFirst();
        var last = list.getLast();
        // void addFirst(E) – inserts an element at the beginning of the collection
        // void addLast(E) – appends an element to the end of the collection
        // E removeFirst() – removes the first element and returns it
        // E removeLast() – removes the last element and returns it

        // SequencedMap Interface
        // Entry<K, V> firstEntry() – returns the first key-value pair of the map
        // Entry<K, V> lastEntry() – returns the last key-value pair of the map
        // Entry<K, V> pollFirstEntry() – removes the first key-value pair and returns it
        // Entry<K, V> pollLastEntry() – removes the last key-value pair and returns it
        // V putFirst(K, V) – inserts a key-value pair at the beginning of the map
        // V putLast(K, V) – appends a key-value pair to the end of the map
        // SequencedMap<K, V> reversed() – returns a view on the map in reverse order

        // New Collections Methods
        // newSequencedSetFromMap(SequencedMap map) – analogous to Collections.setFromMap(…), this method returns a SequencedSet with the properties of the underlying map.
        // unmodifiableSequencedCollection(SequencedCollection c) – analogous to Collections.unmodifiableCollection(…) returns an unmodifiable view of the underlying SequencedCollection.
        // Collections.unmodifiableSequencedMap(SequencedMap m) – returns an unmodifiable view of the underlying SequencedMap, analogous to Collections.unmodifiableMap(…).
        // Collections.unmodifiableSequencedSet(SequencedSet s) – returns an unmodifiable view of the underlying SequencedSet, analogous to Collections.unmodifiableSet(…).

        // 3 Record Patterns – JEP 440
        // They can be combined with Pattern Matching for instanceof and Pattern Matching for switch to access the fields of a record without explicit casts and without using access methods.
        // Record Patterns and Pattern Matching for switch

        // 4 Pattern Matching for switch – JEP 441
        Object obj = getObject();
        switch (obj) {
            case String s when s.length() > 5 -> System.out.println(s.toUpperCase());
            case String s -> System.out.println(s.toLowerCase());
            case Integer i -> System.out.println(i * i);
            case Position(int x, int y) -> System.out.println(x + "/" + y);
            default -> {
            }
        }

        // 5 New String Methods
        // String.indexOf(String str, int beginIndex, int endIndex) – searches the specified substring in a subrange of the string.
        // String.indexOf(char ch, int beginIndex, int endIndex) – searches the specified character in a subrange of the string.
        // String.splitWithDelimiters(String regex, int limit) – splits the string at substrings matched by the regular expression and returns an array of all parts and splitting strings. The string is split at most limit-1 times, i.e., the last element of the array could be further divisible.
        String string = "the red brown fox jumps over the lazy dog";
        String[] parts = string.splitWithDelimiters(" ", 5);
        System.out.println(Arrays.stream(parts).collect(Collectors.joining("', '", "'", "'")));

        // 6 New StringBuilder and StringBuffer Methods
        // repeat(CharSequence cs, int count) – appends to the StringBuilder or StringBuffer the string cs – count times.
        // repeat(int codePoint, int count) – appends the specified Unicode code point to the StringBuilder or StringBuffer – count times. A variable or constant of type char can also be passed as code point.

        // 7 New Character Methods
        // isEmoji(int codePoint)
        // isEmojiComponent(int codePoint)
        // isEmojiModifier(int codePoint)
        // isEmojiModifierBase(int codePoint)
        // isEmojiPresentation(int codePoint)
        // isExtendedPictographic(int codePoint)

        // 8 New Math Methods
        // How many times have we written the following piece of code to ensure that a number is in a given numeric range, or otherwise pushed in?
        Integer value = 0, min = 0, max = Integer.MIN_VALUE;
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }
        // From now on, we can use Math.clamp(...) for exactly this purpose.
    }

    private static Object getObject() {
        return new Object();
    }

    // Old
    public void print(Object o) {
        if (o instanceof Position(int x, int y)) {
            System.out.printf("o is a position: %d/%d%n", x, y);
        } else if (o instanceof String s) {
            System.out.printf("o is a string: %s%n", s);
        } else {
            System.out.printf("o is something else: %s%n", o);
        }
    }

    // In Java 21
    public void print21(Object o) {
        if (o instanceof Position(int x, int y)) {
            System.out.printf("o is a position: %d/%d%n", x, y);
        } else if (o instanceof String s) {
            System.out.printf("o is a string: %s%n", s);
        } else {
            System.out.printf("o is something else: %s%n", o);
        }
    }

    // Old
    public void printSwitch(Object o) {
        switch (o) {
            case Position p -> System.out.printf("o is a position: %d/%d%n", p.x(), p.y());
            case String s -> System.out.printf("o is a string: %s%n", s);
            default -> System.out.printf("o is something else: %s%n", o);
        }
    }

    // In Java 21
    public void printSwitch21(Object o) {
        switch (o) {
            case Position(int x, int y) -> System.out.printf("o is a position: %d/%d%n", x, y);
            case String s -> System.out.printf("o is a string: %s%n", s);
            default -> System.out.printf("o is something else: %s%n", o);
        }
    }

    void flyJava20(Direction direction) {
        switch (direction) {
            case CompassDirection d when d == CompassDirection.NORTH -> System.out.println("Flying north");
            case CompassDirection d when d == CompassDirection.SOUTH -> System.out.println("Flying south");
            case CompassDirection d when d == CompassDirection.EAST -> System.out.println("Flying east");
            case CompassDirection d when d == CompassDirection.WEST -> System.out.println("Flying west");
            case VerticalDirection d when d == VerticalDirection.UP -> System.out.println("Gaining altitude");
            case VerticalDirection d when d == VerticalDirection.DOWN -> System.out.println("Losing altitude");
            default -> throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    void flyJava21(Direction direction) {
        switch (direction) {
            case CompassDirection.NORTH -> System.out.println("Flying north");
            case CompassDirection.SOUTH -> System.out.println("Flying south");
            case CompassDirection.EAST -> System.out.println("Flying east");
            case CompassDirection.WEST -> System.out.println("Flying west");
            case VerticalDirection.UP -> System.out.println("Gaining altitude");
            case VerticalDirection.DOWN -> System.out.println("Losing altitude");
        }
    }
}

record Position(int x, int y) {
}