package tutorial.generics.s3;


/**
 * Person class demonstrating generic inheritance and reflection.
 * Extends AbstractPerson with type parameter bound to itself.
 */
public class Person extends AbstractPerson<Person> {

    private final Integer tableId = 22;
    String field1 = "";
    String field2 = "";

    public Person(Class<Person> typeClass) {
        super(typeClass);
    }

    public Person() {
        this(Person.class);
    }

    public void sayHello() {
        System.out.println("HELLO: " + Person.class.getName());
        System.out.println(Person.class.getSimpleName());
        System.out.println(Person.class.getDeclaredMethods()[1].getName());
    }
}
