package tutorial.interfaces;

public class InterfaceDemo {

    public static void main(String[] args) {
        System.out.println(MyInterface.hello);  // Access interface members
        // Create interface instance
        MyInterface myInterface = new MyInterfaceImpl();
        myInterface.sayHello();

        // Geometric shape interface demo
        Triangle t = new Triangle();
        t.calculatePerimeter();
        t.calculateArea(5);
    }

}
