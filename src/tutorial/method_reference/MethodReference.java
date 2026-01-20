package tutorial.method_reference;

import tutorial.models.computer.Computer;
import tutorial.models.computer.ComputerUtils;
import tutorial.models.computer.MacbookPro;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/*
Advantages of the Double Colon (::) Operator
- Improved Readability: Reduces boilerplate code by replacing verbose lambda expressions.
- Functional Programming Style: Fits naturally into Java's functional programming paradigm introduced in Java 8.
- Reusability: Methods and constructors can be reused without duplicating logic.
- Consistency: Works seamlessly with the Stream API and other functional interfaces.
*/

public class MethodReference {

    static InterfaceComputer c = Computer::new;
    // Constructors with two parameters
    static BiFunction<Integer, String, Computer> c4Function = Computer::new;
    static TriFunction<Integer, String, Integer, Computer> c6Function = Computer::new;
    static Function<Integer, Computer[]> computerCreator = Computer[]::new;

    public static void main(String[] args) {
        //Computer c1 = new Computer();

        // Lambdas
        Comparator<Computer> comp1 = (Computer c1, Computer c2) -> c1.getAge().compareTo(c2.getAge());

        // With type inference
        Comparator<Computer> comp2 = (c1, c2) -> c1.getAge().compareTo(c2.getAge());

        // Double colon
        Comparator<Computer> comp3 = Comparator.comparing(Computer::getAge);

        // Get age double colon
        Computer c1 = new Computer(2015, "white");
        Function<Computer, Integer> getAge = Computer::getAge;
        Integer computerAge = getAge.apply(c1);

        // Use of a static method
        List<Computer> inventory = Arrays.asList(new Computer(2015, "white", 35), new Computer(2009, "black", 65));
        inventory.forEach(ComputerUtils::repair);

        // Use of an existent method
        Computer cc1 = new Computer(2015, "white");
        Computer cc2 = new Computer(2009, "black");
        Computer cc3 = new Computer(2014, "black");
        Arrays.asList(cc1, cc2, cc3).forEach(System.out::print);

        // Use of internal method
        Computer c4 = new Computer(2015, "white", 100);
        Computer c5 = new Computer(2009, "black", 100);
        List<Computer> inventory2 = Arrays.asList(c4, c5);
        inventory2.forEach(Computer::turnOnPc);

        // Subclass and superclass
        MacbookPro mac = new MacbookPro(2015, "white", 100);
        mac.calculateValue(999.99);

        // Creation of object
        Computer computer = c.create();

        // With constructor
        Computer c7 = c4Function.apply(2013, "white");

        // With constructor 3 paramaters
        Computer c8 = c6Function.apply(2008, "black", 90);

        // Array di 5 elementi
        Computer[] computerArray = computerCreator.apply(5);
    }


    @FunctionalInterface
    public interface InterfaceComputer {
        Computer create();
    }

    // If constructor has more than two parameters
    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c) -> after.apply(apply(a, b, c));
        }
    }

}
