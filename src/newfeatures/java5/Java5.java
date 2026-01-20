package newfeatures.java5;

public class Java5 {
    public static void main(String[] args) {
        // 1. Generics:
        // Usage of the generic class
        Box<Integer> integerBox = new Box<>();
        integerBox.setValue(42);
        Integer value = integerBox.getValue();

        // 2. Metadata Annotations:

        // 3. Enhanced For Loop (For-each Loop):

        // 4. Autoboxing and Unboxing:
        int primitiveInt = 42;
        // Autoboxing
        Integer wrapperInt = primitiveInt;
        // Unboxing
        int backToPrimitive = wrapperInt;

        // 5. Enumerated Types:
        enum Day {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        // Usage of the enum
        Day today = Day.MONDAY;

        // 6. Varargs:
        int total = sum(1, 2, 3, 4, 5);
    }

    public static int sum(int... numbers) {
        int result = 0;
        for (int num : numbers) {
            result += num;
        }
        return result;
    }

}

// Generic class example
class Box<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}