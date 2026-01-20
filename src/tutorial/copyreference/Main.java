package tutorial.copyreference;

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }
}

public class Main {

    public static void classReference(Person person) {
        person.name = person.name.toUpperCase();
    }

    public static void stringCopy(String str) {
        str = str.toUpperCase();
    }

    public static void intCopy(Integer integ) {
        integ = 10;
    }

    public static void main(String[] args) {
        Person person1 = new Person("Alice");
        Person person2 = person1;

        System.out.println(person1.name); // Output: Alice
        System.out.println(person2.name); // Output: Alice

        person2.name = "Bob";

        System.out.println(person1.name); // Output: Bob
        System.out.println(person2.name); // Output: Bob

        classReference(person1);

        System.out.println(person1.name); // Output: BOB
        System.out.println(person2.name); // Output: BOB

        String str = "hello";
        stringCopy(str);
        System.out.println(str); // Output: hello

        Integer integ = 1;
        intCopy(integ);
        System.out.println(integ); // Output: 1
    }
}