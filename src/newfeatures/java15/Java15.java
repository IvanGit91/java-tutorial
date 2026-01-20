package newfeatures.java15;

public class Java15 {
    public static void main(String[] args) {
        Person3 person = new Employee();
        int id = getPersonId(person);
    }

    public static int getPersonId(Person3 person) {
        int id = -1;
        if (person instanceof Employee) {
            id = person.getEmployeeId();
        } else if (person instanceof Manager) {
            id = ((Manager) person).getSupervisorId();
        }
        return id;
    }
}


// 2.1. Without Records
record Person(String name, int age) {
}


// 2.2. With Records
// Using this header, the compiler can infer the internal fields. This means we don’t need to define specific member variables and accessors, as they’re provided by default. We also don’t have to provide a constructor.
// Additionally, the compiler provides sensible implementations for the toString, equals, and hashCode methods.
// While records eliminate a lot of boilerplate code, they do allow us to override some of the default behaviors. For example, we could define a canonical constructor that does some validation:
record Person2(String name, int age) {
    // additional check
    public Person2 {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }
}

// It’s worth mentioning that records do have some restrictions. Among other things, they are always final, they cannot be declared abstract, and they can’t use native methods.
