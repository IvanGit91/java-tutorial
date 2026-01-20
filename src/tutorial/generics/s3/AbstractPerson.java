package tutorial.generics.s3;

import java.lang.reflect.Field;

/**
 * Abstract base class demonstrating generic type parameters with reflection.
 * Finds the ID field in the generic type class.
 *
 * @param <T> the type parameter representing the concrete class
 */
abstract class AbstractPerson<T> {

    public static final String name = "abstract";
    private final Class<T> typeClass;
    private String idFieldName = null;

    protected AbstractPerson(Class<T> typeClass) {
        this.typeClass = typeClass;
        for (Field field : typeClass.getDeclaredFields()) {
            if (field.getName().contains("id")) {
                idFieldName = field.getName();
                break;
            }
        }
    }

    public void run() {
        System.out.println("Result: " + typeClass + " ID: " + idFieldName);
    }
}
