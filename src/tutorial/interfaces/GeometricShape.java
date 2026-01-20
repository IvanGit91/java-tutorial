package tutorial.interfaces;

interface GeometricShape {
    void calculatePerimeter();

    void calculateArea(int height);
}

class Triangle implements GeometricShape {
    int side1 = 1;
    int side2 = 2;
    int side3 = 3;

    @Override
    public void calculatePerimeter() {
        int p = side1 + side2 + side3;
        System.out.print("Perimeter = " + p);
    }

    @Override
    public void calculateArea(int height) {
        int a = (side3 * height);
        System.out.print(" Area = " + a);
    }
}
