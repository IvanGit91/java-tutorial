package tutorial.encapsulation;

/*
default (package-private): This is the default access modifier if none is specified.
It means that the member (method or variable) is accessible only within classes that are in the same package. It is not accessible outside the package.

protected: The protected access modifier makes the member accessible within the same package (like default),
but also accessible to subclasses (even if they are in different packages).

Modifier    | Class | Package | Subclass | World

public      |  Y    |    Y    |    Y     |   Y

protected   |  Y    |    Y    |    Y     |   N

no modifier |  Y    |    Y    |    N     |   N

private     |  Y    |    N    |    N     |   N

*/

class Parent {
    // protected access
    protected int protectedValue = 20;
    // default access (package-private)
    int defaultValue = 10;
}

class Child extends Parent {
    void display() {
        // Can access protected member because it's inherited
        System.out.println("Protected value: " + protectedValue + defaultValue);
    }
}

public class ProtectedDefaultSample {
    public static void main(String[] args) {
        Parent parent = new Parent();

        // Can access defaultValue because it's in the same package
        System.out.println("Default value: " + parent.defaultValue);

        System.out.println(parent.protectedValue);

        Child child = new Child();
        System.out.println(child.defaultValue);
        child.display(); // This works because protectedValue is inherited in Child
    }
}
