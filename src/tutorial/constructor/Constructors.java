package tutorial.constructor;

class Student {
    protected String name = "default";

    Student() {
        name = "unnamed";
    }

    Student(String name) {
        this.name = name;
    }
}

class ScholarshipHolder extends Student {
    int stipend;

    ScholarshipHolder(String name, int stipend) {
        super(name);     // Calls constructor with String parameter
        // Could also write: super.name = name;
        this.stipend = stipend;
    }

    ScholarshipHolder() {
        // Implicit call to super(), which calls Student()
    }

    ScholarshipHolder(String name) {
        // Always calls Student() even if there's an input parameter
    }

}

public class Constructors {

    public static void main(String[] args) {
        Student s = new Student();
        Student b = new ScholarshipHolder();
        ScholarshipHolder bb = new ScholarshipHolder();
        System.out.print(b.name);  // Implicit call to super
        System.out.print(bb.name); // Implicit call to super
        Student b1 = new ScholarshipHolder("John");
        System.out.print(b1.name);
    }

}
