package tutorial.interfaces;

interface MyInterface {

    String hello = "Hello";

    void sayHello();
}


class MyInterfaceImpl implements MyInterface {

    @Override
    public void sayHello() {
        System.out.println(MyInterface.hello);
    }
}