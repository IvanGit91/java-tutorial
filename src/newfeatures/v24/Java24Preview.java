package newfeatures.v24;

public class Java24Preview {
    public static void main(String[] args) {
        System.out.println("=== Java 24 Preview Features ===\n");

        // 1. Primitive Patterns in instanceof and switch – JEP 488 (Second Preview)
        // Extended pattern matching for primitive types
        // Refinements from first preview in Java 23
        System.out.println("1. Primitive Patterns (Second Preview):");

        /*
        Object value = 42;

        // Pattern matching with primitive types
        switch (value) {
            case int i when i > 0    -> System.out.println("Positive: " + i);
            case int i when i < 0    -> System.out.println("Negative: " + i);
            case int i               -> System.out.println("Zero");
            case long l              -> System.out.println("Long: " + l);
            case double d            -> System.out.println("Double: " + d);
            case String s            -> System.out.println("String: " + s);
            default                  -> System.out.println("Other type");
        }

        // Primitive patterns in instanceof
        if (value instanceof int i && i > 0) {
            System.out.println("Positive integer: " + i);
        }
        */

        System.out.println("   Pattern matching with int, long, double, etc.");
        System.out.println("   Works in both instanceof and switch");
        System.out.println("   Can use guards (when clauses)");

        // 2. Flexible Constructor Bodies – JEP 492 (Second Preview)
        // More flexible initialization in constructors
        // Continued refinement from Java 23
        System.out.println("\n2. Flexible Constructor Bodies (Second Preview):");
        System.out.println("   Enhanced constructor initialization");
        System.out.println("   More statements before super()/this()");
        System.out.println("   See example classes below");

        // 3. Module Import Declarations – JEP 489 (Second Preview)
        // Import all packages from a module
        // Refinements from first preview
        System.out.println("\n3. Module Import Declarations (Second Preview):");
        System.out.println("   Import entire modules with one statement");
        System.out.println("   Reduces boilerplate in modular applications");

        /*
        // Import all public APIs from java.base module
        import module java.base;

        // Import from multiple modules
        import module java.sql;
        import module java.xml;

        // Now all public APIs from these modules are available
        */

        // 4. Structured Concurrency – JEP 491 (Fourth Preview or Final?)
        // Managing groups of related concurrent tasks
        // Further refinements
        System.out.println("\n4. Structured Concurrency:");
        System.out.println("   Improved API for concurrent task management");
        System.out.println("   Better error propagation");
        System.out.println("   Clearer resource management");

        /*
        import java.util.concurrent.StructuredTaskScope;

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var user = scope.fork(() -> fetchUserFromDB());
            var orders = scope.fork(() -> fetchUserOrders());
            var preferences = scope.fork(() -> fetchUserPreferences());

            scope.join();
            scope.throwIfFailed();

            // All tasks completed successfully
            return new UserProfile(
                user.resultNow(),
                orders.resultNow(),
                preferences.resultNow()
            );
        }
        */

        // 5. Scoped Values – JEP 490 (Fourth Preview or Final?)
        // Efficient alternative to ThreadLocal
        // Optimized for virtual threads
        System.out.println("\n5. Scoped Values:");
        System.out.println("   Share immutable data across scopes");
        System.out.println("   Better performance than ThreadLocal");
        System.out.println("   Ideal for virtual threads");

        /*
        import java.util.concurrent.ScopedValue;

        private static final ScopedValue<Request> CURRENT_REQUEST = ScopedValue.newInstance();
        private static final ScopedValue<User> CURRENT_USER = ScopedValue.newInstance();

        // Bind values for a scope
        ScopedValue.where(CURRENT_REQUEST, request)
                   .where(CURRENT_USER, user)
                   .run(() -> {
            // Both values are available in this scope and child scopes
            processRequest();
        });
        // Values are not available outside the scope
        */

        // 6. Implicitly Declared Classes – JEP 495 (Preview)
        // Simplified Java for beginners
        // Continued evolution
        System.out.println("\n6. Implicitly Declared Classes (Preview):");
        System.out.println("   Simplified main method for learning");
        System.out.println("   void main() is sufficient");
        System.out.println("   No need for class declaration in simple programs");

        System.out.println("\n=== Preview Features Summary ===");
        System.out.println("1. Primitive Patterns (Second Preview)");
        System.out.println("2. Flexible Constructor Bodies (Second Preview)");
        System.out.println("3. Module Import Declarations (Second Preview)");
        System.out.println("4. Structured Concurrency (Preview/Final)");
        System.out.println("5. Scoped Values (Preview/Final)");
        System.out.println("6. Implicitly Declared Classes (Preview)");
        System.out.println("\nNote: Preview features require --enable-preview flag");
    }

    // Example: Flexible Constructor Bodies
    static class Configuration {
        private final String environment;
        private final int port;
        private final boolean secure;

        Configuration(String env, String portStr, boolean secure) {
            // Validate and transform before calling this()
            String normalizedEnv = env.trim().toLowerCase();
            if (!normalizedEnv.matches("dev|test|prod")) {
                throw new IllegalArgumentException("Invalid environment");
            }

            int parsedPort;
            try {
                parsedPort = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                parsedPort = 8080; // default
            }

            this(normalizedEnv, parsedPort, secure);
        }

        Configuration(String environment, int port, boolean secure) {
            this.environment = environment;
            this.port = port;
            this.secure = secure;
        }
    }
}
