package newfeatures.v22;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherers;

public class Java22Preview {
    public static void main(String[] args) {
        System.out.println("=== Java 22 Preview Features ===\n");

        // 1. Stream Gatherers – JEP 461 (Preview)
        // New intermediate operation that allows custom stream transformations
        // More flexible than existing map, filter, etc.
        // Requires --enable-preview flag

        // Example: Using built-in gatherers
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");

        // Fixed window gatherer - groups elements into fixed-size windows
        List<List<String>> windows = words.stream()
            .gather(Gatherers.windowFixed(2))
            .toList();
        System.out.println("1. Stream Gatherers - Fixed windows:");
        System.out.println("   " + windows); // [[apple, banana], [cherry, date], [elderberry]]

        // Sliding window gatherer
        List<List<String>> sliding = words.stream()
            .gather(Gatherers.windowSliding(2))
            .toList();
        System.out.println("   Sliding windows:");
        System.out.println("   " + sliding); // [[apple, banana], [banana, cherry], [cherry, date], [date, elderberry]]

        // 2. Statements Before super() – JEP 447 (Preview)
        // Allows statements to appear before explicit constructor invocations (super() or this())
        // Previously, super() or this() had to be the first statement
        // See example in Child class below
        System.out.println("\n2. Statements Before super() - See Child class example");

        // 3. Implicitly Declared Classes and Instance Main Methods – JEP 463 (Second Preview)
        // Simplifies "Hello World" programs for beginners
        // Allows main method to be an instance method and without String[] args
        // Can omit class declaration for single-file programs
        System.out.println("\n3. Implicitly Declared Classes:");
        System.out.println("   Traditional: public class HelloWorld { public static void main(String[] args) {...} }");
        System.out.println("   Java 22:     void main() { System.out.println(\"Hello!\"); }");

        /*
        // Traditional Hello World
        public class HelloWorld {
            public static void main(String[] args) {
                System.out.println("Hello, World!");
            }
        }

        // Java 22 simplified version (in a separate file)
        void main() {
            System.out.println("Hello, World!");
        }
        */

        // 4. String Templates – JEP 459 (Second Preview)
        // NOTE: This feature was in preview in Java 21 and 22, but was dropped and not finalized
        // It may return in a future version with modifications
        System.out.println("\n4. String Templates (Preview - later dropped):");
        System.out.println("   This feature was not finalized and may return in modified form");

        /*
        String name = "John";
        int age = 30;

        // Traditional string concatenation
        String msg1 = "My name is " + name + " and I am " + age + " years old";

        // Java 22 String Templates (preview feature - not finalized)
        String msg2 = STR."My name is \{name} and I am \{age} years old";
        System.out.println(msg2);
        */

        // 5. Structured Concurrency – JEP 462 (Second Preview)
        // Simplifies concurrent programming by treating groups of related tasks as a single unit
        // Improves reliability and observability of concurrent code
        System.out.println("\n5. Structured Concurrency:");
        System.out.println("   Treats related concurrent tasks as a single unit of work");
        System.out.println("   Improves error handling and resource management");

        /*
        import java.util.concurrent.StructuredTaskScope;

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String> user = scope.fork(() -> fetchUser());
            Future<Integer> order = scope.fork(() -> fetchOrder());

            scope.join();           // Wait for all tasks
            scope.throwIfFailed();  // Propagate errors

            // Both tasks succeeded
            System.out.println(user.resultNow() + " " + order.resultNow());
        }
        */

        // 6. Scoped Values – JEP 464 (Second Preview)
        // Alternative to ThreadLocal with better performance for virtual threads
        // Immutable data shared within a bounded scope
        System.out.println("\n6. Scoped Values:");
        System.out.println("   Better alternative to ThreadLocal for virtual threads");
        System.out.println("   Immutable data shared within bounded scopes");

        /*
        import java.util.concurrent.ScopedValue;

        private static final ScopedValue<String> USERNAME = ScopedValue.newInstance();

        ScopedValue.where(USERNAME, "john").run(() -> {
            // USERNAME.get() returns "john" in this scope
            processRequest();
        });
        // USERNAME is not available outside the scope
        */

        // 7. Class-File API – JEP 457 (Preview)
        // Standard API for parsing, generating, and transforming Java class files
        // Replaces reliance on third-party libraries like ASM
        // Useful for frameworks and tools that need to manipulate bytecode
        System.out.println("\n7. Class-File API:");
        System.out.println("   Standard API for parsing, generating, and transforming class files");
        System.out.println("   Eliminates need for libraries like ASM for bytecode manipulation");

        /*
        import java.lang.classfile.*;

        // Example: Reading and transforming a class file
        ClassFile cf = ClassFile.of();
        byte[] bytes = ...;  // class file bytes

        ClassModel classModel = cf.parse(bytes);
        byte[] newBytes = cf.transform(classModel,
            ClassTransform.transformingMethodBodies(
                (cob, coe) -> {
                    // Transform bytecode here
                }
            )
        );
        */

        System.out.println("\n=== Preview Features Summary ===");
        System.out.println("1. Stream Gatherers (Preview)");
        System.out.println("2. Statements Before super() (Preview)");
        System.out.println("3. Implicitly Declared Classes (Second Preview)");
        System.out.println("4. String Templates (Second Preview - later dropped)");
        System.out.println("5. Structured Concurrency (Second Preview)");
        System.out.println("6. Scoped Values (Second Preview)");
        System.out.println("7. Class-File API (Preview)");
        System.out.println("\nNote: Preview features require --enable-preview flag");
    }

    // Example: Statements Before super() - JEP 447 (Preview)
    static class Parent {
        protected final String config;

        Parent(String config) {
            this.config = config;
        }
    }

    static class Child extends Parent {
        // Java 22 Preview: Can execute statements before super()
        Child(String rawConfig) {
            // Previously this would cause compilation error
            // Now you can validate/transform arguments before calling super()
            String processed = rawConfig.trim().toUpperCase();
            if (processed.isEmpty()) {
                throw new IllegalArgumentException("Config cannot be empty");
            }
            super(processed); // super() no longer needs to be first statement
            System.out.println("Child initialized with config: " + config);
        }
    }

    // Example: Custom gatherer (advanced usage)
    /*
    static <T> Gatherer<T, ?, T> distinctByKey(Function<T, ?> keyExtractor) {
        return Gatherer.of(
            HashSet::new,  // initializer - creates the state
            Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                if (state.add(keyExtractor.apply(element))) {
                    downstream.push(element);
                }
                return true;
            })
        );
    }

    // Usage:
    records.stream()
        .gather(distinctByKey(Person::name))
        .toList();
    */
}
