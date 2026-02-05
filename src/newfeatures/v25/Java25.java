package newfeatures.v25;

public class Java25 {
    public static void main(String[] args) {
        System.out.println("=== Java 25 Final Features ===");
        System.out.println("Released: September 16, 2025 (LTS Release)\n");

        // 1. Scoped Values – JEP 506 (Final)
        // Finalized after 4 preview rounds in Java 21-24
        // Modern replacement for ThreadLocal, especially beneficial for virtual threads
        System.out.println("1. Scoped Values (Final - JEP 506):");
        System.out.println("   Share immutable data across method calls within a scope");
        System.out.println("   Better performance than ThreadLocal");
        System.out.println("   Optimized for virtual threads");
        System.out.println("   Automatically cleaned up when scope exits\n");

        /*
        import java.util.concurrent.ScopedValue;

        private static final ScopedValue<User> CURRENT_USER = ScopedValue.newInstance();
        private static final ScopedValue<RequestContext> REQUEST_CONTEXT = ScopedValue.newInstance();

        // Bind values within a scope
        ScopedValue.where(CURRENT_USER, authenticatedUser)
                   .where(REQUEST_CONTEXT, context)
                   .run(() -> {
            // Values are available here and in all called methods
            processRequest();
            // Values are automatically unbound when scope exits
        });

        // Benefits over ThreadLocal:
        // - Immutable (thread-safe by design)
        // - Bounded lifetime (scope-based)
        // - Better performance with virtual threads
        // - No cleanup required (automatic)
        */

        // 2. Flexible Constructor Bodies – JEP 513 (Final)
        // Finalized after previews in Java 22, 23, and 24
        // Allows statements before super() or this() calls
        System.out.println("2. Flexible Constructor Bodies (Final - JEP 513):");
        System.out.println("   Execute statements before super() or this()");
        System.out.println("   Validate and transform arguments before delegation");
        System.out.println("   More natural constructor code\n");

        ConfigurableService service = new ConfigurableService(" TEST-ENV ", "9000");
        System.out.println("   Example - Service created: " +
            service.getEnvironment() + ":" + service.getPort() + "\n");

        // 3. Module Import Declarations – JEP 511 (Final)
        // Import all packages exported by a module with one statement
        System.out.println("3. Module Import Declarations (Final - JEP 511):");
        System.out.println("   Import entire modules: import module java.base;");
        System.out.println("   Reduces import boilerplate significantly");
        System.out.println("   Better support for modular development\n");

        /*
        // Before Java 25:
        import java.util.List;
        import java.util.Map;
        import java.util.Set;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.stream.Stream;
        import java.util.stream.Collectors;
        // ... potentially dozens more imports

        // Java 25:
        import module java.base;
        // All public APIs from java.base module are now available!

        // Can also import multiple modules:
        import module java.sql;
        import module java.xml;
        */

        // 4. Compact Source Files and Instance Main Methods – JEP 512 (Final)
        // Simplifies Java programs for educational purposes
        // Finalized in Java 25
        System.out.println("4. Compact Source Files & Instance Main (Final - JEP 512):");
        System.out.println("   Simplified entry point for learning Java");
        System.out.println("   void main() { ... } is sufficient");
        System.out.println("   No class declaration needed for simple programs\n");

        /*
        // Traditional Java program:
        public class HelloWorld {
            public static void main(String[] args) {
                System.out.println("Hello, World!");
            }
        }

        // Java 25 - can now write just:
        void main() {
            println("Hello, World!");
        }

        // Benefits:
        // - Easier for beginners to learn Java
        // - Less boilerplate for simple programs
        // - Smoother on-ramp to Java programming
        */

        // 5. Key Derivation Function API – JEP 510 (Final)
        // New API for deriving cryptographic keys from passwords or other secrets
        System.out.println("5. Key Derivation Function API (Final - JEP 510):");
        System.out.println("   Standardized API for password-based key derivation");
        System.out.println("   Supports PBKDF2, scrypt, Argon2, and other KDFs");
        System.out.println("   Better security for password storage\n");

        /*
        import javax.crypto.KDF;
        import javax.crypto.spec.KDFParameterSpec;

        // Derive a key from a password using PBKDF2
        KDF kdf = KDF.getInstance("PBKDF2WithHmacSHA256");
        KDFParameterSpec params = KDFParameterSpec.ofPBKDF2()
            .password(password)
            .salt(salt)
            .iterations(100000)
            .keyLength(256)
            .build();

        SecretKey derivedKey = kdf.deriveKey("AES", params);
        */

        // 6. Compact Object Headers – JEP 519
        // Reduces object header size from 128 bits to 64 bits
        // Improves memory efficiency
        System.out.println("6. Compact Object Headers (JEP 519):");
        System.out.println("   Reduces object header overhead");
        System.out.println("   Better memory efficiency (smaller heap)");
        System.out.println("   Improved cache locality");
        System.out.println("   Enabled with -XX:+UseCompactObjectHeaders\n");

        // 7. Generational Shenandoah – JEP 521 (Final)
        // Generational mode for Shenandoah GC
        System.out.println("7. Generational Shenandoah (Final - JEP 521):");
        System.out.println("   Generational mode for Shenandoah GC");
        System.out.println("   Better performance for most workloads");
        System.out.println("   Lower pause times");
        System.out.println("   Use -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational\n");

        System.out.println("=== Additional Features ===");
        System.out.println("- JEP 514: Ahead-of-Time Command-Line Ergonomics");
        System.out.println("- JEP 515: Ahead-of-Time Method Profiling");
        System.out.println("- JEP 518: JFR Cooperative Sampling");
        System.out.println("- JEP 520: JFR Method Timing & Tracing");
        System.out.println("- JEP 503: Remove 32-bit x86 Port");

        System.out.println("\n=== Final Features Summary ===");
        System.out.println("1. Scoped Values (JEP 506)");
        System.out.println("2. Flexible Constructor Bodies (JEP 513)");
        System.out.println("3. Module Import Declarations (JEP 511)");
        System.out.println("4. Compact Source Files & Instance Main (JEP 512)");
        System.out.println("5. Key Derivation Function API (JEP 510)");
        System.out.println("6. Compact Object Headers (JEP 519)");
        System.out.println("7. Generational Shenandoah (JEP 521)");
        System.out.println("\nFor preview features, see Java25Preview.java");
    }

    // Example: Flexible Constructor Bodies (JEP 513)
    static class BaseService {
        protected final String environment;
        protected final int port;

        BaseService(String environment, int port) {
            this.environment = environment;
            this.port = port;
        }
    }

    static class ConfigurableService extends BaseService {
        private final String normalizedEnv;

        ConfigurableService(String rawEnv, String portStr) {
            // Flexible constructor body - statements before super()
            // This was previewed in Java 22, 23, 24 and finalized in Java 25

            String trimmed = rawEnv.trim();
            String normalized = trimmed.toLowerCase();

            // Validate before calling super()
            if (normalized.isEmpty()) {
                throw new IllegalArgumentException("Environment cannot be empty");
            }

            // Parse and validate port
            int port;
            try {
                port = Integer.parseInt(portStr);
                if (port < 0 || port > 65535) {
                    port = 8080;
                }
            } catch (NumberFormatException e) {
                port = 8080;
            }

            // Store in instance field before super() - now allowed!
            this.normalizedEnv = normalized;

            // Call super() - no longer needs to be first statement
            super(normalized, port);
        }

        public String getEnvironment() {
            return environment;
        }

        public int getPort() {
            return port;
        }
    }
}
