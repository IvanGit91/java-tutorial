package tutorial.lambda.s1;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("boxing")
public class LambdaGameWithoutLambda {

    // consider the same list a list of strings
    static List<String> list =
            Arrays.asList("Cristiano", "Michele", "Sergio", "Giuseppe", "Stefano");
    // the functions used are inline anonymous classes
    static SmallJava8.Function<String, String> devowelizerMapperClass = new SmallJava8.Function<String, String>() {

        @Override
        public String apply(String p) {
            return p.replaceAll("[aeiou]", "");
        }
    };
    static SmallJava8.Function<String, Integer> lengthMapperClass = new SmallJava8.Function<String, Integer>() {

        @Override
        public Integer apply(String a) {
            return a.length();
        }
    };
    static SmallJava8.BinaryOperator<Integer> accumulatorClass = new SmallJava8.BinaryOperator<Integer>() {

        @Override
        public Integer apply(Integer a, Integer b) {
            return a + b;
        }
    };

    // this class runs also on Java 7
    public static void main(String[] args) {
        howManyConsonantsProcedural();
        howManyConsonantsJava7();
        eachStepOfHowManyConsonantsJava7();
    }

    // lets count how many consonants in all the names
    public static void howManyConsonantsProcedural() {

        Integer out = 0;
        for (String name : list) {
            String consonants = name.replaceAll("[aeiou]", "");
            int length = consonants.length();
            out += length;
        }

        System.out.println("howManyConsonantsProcedural: there are " + out + " consonants");
    }

    // implementation without lambda and without java 8 streams
    public static void howManyConsonantsJava7() {

        Integer out = SmallJava8.streamFromList(list)
                .map(devowelizerMapperClass)
                .map(lengthMapperClass)
                .reduce(accumulatorClass)
                .get();

        System.out.println("howManyConsonantsJava7: there are " + out + " consonants");
    }

    // and same implementation in each individual step
    public static void eachStepOfHowManyConsonantsJava7() {

        SmallJava8.Stream<String> stream = SmallJava8.streamFromList(list);
        SmallJava8.Stream<String> devowelizedStream = stream.map(devowelizerMapperClass);
        SmallJava8.Stream<Integer> lengthStream = devowelizedStream.map(lengthMapperClass);
        SmallJava8.Optional<Integer> reduce = lengthStream.reduce(accumulatorClass);
        Integer out = reduce.get();

        System.out.println("eachStepOfHowManyConsonantsJava7: there are " + out + " consonants");
    }

    // but we don't use Java 8! so here is the small implementation of Streams and Functional Interfaces
    // that we have used above
    public static class SmallJava8 {

        /**
         * Utility to get a Stream from a java.util.List
         */
        @SuppressWarnings("unchecked")
        public static <T> Stream<T> streamFromList(List<T> list) {
            T[] arr = (T[]) new Object[list.size()];
            arr = list.toArray(arr);
            Stream<T> ret = new ArrayStream<T>(arr);
            return ret;
        }

        /**
         * Functional Interface similar to java.util.function.Function
         */
        public interface Function<I, O> {

            /**
             * Applies this function to the given argument
             */
            O apply(I input);
        }

        /**
         * Functional Interface similar to java.util.function.BinaryOperator
         */
        public interface BinaryOperator<T> {

            /**
             * Applies this function to the given argument
             */
            T apply(T param1, T param2);
        }

        /**
         * A sequence of elements supporting aggregate operations that works in a
         * similar way as java.util.stream.Stream
         */
        public interface Stream<T> {

            /**
             * Returns a stream consisting of the results of applying the given
             * function to the elements of this stream.
             */
            <R> Stream<R> map(Function<? super T, ? extends R> mapper);

            /**
             * Performs a reduction on the elements of this stream, using an
             * associative accumulation function, and returns an Optional describing
             * the reduced value, if any.
             */
            T reduce(T identity, BinaryOperator<T> accumulator);

            /**
             * Performs a reduction on the elements of this stream, using the provided
             * identity value and an associative accumulation function, and returns
             * the reduced value.
             */
            Optional<T> reduce(BinaryOperator<T> accumulator);

        }

        /**
         * Implementation of SmallJava8.Stream backed by an array
         */
        public static class ArrayStream<T> implements Stream<T> {

            private final T[] array;

            private ArrayStream(T[] array) {
                this.array = array;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {

                int length = array.length;
                R[] outArray = (R[]) new Object[length];
                for (int i = 0; i < length; i++) {
                    outArray[i] = mapper.apply(array[i]);
                }
                return new ArrayStream<R>(outArray);
            }

            @Override
            public T reduce(T identity, BinaryOperator<T> accumulator) {
                T result = identity;
                for (T element : array) {
                    result = accumulator.apply(result, element);
                }
                return result;
            }

            @Override
            public Optional<T> reduce(BinaryOperator<T> accumulator) {
                boolean foundAny = false;
                T result = null;
                for (T element : array) {
                    if (!foundAny) {
                        foundAny = true;
                        result = element;
                    } else
                        result = accumulator.apply(result, element);
                }
                return foundAny ? (new Optional<T>(result)) : (new Optional<T>());
            }
        }

        /**
         * A container object which may or may not contain a non-null value.
         */
        public static class Optional<T> {

            final T t;

            public Optional(T t) {
                this.t = t;
            }

            public Optional() {
                this.t = null;
            }

            public T get() {
                return t;
            }
        }
    }

}
