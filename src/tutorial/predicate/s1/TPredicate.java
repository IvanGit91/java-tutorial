package tutorial.predicate.s1;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class TPredicate {

    private static final Predicate<?> notNull = Objects::nonNull;
    private static final Predicate<?> nameStartsWithS = str -> str.toString().startsWith("S");

    public static void main(String[] args) {
        test();
        test2(nameStartsWithS);
    }

    public static void test() {
        var lista = List.of("ciao", "Sciao", "hoS");
        var lista2 = lista.stream().filter(f -> f.startsWith("S"));
        lista2.forEach(System.out::println);
    }

    public static void test2(Predicate pr) {
        var lista = List.of("ciao", "Sciao", "hoS");
        var lista2 = lista.stream().filter(pr);
        lista2.forEach(System.out::println);
    }

    public static void test3(Predicate pr) {
        var lista = List.of("ciao", "Sciao", "hoS");
        var lista2 = List.of("ciao", "sss", "cccc");
        var lista3 = lista.stream().filter(l -> lista2.stream().anyMatch(l2 -> l2.equals(l)));
        lista2.forEach(System.out::println);
    }

    public static void test4(Predicate pr) {
        var lista = List.of("ciao", "Sciao", "hoS");
        var lista2 = List.of("ciao", "sss", "cccc");
        var lista3 = lista.stream().filter(listPredicate(lista2));
        lista2.forEach(System.out::println);
    }

    public static <E> Predicate<E> listPredicate(List<E> lista) {
        return pr -> lista.stream().anyMatch(l -> l.equals(pr));
    }
}
