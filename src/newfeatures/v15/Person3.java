package newfeatures.v15;

// the goal of sealed classes is to allow individual classes to declare which types may be used as sub-types. This also applies to interfaces and determining which types can implement them.
// Sealed classes involve two new keywords — sealed and permits:
public abstract sealed class Person3 permits Employee, Manager {
    private int employeeId;

    protected int getEmployeeId() {
        return employeeId;
    }
}

// // It’s important to note that any class that extends a sealed class must itself be declared sealed, non-sealed, or final. This ensures the class hierarchy remains finite and known by the compiler.