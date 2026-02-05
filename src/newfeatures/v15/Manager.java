package newfeatures.v15;

// non-sealed – allowing any class to be able to inherit from it.
public non-sealed class Manager extends Person3 {
    private int supervisorId;

    protected int getSupervisorId() {
        return supervisorId;
    }
}
