package newfeatures.v22;

import java.util.Arrays;
import java.util.List;

public class Java22 {
    public static void main(String[] args) {
        System.out.println("=== Java 22 Final Features ===\n");

        // 1. Unnamed Variables & Patterns – JEP 456 (Final)
        // Allows using underscore (_) for unused variables, making code cleaner and more intentional
        // Previously you had to name variables even if you didn't use them

        System.out.println("1. Unnamed Variables & Patterns:\n");

        // Example with records - ignoring some fields
        record Point(int x, int y, int z) {}
        Point point = new Point(10, 20, 30);

        // Old way - had to name all variables even if not used
        if (point instanceof Point(int x, int y, int z)) {
            System.out.println("   Old way - X coordinate: " + x); // y and z named but not used
        }

        // Java 22 way - use _ for unused variables
        if (point instanceof Point(int x, _, _)) {
            System.out.println("   Java 22 - X coordinate: " + x); // clearly shows y and z are ignored
        }

        // Also useful in try-catch blocks
        System.out.println("\n   Exception handling with unnamed variable:");
        try {
            int result = Integer.parseInt("not a number");
        } catch (NumberFormatException _) {
            // Exception object not needed, use _ instead of 'e'
            System.out.println("   Caught exception (variable not needed)");
        }

        // Useful in loops where index is not needed
        System.out.println("\n   Loop with unnamed variable:");
        List<String> items = Arrays.asList("apple", "banana", "cherry");
        int count = 0;
        for (var _ : items) {
            count++;
        }
        System.out.println("   Processed " + count + " items");

        // Example demonstrating unnamed patterns in switch
        System.out.println("\n   Pattern matching with unnamed variables:");
        processShape(new Circle(5.0));
        processShape(new Square(4.0, "red"));

        // 2. Foreign Function & Memory API – JEP 454 (Final)
        // Allows Java programs to interoperate with code and data outside the Java runtime
        // Call native libraries without JNI, access foreign memory safely
        // This feature became final in Java 22 after being in preview since Java 19
        System.out.println("\n2. Foreign Function & Memory API:");
        System.out.println("   Call native libraries without JNI");
        System.out.println("   Safe access to foreign memory");
        System.out.println("   Better performance than traditional JNI");

        /*
        import java.lang.foreign.*;

        // Example: Calling C's strlen function
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        MemorySegment strlenAddress = stdlib.find("strlen").orElseThrow();

        FunctionDescriptor strlenDescriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS
        );

        MethodHandle strlen = linker.downcallHandle(strlenAddress, strlenDescriptor);

        // Call the native function
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment str = arena.allocateUtf8String("Hello World");
            long length = (long) strlen.invoke(str);
            System.out.println("String length: " + length);
        }
        */

        // 3. Region Pinning for G1 – JEP 423
        // Performance improvement for G1 garbage collector
        // Reduces latency when using JNI critical regions
        // No code changes needed - automatic improvement
        System.out.println("\n3. Region Pinning for G1 GC:");
        System.out.println("   Improves G1 GC performance with JNI critical regions");
        System.out.println("   Reduces latency spikes");
        System.out.println("   No code changes required - automatic improvement");

        System.out.println("\n=== Final Features Summary ===");
        System.out.println("1. Unnamed Variables & Patterns (Final)");
        System.out.println("2. Foreign Function & Memory API (Final)");
        System.out.println("3. Region Pinning for G1 GC");
        System.out.println("\nFor preview features, see Java22Preview.java");
    }

    // Example demonstrating unnamed patterns in switch
    static void processShape(Object shape) {
        switch (shape) {
            case Circle(double radius) ->
                System.out.println("   Circle with radius: " + radius);
            case Rectangle(double width, double height) ->
                System.out.println("   Rectangle " + width + "x" + height);
            case Square(double side, _) -> // Ignore color parameter using unnamed variable
                System.out.println("   Square with side: " + side + " (color ignored)");
            default ->
                System.out.println("   Unknown shape");
        }
    }

    // Example showing multiple unnamed variables in nested patterns
    static void processNestedPattern(Object obj) {
        record Point(int x, int y, int z) {}
        record Container(Point point, String label) {}

        if (obj instanceof Container(Point(int x, _, _), _)) {
            // Only care about x coordinate, ignore y, z, and label
            System.out.println("X coordinate: " + x);
        }
    }
}

record Circle(double radius) {}
record Rectangle(double width, double height) {}
record Square(double side, String color) {} // color is ignored in pattern matching using unnamed variable
