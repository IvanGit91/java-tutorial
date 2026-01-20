package tutorial.generics.s3;

import tutorial.models.common.Pair;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Demonstrates generic types including:
 * - Generic pair as map key
 * - Generic inheritance patterns
 * - Reflection with generic types
 */
public class GenericDemo {

    private static final HashMap<Pair<String, String>, Double> map = new LinkedHashMap<>();

    public static void main(String[] args) {
        Person p = new Person();
        p.run();
        p.sayHello();

        map.put(new Pair<>("hello", "ok"), 2d);
        map.put(new Pair<>("findme", "ok"), 2d);
        map.put(new Pair<>("nice", "ok"), 2d);

        System.out.println("Contains non-existent key: " + map.containsKey(new Pair<>("dd", "asd")));
        System.out.println("Contains partial match: " + map.containsKey(new Pair<>("hello", "k")));
        System.out.println("Contains exact match: " + map.containsKey(new Pair<>("hello", "ok")));

        GenericDemo demo = new GenericDemo();
        demo.printType(p);
    }

    public <T> void printType(T object) {
        Class<T> typeClass = (Class<T>) ((ParameterizedType) object.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println("Object class: " + object.getClass());
        System.out.println("Type parameter: " + typeClass);
    }
}
