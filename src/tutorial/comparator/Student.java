package tutorial.comparator;

import java.util.Comparator;

//A class to represent a student. 
public class Student implements Comparable<Student> {
    int rollno;
    String name;
    String address;

    // Constructor
    public Student(int rollno, String name, String address) {
        this.rollno = rollno;
        this.name = name;
        this.address = address;
    }

    // Used to print student details in main()
    public String toString() {
        return this.rollno + " " + this.name + " " + this.address;
    }

    @Override
    public int compareTo(Student b) {
        return this.name.compareTo(b.name);
    }
}

class Sortbyroll implements Comparator<Student> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(Student a, Student b) {
        return a.rollno - b.rollno;
    }
}

class Sortbyname implements Comparator<Student> {
    // Used for sorting in ascending order of
    // roll name
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
} 
