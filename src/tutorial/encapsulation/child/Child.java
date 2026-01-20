package tutorial.encapsulation.child;

import tutorial.encapsulation.parent.Parent;

public class Child extends Parent {
    void display() {
        // Can access protected member because it's inherited
        System.out.println("Protected value: " + protectedValue);
        // Can't access default member because it's in another package, make it public or protected
        // System.out.println("Protected value: " + defaultValue); // GIVE ERROR
    }
}