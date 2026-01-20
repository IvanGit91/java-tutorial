package tutorial.staticmembers;

public class StaticBlockExample {

    // Static variable
    static int staticValue;

    // Static block
    static {
        System.out.println("Static block is executed.");
        staticValue = 42; // Initialize static variable
    }

    // Main method
    public static void main(String[] args) {
        System.out.println("Main method is executed.");
        System.out.println("Static Value: " + staticValue);
    }
}
