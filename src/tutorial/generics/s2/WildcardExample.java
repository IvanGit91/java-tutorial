package tutorial.generics.s2;

import java.util.List;

public class WildcardExample {
    public static void main(String[] args) {
        // Create Lists of different Number types
        List<Integer> intList = List.of(1, 2, 3);
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);

        // Process lists using a method with List<? extends Number>
        printNumbers(intList);
        printNumbers(doubleList);
    }

    // Method accepting a List of unknown subtype of Number
    public static void printNumbers(List<? extends Number> numbers) {
        System.out.println("Numbers:");
        for (Number number : numbers) {
            System.out.println(number);
        }
    }
}
