package tutorial.lambda.s1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("boxing")
public class LambdaGame {

    // consider a list of strings
    static List<String> list =
            Arrays.asList("Cristiano", "Michele", "Sergio", "Giuseppe", "Stefano");
    // we have used the following @FunctionalInterfaces
    static BinaryOperator<Integer> accumulator = (a, b) -> a + b;
    static Function<String, Integer> lengthMapper = (a) -> a.length();
    static Function<String, String> devowelizerMapper = (p) -> p.replaceAll("[aeiou]", "");
    // let's rewrite it without inlining anonymous classes
    static Function<String, String> anonymousDevowelizerMapperClass = p -> p.replaceAll("[aeiou]", "");
    static Function<String, Integer> anonymousLengthMapperClass = new Function<String, Integer>() {

        @Override
        public Integer apply(String a) {
            return a.length();
        }
    };
    static BinaryOperator<Integer> anonymousAccumulatorClass = new BinaryOperator<Integer>() {

        @Override
        public Integer apply(Integer a, Integer b) {
            return a + b;
        }
    };

    // this class runs at least with Java 8
    public static void main(String[] args) {
        howManyConsonants();
        howManyConsonantsWithoutTypeInference();
        howManyConsonantsWithName();
        eachStepOfHowManyConsonants();
        howManyConsonantsWithMethodReferences();
        howManyConsonantsPseudoJava7();
        howManyConsonantsPseudoJava7Inlineless();
        howManyConsonantsJava7();
    }

    // let's count with lambda how many consonants in all the names
    public static void howManyConsonants() {

        Integer out = list.stream() // process with a stream
                .map((p) -> p.replaceAll("[aeiou]", "")) // strip all (lower case) vowels from each name
                .map((a) -> a.length()) // count the length of the remaining word
                .reduce((a, b) -> a + b) // sum each of the previously computed lengths
                .get(); // get the result

        System.out.println("howManyConsonants: there are " + out + " consonants");
    }

    // that without type inference looks like
    public static void howManyConsonantsWithoutTypeInference() {
        Integer out = list.stream()
                .map((String p) -> p.replaceAll("[aeiou]", ""))
                .map((String a) -> a.length())
                .reduce((Integer a, Integer b) -> a + b)
                .get();

        System.out.println("howManyConsonantsWithoutTypeInference: there are " + out + " consonants");
    }

    // and we can rewrite it using these above
    public static void howManyConsonantsWithName() {
        Integer out = list.stream()
                .map(devowelizerMapper)
                .map(lengthMapper)
                .reduce(accumulator)
                .get();

        System.out.println("howManyConsonantsWithName: there are " + out + " consonants");
    }

    // we have used "streams" to process the output, these are the individual
    // steps
    public static void eachStepOfHowManyConsonants() {

        Stream<String> stream = list.stream();
        Stream<String> devowelizedStream = stream.map(devowelizerMapper);
        Stream<Integer> lengthStream = devowelizedStream.map(lengthMapper);
        Optional<Integer> reduce = lengthStream.reduce(accumulator);
        Integer out = reduce.get();

        System.out.println("eachStepOfHowManyConsonants: there are " + out + " consonants");
    }

    // methods of a class can be now referenced and used in place of a lambda
    public static void howManyConsonantsWithMethodReferences() {
        Integer out = list.stream()
                .map(LambdaMethods::devowelizerMapper)
                .map(LambdaMethods::lengthMapper)
                .reduce(LambdaMethods::accumulator)
                .get();

        System.out.println("howManyConsonantsWithMethodReferences: there are " + out + " consonants");
    }

    // if we don't have java 8? we would have used anonymous classes
    public static void howManyConsonantsPseudoJava7() {
        Integer out =
                list.stream().map(new Function<String, String>() {

                    @Override
                    public String apply(String p) {
                        return p.replaceAll("[aeiou]", "");
                    }
                }).map(new Function<String, Integer>() {

                    @Override
                    public Integer apply(String a) {
                        return a.length();
                    }
                }).reduce(new BinaryOperator<Integer>() {

                    @Override
                    public Integer apply(Integer a, Integer b) {
                        return a + b;
                    }
                }).get();

        System.out.println("howManyConsonantsPseudoJava7: there are " + out + " consonants");
    }

    // and then re-implement without the inline anonymous classes
    public static void howManyConsonantsPseudoJava7Inlineless() {
        Integer out = list.stream()
                .map(anonymousDevowelizerMapperClass)
                .map(anonymousLengthMapperClass)
                .reduce(anonymousAccumulatorClass)
                .get();

        System.out.println("howManyConsonantsPseudoJava7Inlineless: there are " + out + " consonants");
    }

    // we haven't used lambda but we are still using Java 8 streams, what's without?
    public static void howManyConsonantsJava7() {
        LambdaGameWithoutLambda.main(null);
    }

    // the lambda function we have used are equivalent to the methods in this
    // class
    public static class LambdaMethods {

        public static Integer accumulator(Integer a, Integer b) {
            return a + b;
        }

        public static Integer lengthMapper(String s) {
            return s.length();
        }

        public static String devowelizerMapper(String s) {
            return s.replaceAll("[aeiou]", "");
        }
    }

    // they are so equivalent that we can implement the methods using them
    public static class MethodsUsingLambdasImpl {

        public static Integer accumulator(Integer t, Integer u) {
            return accumulator.apply(t, u);
        }

        public static Integer lengthMapper(String s) {
            return lengthMapper.apply(s);
        }

        public static String devowelizerMapper(String s) {
            return devowelizerMapper.apply(s);
        }
    }
}
