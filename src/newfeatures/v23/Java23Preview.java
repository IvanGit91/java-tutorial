package newfeatures.v23;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherers;

public class Java23Preview {
    public static void main(String[] args) {
        System.out.println("=== Java 23 Preview Features ===\n");

        // 1. Primitive Patterns in instanceof and switch – JEP 455 (Preview)
        // Extends pattern matching to work with primitive types
        // Previously, pattern matching only worked with reference types
        System.out.println("1. Primitive Patterns:");

        Object obj = 42;

        // Pattern matching with primitives
        if (obj instanceof Integer i) {
            System.out.println("   Integer value: " + i);
        }

        // Switch with primitive patterns (requires preview)
        /*
        Object value = 3.14;
        String result = switch (value) {
            case int i    -> "Integer: " + i;
            case long l   -> "Long: " + l;
            case double d -> "Double: " + d;
            case String s -> "String: " + s;
            default       -> "Unknown type";
        };
        */

        // 2. Flexible Constructor Bodies – JEP 482 (Preview)
        // Enhanced version of "Statements before super()" from Java 22
        // Allows more flexible initialization in constructors
        System.out.println("\n2. Flexible Constructor Bodies:");
        System.out.println("   More statements allowed before super() or this()");
        System.out.println("   Can initialize fields, validate arguments, etc.");
        System.out.println("   See Child class example below");

        // 3. Stream Gatherers – JEP 473 (Second Preview)
        // Custom intermediate stream operations
        // Now in second preview with refinements
        System.out.println("\n3. Stream Gatherers (Second Preview):");

        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");

        // Fixed window gatherer
        List<List<String>> windows = words.stream()
            .gather(Gatherers.windowFixed(2))
            .toList();
        System.out.println("   Fixed windows: " + windows);

        // Fold operation
        String concatenated = words.stream()
            .gather(Gatherers.fold(() -> "", (acc, word) -> acc + word.charAt(0)))
            .findFirst()
            .orElse("");
        System.out.println("   First letters: " + concatenated);

        // 4. Module Import Declarations – JEP 476 (Preview)
        // Import all packages from a module at once
        // Simplifies imports for modular applications
        System.out.println("\n4. Module Import Declarations:");
        System.out.println("   Import entire modules: import module java.base;");
        System.out.println("   Reduces boilerplate import statements");

        /*
        // Instead of:
        import java.util.List;
        import java.util.Map;
        import java.util.Set;
        // ... many more imports

        // You can write:
        import module java.base;
        */

        // 5. Implicitly Declared Classes and Instance Main Methods – JEP 477 (Third Preview)
        // Continued refinement for simpler Hello World programs
        System.out.println("\n5. Implicitly Declared Classes (Third Preview):");
        System.out.println("   Simplified entry point for learning Java");
        System.out.println("   void main() { ... } is sufficient");

        // 6. Structured Concurrency – JEP 480 (Third Preview)
        // Treats related concurrent tasks as a unit
        // Now in third preview with improvements
        System.out.println("\n6. Structured Concurrency (Third Preview):");
        System.out.println("   Refined API for managing concurrent tasks");
        System.out.println("   Better error handling and cancellation");

        /*
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = scope.fork(() -> fetchData1());
            var task2 = scope.fork(() -> fetchData2());

            scope.join();
            scope.throwIfFailed();

            // Both tasks completed successfully
            process(task1.get(), task2.get());
        }
        */

        // 7. Scoped Values – JEP 481 (Third Preview)
        // Efficient data sharing in concurrent programs
        // Better alternative to ThreadLocal
        System.out.println("\n7. Scoped Values (Third Preview):");
        System.out.println("   Improved performance for virtual threads");
        System.out.println("   Immutable scoped data sharing");

        /*
        private static final ScopedValue<User> CURRENT_USER = ScopedValue.newInstance();

        ScopedValue.where(CURRENT_USER, user).run(() -> {
            // CURRENT_USER.get() returns user in this scope
            handleRequest();
        });
        */

        // 8. Class-File API – JEP 466 (Second Preview)
        // Standard API for class file manipulation
        // Second preview with refinements
        System.out.println("\n8. Class-File API (Second Preview):");
        System.out.println("   Parse, generate, and transform class files");
        System.out.println("   Refined API based on feedback");

        // 9. Vector API – JEP 469 (Seventh Incubator)
        // SIMD operations for improved performance
        // Still in incubator status
        System.out.println("\n9. Vector API (Seventh Incubator):");
        System.out.println("   Express vector computations");
        System.out.println("   Better performance for numeric operations");

        /*
        import jdk.incubator.vector.*;

        static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

        void vectorComputation(float[] a, float[] b, float[] c) {
            for (int i = 0; i < a.length; i += SPECIES.length()) {
                var va = FloatVector.fromArray(SPECIES, a, i);
                var vb = FloatVector.fromArray(SPECIES, b, i);
                var vc = va.mul(vb);
                vc.intoArray(c, i);
            }
        }
        */

        System.out.println("\n=== Preview Features Summary ===");
        System.out.println("1. Primitive Patterns (Preview)");
        System.out.println("2. Flexible Constructor Bodies (Preview)");
        System.out.println("3. Stream Gatherers (Second Preview)");
        System.out.println("4. Module Import Declarations (Preview)");
        System.out.println("5. Implicitly Declared Classes (Third Preview)");
        System.out.println("6. Structured Concurrency (Third Preview)");
        System.out.println("7. Scoped Values (Third Preview)");
        System.out.println("8. Class-File API (Second Preview)");
        System.out.println("9. Vector API (Seventh Incubator)");
        System.out.println("\nNote: Preview features require --enable-preview flag");
    }

    // Example: Flexible Constructor Bodies - JEP 482
    static class Parent {
        protected final String config;
        protected final int id;

        Parent(String config, int id) {
            this.config = config;
            this.id = id;
        }
    }

    static class Child extends Parent {
        private final String normalized;

        Child(String rawConfig, int rawId) {
            // Can perform validation and transformations
            String processed = rawConfig.trim().toUpperCase();
            int validatedId = rawId > 0 ? rawId : 1;

            // Store some results in fields before super()
            this.normalized = processed.toLowerCase();

            if (processed.isEmpty()) {
                throw new IllegalArgumentException("Config cannot be empty");
            }

            super(processed, validatedId);
        }
    }
}
