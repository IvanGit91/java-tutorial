package tutorial.innerclass;

/*
Regular Inner Class:
- Defined directly inside another class.
- Can access all fields and methods of the enclosing class, even private ones.
- Needs an instance of the outer class to be instantiated.

Static Nested Class:
- Declared with the static keyword.
- Does not require an instance of the outer class to be instantiated.
- Cannot access non-static members of the enclosing class.

Method-Local Inner Class:
- Defined inside a method.
- Scope is limited to the method where it is declared.
- Can access local variables of the method if they are final or effectively final.

- Anonymous Inner Class:
- A class without a name.
- Typically used to override methods of an interface or a superclass in a single-use scenario.
- Cannot have a constructor since it doesn't have a name.
*/

public class OuterClass {

    private final String outerField = "Outer Field";

    public static void main(String[] args) {
        // 1. Regular Inner Class
        OuterClass outer = new OuterClass();
        RegularInnerClass regularInner = outer.new RegularInnerClass();
        regularInner.displayOuterField();

        // 2. Static Nested Class
        StaticNestedClass staticNested = new StaticNestedClass();
        staticNested.displayStaticMessage();

        // 3. Method-Local Inner Class
        outer.methodWithInnerClass();

        // 4. Anonymous Inner Class
        outer.createAnonymousInnerClass();
    }

    // Method-Local Inner Class
    public void methodWithInnerClass() {
        class MethodLocalInnerClass {
            public void displayMessage() {
                System.out.println("Method-Local Inner Class can access outer class field: " + outerField);
            }
        }

        MethodLocalInnerClass inner = new MethodLocalInnerClass();
        inner.displayMessage();
    }

    // Anonymous Inner Class
    public void createAnonymousInnerClass() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner Class implementing Runnable.");
            }
        };
        new Thread(runnable).start();
    }

    // Static Nested Class
    public static class StaticNestedClass {
        public void displayStaticMessage() {
            System.out.println("Static Nested Class doesn't require an instance of the outer class.");
        }
    }

    // Regular Inner Class
    public class RegularInnerClass {
        public void displayOuterField() {
            System.out.println("Accessing outer field: " + outerField);
        }
    }
}
