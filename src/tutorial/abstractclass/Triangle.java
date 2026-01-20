package tutorial.abstractclass;

abstract class Shape {
    static final int DEFAULT_SIDES = 6;
    String description = "generic shape";

    void calculatePerimeter() {
    }

    void calculateArea() {
    }

    abstract void calculate();
}

public class Triangle extends Shape {
    int side = 2;

    public static void main(String[] args) {
        Triangle triangle = new Triangle();
        triangle.calculatePerimeter();
        triangle.calculateArea();
        triangle.calculate();

        Shape shape = new Triangle();
        shape.calculate();
    }

    @Override
    public void calculatePerimeter() {
        System.out.println("Perimeter = " + side * 3);
    }

    @Override
    public void calculateArea() {
        System.out.println("Area = " + side * side);
    }

    @Override
    void calculate() {
        System.out.println("Calculated!");
    }

}
