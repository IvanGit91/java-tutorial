package newfeatures.v24;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherers;

public class Java24 {
    public static void main(String[] args) {
        System.out.println("=== Java 24 Final Features ===\n");

        // 1. Stream Gatherers – JEP 485 (Final)
        // ==========================================
        // Gatherers are a new type of intermediate stream operation that allow for
        // custom transformations that were previously difficult or impossible with
        // existing operations like map, filter, or flatMap.
        //
        // Think of gatherers as a way to process stream elements with:
        // - State (unlike stateless operations like map)
        // - Custom logic (more flexible than reduce)
        // - Intermediate results (can emit 0, 1, or many elements per input)
        //
        // After being in preview in Java 22 and 23, gatherers are now finalized!

        System.out.println("1. Stream Gatherers (Final):\n");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        // WINDOWING OPERATIONS
        // ====================
        // A "window" is a subset of consecutive elements from the stream.
        // Windowing is useful for analyzing patterns, calculating moving averages,
        // batch processing, or any operation that needs to look at groups of elements.

        System.out.println("   FIXED WINDOWS:");
        System.out.println("   --------------");
        System.out.println("   Fixed windows divide the stream into non-overlapping chunks");
        System.out.println("   of a specific size. Each element appears in exactly ONE window.");
        System.out.println();
        System.out.println("   Input:  [1, 2, 3, 4, 5, 6, 7, 8]");
        System.out.println("   Window size: 3");

        List<List<Integer>> fixedWindows = numbers.stream()
            .gather(Gatherers.windowFixed(3))
            .toList();

        System.out.println("   Output: " + fixedWindows);
        System.out.println("           [[1, 2, 3], [4, 5, 6], [7, 8]]");
        System.out.println("           └─ Win 1 ─┘ └─ Win 2 ─┘ └Win 3┘");
        System.out.println("   Note: Last window may be incomplete if stream size isn't divisible by window size");
        System.out.println();

        // Practical example: Batch processing
        System.out.println("   Use case - Batch processing emails:");
        List<String> emails = Arrays.asList("a@ex.com", "b@ex.com", "c@ex.com", "d@ex.com", "e@ex.com");
        emails.stream()
            .gather(Gatherers.windowFixed(2))
            .forEach(batch -> System.out.println("   Sending batch: " + batch));
        System.out.println();

        System.out.println("   SLIDING WINDOWS:");
        System.out.println("   ----------------");
        System.out.println("   Sliding windows create overlapping chunks that 'slide' one element");
        System.out.println("   at a time. Each element (except edges) appears in MULTIPLE windows.");
        System.out.println();
        System.out.println("   Input:  [1, 2, 3, 4, 5, 6, 7, 8]");
        System.out.println("   Window size: 3");

        List<List<Integer>> slidingWindows = numbers.stream()
            .gather(Gatherers.windowSliding(3))
            .toList();

        System.out.println("   Output: " + slidingWindows);
        System.out.println("           [[1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5, 6], [5, 6, 7], [6, 7, 8]]");
        System.out.println("           └─ Win 1 ─┘");
        System.out.println("              └─ Win 2 ─┘  (slides by 1)");
        System.out.println("                 └─ Win 3 ─┘  (slides by 1)");
        System.out.println();

        // Practical example: Moving average
        System.out.println("   Use case - Calculate 3-day moving average of stock prices:");
        List<Double> stockPrices = Arrays.asList(100.0, 102.0, 101.0, 105.0, 108.0);
        List<Double> movingAverage = stockPrices.stream()
            .gather(Gatherers.windowSliding(3))
            .map(window -> window.stream().mapToDouble(d -> d).average().orElse(0.0))
            .toList();
        System.out.println("   Prices: " + stockPrices);
        System.out.println("   3-day moving avg: " + movingAverage);
        System.out.println();

        // FOLD OPERATION
        // ==============
        System.out.println("   FOLD:");
        System.out.println("   -----");
        System.out.println("   Accumulates all elements into a single result");
        System.out.println("   Similar to reduce(), but as an intermediate operation");

        List<String> words = Arrays.asList("Java", "24", "is", "great");
        String folded = words.stream()
            .gather(Gatherers.fold(() -> "", (acc, word) -> acc + word + " "))
            .findFirst()
            .orElse("");
        System.out.println("   Words: " + words);
        System.out.println("   Folded: '" + folded.trim() + "'");
        System.out.println();

        // SCAN OPERATION
        // ==============
        System.out.println("   SCAN:");
        System.out.println("   -----");
        System.out.println("   Like fold, but emits intermediate results at each step");
        System.out.println("   Useful for running totals, cumulative sums, etc.");

        List<Integer> runningSum = numbers.stream()
            .gather(Gatherers.scan(() -> 0, (acc, n) -> acc + n))
            .toList();
        System.out.println("   Numbers: " + numbers);
        System.out.println("   Running sum: " + runningSum);
        System.out.println("   [0, 1, 3, 6, 10, 15, 21, 28, 36]");
        System.out.println("    ↑  ↑  ↑  ↑   ↑   ↑   ↑   ↑   ↑");
        System.out.println("    0  +1 +2 +3  +4  +5  +6  +7  +8");
        System.out.println();

        // Practical example: Running balance
        System.out.println("   Use case - Bank account running balance:");
        List<Integer> transactions = Arrays.asList(100, -20, -15, 50, -10);  // +deposit, -withdrawal
        List<Integer> balance = transactions.stream()
            .gather(Gatherers.scan(() -> 1000, (bal, trans) -> bal + trans))  // Start with $1000
            .toList();
        System.out.println("   Transactions: " + transactions);
        System.out.println("   Balance after each: " + balance);
        System.out.println();

        // MAP CONCURRENT
        // ==============
        System.out.println("   MAP CONCURRENT:");
        System.out.println("   ---------------");
        System.out.println("   Process elements concurrently while preserving order");

        List<String> items = Arrays.asList("a", "b", "c", "d");
        List<String> mapped = items.stream()
            .gather(Gatherers.mapConcurrent(10, String::toUpperCase))
            .toList();
        System.out.println("   Mapped concurrently: " + mapped);

        // 2. Class-File API – JEP 484 (Final)
        // Standard API for parsing, generating, and transforming Java class files
        // Finalized after being in preview
        System.out.println("\n2. Class-File API (Final):");
        System.out.println("   Parse, generate, and transform class files");
        System.out.println("   No longer need external libraries like ASM");
        System.out.println("   Better integration with the Java platform");

        /*
        import java.lang.classfile.*;
        import java.lang.constant.*;

        // Read and analyze a class file
        ClassModel classModel = ClassFile.of().parse(classBytes);

        // Transform a class file
        byte[] transformedBytes = ClassFile.of().transform(classModel,
            ClassTransform.transformingMethods(
                MethodTransform.transformingCode(
                    (codeBuilder, codeElement) -> {
                        // Transform bytecode here
                        codeBuilder.with(codeElement);
                    }
                )
            )
        );

        // Generate a new class
        byte[] newClass = ClassFile.of().build(
            ClassDesc.of("com.example", "MyClass"),
            classBuilder -> {
                classBuilder.withFlags(AccessFlag.PUBLIC);
                classBuilder.withMethod("hello", MethodTypeDesc.of(CD_void),
                    Classfile.ACC_PUBLIC | Classfile.ACC_STATIC,
                    methodBuilder -> {
                        // Add method body
                    }
                );
            }
        );
        */

        // 3. Late Barrier Expansion for G1 – JEP 475
        // Performance improvement for G1 garbage collector
        // Optimizes barrier expansion timing during compilation
        System.out.println("\n3. Late Barrier Expansion for G1:");
        System.out.println("   Improves G1 GC performance");
        System.out.println("   Better compiler optimizations");
        System.out.println("   Reduced overhead for write barriers");
        System.out.println("   Automatic improvement - no code changes needed");

        System.out.println("\n=== Final Features Summary ===");
        System.out.println("1. Stream Gatherers (Final)");
        System.out.println("2. Class-File API (Final)");
        System.out.println("3. Late Barrier Expansion for G1 GC");
        System.out.println("\nFor preview features, see Java24Preview.java");
    }

    // Example: Custom gatherer for distinct by key
    public static void customGathererExample() {
        record Person(String name, int age) {}

        List<Person> people = Arrays.asList(
            new Person("Alice", 30),
            new Person("Bob", 25),
            new Person("Alice", 35),  // Duplicate name
            new Person("Charlie", 30)
        );

        // Using gatherers to get distinct by name
        // This would require a custom gatherer implementation
        System.out.println("Custom gatherer examples would go here");
    }
}
