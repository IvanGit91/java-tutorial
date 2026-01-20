package tutorial.polymorphism;

/*
Polymorphism in Java allows objects to be treated as instances of their parent class rather than their actual class.
This enables methods to behave differently based on the object that invokes them
*/

abstract class Animal {
    // Abstract method for sound
    public abstract void makeSound();

    // Concrete method for eating
    public void eat() {
        System.out.println("This animal eats food.");
    }
}

class Lion extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Lion roars: Roar!");
    }
}

class Elephant extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Elephant trumpets: Pawoo!");
    }
}

class Monkey extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Monkey chatters: Ooh Ooh Aah Aah!");
    }
}

public class Zoo {
    public static void main(String[] args) {
        // Polymorphic array of Animal references
        Animal[] animals = new Animal[3];
        animals[0] = new Lion();       // Upcasting Lion to Animal
        animals[1] = new Elephant();  // Upcasting Elephant to Animal
        animals[2] = new Monkey();    // Upcasting Monkey to Animal

        // Iterate through animals and call methods
        for (Animal animal : animals) {
            animal.makeSound(); // Calls overridden method
            animal.eat();       // Calls parent method
            System.out.println();
        }
    }
}
