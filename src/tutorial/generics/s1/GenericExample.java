package tutorial.generics.s1;

import java.util.ArrayList;
import java.util.List;

// Generic class
class Box<T> {
    private T content;

    public Box(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Box contains: " + content.toString();
    }
}

// A utility class with a generic method
class BoxUtils {
    // Generic method
    public static <T> void displayBoxContent(Box<T> box) {
        System.out.println(box);
    }
}

public class GenericExample {
    public static void main(String[] args) {
        // Create a Box for String
        Box<String> stringBox = new Box<>("Hello, Generics!");
        BoxUtils.displayBoxContent(stringBox);

        // Create a Box for Integer
        Box<Integer> intBox = new Box<>(42);
        BoxUtils.displayBoxContent(intBox);

        // Create a List of Boxes
        List<Box<?>> boxes = new ArrayList<>();
        boxes.add(new Box<>("String Box"));
        boxes.add(new Box<>(100));

        for (Box<?> box : boxes) {
            System.out.println(box);
        }
    }
}
