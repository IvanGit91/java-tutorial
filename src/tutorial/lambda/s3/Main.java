package tutorial.lambda.s3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
interface TwoArgInterface {
    int operation(int a, int b);
}

@FunctionalInterface
interface OneArgInterface {
    String makeIt(String s);

}


public class Main {

    static UnaryOperator<String> trimFunction = String::trim;
    static UnaryOperator<String> toUpperCaseFunction = String::toUpperCase;


    public static void main(String[] args) {
        // this is lambda expression custom
        TwoArgInterface plusOperation = (a, b) -> a + b;
        TwoArgInterface plusOperationRet = (a, b) -> {
            return (a + b);
        };
        System.out.println("Sum of 10,34 : " + plusOperation.operation(10, 34));
        System.out.println("Sum of 10,34 : " + plusOperationRet.operation(10, 34));

        // with existing lambda
        IntBinaryOperator plusOperation2 = (a, b) -> a + b;
        System.out.println("Sum of 10,34 : " + plusOperation2.applyAsInt(10, 34));

        OneArgInterface printOp = (a) -> a;
        System.out.println("Stampa : " + printOp.makeIt("ok"));

        List<String> strs = Stream.of(" a ", " b ").map(trimFunction.andThen(toUpperCaseFunction)).collect(Collectors.toList());
        strs.forEach(p -> System.out.println("TRIM: " + p));


        List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        final IntWrapper dWrapper = new IntWrapper();

        strings.forEach(e -> {
            if (e.equals("a"))
                dWrapper.setValue(7);
        });
        System.out.println("AS: " + dWrapper.getValue());


        Double[] dbl = {60d, 30d, 10d};

        Double val = Arrays.asList(dbl).stream().reduce((subtotal, element) -> subtotal - element).get();
        System.out.println("Differenza: " + val);

    }


    public String custom(String s) {
        return s + "ok";
    }


}


class IntWrapper {
    private int value;

    public IntWrapper() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
