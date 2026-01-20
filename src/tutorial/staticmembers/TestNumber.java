package tutorial.staticmembers;

public class TestNumber {

    public static void main(String[] args) {
        int a = 18;
        int b = 7;

        // Static member functions must be called through the CLASS NAME, not from the object name

        System.out.println(a + " plus " + b + " equals " + NumberDemo.add(a, b));

        System.out.println(a + " minus " + b + " equals " + NumberDemo.subtract(a, b));

        System.out.println(a + " times " + b + " equals " + NumberDemo.multiply(a, b));

        System.out.println(a + " divided by " + b + " equals " + NumberDemo.divide(a, b));

    }
}

