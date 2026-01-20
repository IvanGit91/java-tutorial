package tutorial.polymorphism;

class Animal2 {
    public void makeSound() {
        System.out.println("Generic animal sound");
    }
}

class Dog extends Animal2 {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

class Cat extends Animal2 {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal2 animal1 = new Dog();
        Animal2 animal2 = new Cat();

        animal1.makeSound(); // Outputs: "Woof!"
        animal2.makeSound(); // Outputs: "Meow!"
    }
}