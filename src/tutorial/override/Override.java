package tutorial.override;

class Animal {
    int i = 0;

    public void move() {
        System.out.println("Animals can move");
    }

    public void dark() {
        System.out.println("Scuro");
    }

}

class Dog extends Animal {
    int i = 1;
    int o = 0;

    public void move() {
        System.out.println("Dogs can walk and run");
    }

    public void bark() {
        System.out.println("Dogs can bark");
    }
}

class Cat extends Animal {

}

public class Override {


    public static void main(String[] args) {
        Animal a = new Animal(); // Animal reference and object
        Animal b = new Dog(); // Animal reference but Dog object
        a.move();
        b.move();  // doesn't see 'bark' method
        System.out.println(a.i);
        System.out.println(b.i);
        Dog c = new Dog();
        c.bark();   // in this way it can see 'bark' method
        System.out.println(c.i);
    }
}
