package newfeatures.v10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Java10 {
    public static void main(String[] args) {
        // 2. Local Variable Type Inference
        // old
        String message = "Good bye, Java 9";
        // new
        var messageNew = "Hello, Java 10";
        //assertTrue(messageNew instanceof String);

        // old
        Map<Integer, String> map = new HashMap<>();
        // new
        var idToNameMap = new HashMap<Integer, String>();

        // As mentioned earlier, var won’t work without the initializer:
        // Nor would it work if initialized with null:
        // It won’t work for non-local variables:

        // Another situation where it’s best to avoid var is in streams with long pipeline:

        // 3. Unmodifiable Collections
        // 3.1. copyOf() - It returns the unmodifiable copy of the given Collection:
        List<Integer> someIntList = new ArrayList<>();
        List<Integer> copyList = List.copyOf(someIntList);

        // 3.2. toUnmodifiable*()
        // java.util.stream.Collectors get additional methods to collect a Stream into unmodifiable List, Map or Set:
        List<Integer> evenList = someIntList.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toUnmodifiableList());
        evenList.add(4);

        // 4. Optional*.orElseThrow()
        // which doesn’t take any argument and throws NoSuchElementException if no value is present:
        Integer firstEven = someIntList.stream()
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElseThrow();
    }
}
