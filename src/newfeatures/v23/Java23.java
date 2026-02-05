package newfeatures.v23;

import java.util.List;

public class Java23 {
    public static void main(String[] args) {
        System.out.println("=== Java 23 Final Features ===\n");

        // 1. Markdown Documentation Comments – JEP 467
        // Allows writing documentation comments using Markdown syntax
        // Makes JavaDoc more readable and easier to write
        System.out.println("1. Markdown Documentation Comments:");
        System.out.println("   Write JavaDoc using Markdown syntax");
        System.out.println("   Supports headers, lists, code blocks, emphasis, etc.");
        System.out.println("   See example methods below\n");

        /*
        Traditional JavaDoc:
        /**
         * Returns the sum of two numbers.
         * <p>
         * Example usage:
         * <pre>{@code
         *   int result = add(5, 3);
         * }</pre>
         *
         * @param a the first number
         * @param b the second number
         * @return the sum
         *‍/

        Markdown JavaDoc (Java 23):
        ///
        /// Returns the sum of two numbers.
        ///
        /// Example usage:
        /// ```java
        /// int result = add(5, 3);
        /// ```
        ///
        /// @param a the first number
        /// @param b the second number
        /// @return the sum
        ///
        */

        // 2. Deprecate Memory-Access Methods in sun.misc.Unsafe – JEP 471
        // Deprecates the memory-access methods in sun.misc.Unsafe for removal
        // Encourages use of standard alternatives like VarHandle and MemorySegment
        System.out.println("2. Deprecate sun.misc.Unsafe Memory Methods:");
        System.out.println("   Memory-access methods marked for removal");
        System.out.println("   Use VarHandle or Foreign Function & Memory API instead");
        System.out.println("   Improves safety and maintainability\n");

        /*
        // Deprecated approach (sun.misc.Unsafe):
        Unsafe unsafe = Unsafe.getUnsafe();
        long address = unsafe.allocateMemory(100);
        unsafe.putInt(address, 42);

        // Modern approach (Foreign Function & Memory API):
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(100);
            segment.set(ValueLayout.JAVA_INT, 0, 42);
        }
        */

        // 3. ZGC: Generational Mode by Default – JEP 474
        // Makes generational mode the default for the Z Garbage Collector
        // Improves performance by separating young and old objects
        // Reduces memory footprint and improves throughput
        System.out.println("3. ZGC Generational Mode by Default:");
        System.out.println("   Generational ZGC is now the default");
        System.out.println("   Better performance for most workloads");
        System.out.println("   Lower memory footprint");
        System.out.println("   Use -XX:-ZGenerational to disable if needed\n");

        System.out.println("=== Final Features Summary ===");
        System.out.println("1. Markdown Documentation Comments");
        System.out.println("2. Deprecate sun.misc.Unsafe Memory Methods");
        System.out.println("3. ZGC: Generational Mode by Default");
        System.out.println("\nFor preview features, see Java23Preview.java");
    }

    /// Example of Markdown documentation comment in Java 23
    ///
    /// This method calculates the **factorial** of a number.
    ///
    /// ## Example
    ///
    /// ```java
    /// long result = factorial(5);  // returns 120
    /// ```
    ///
    /// ## Parameters
    /// - `n` - the number to calculate factorial for
    ///
    /// ## Returns
    /// The factorial of n
    ///
    /// ## Throws
    /// - `IllegalArgumentException` if n is negative
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    /// Calculates the sum of a list of integers.
    ///
    /// ### Features:
    /// - Handles empty lists
    /// - Returns `0` for empty list
    /// - Uses streams for calculation
    ///
    /// @param numbers the list of numbers to sum
    /// @return the sum of all numbers
    public static int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }
}
