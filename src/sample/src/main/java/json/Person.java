package json;

import java.util.Objects;

/**
 * Person class for JSON serialization/deserialization examples.
 * Represents a person with basic information.
 */
public class Person {

    private String firstName;
    private String lastName;
    private boolean gender;

    /**
     * Default constructor required for JSON deserialization.
     */
    public Person() {
    }

    /**
     * Creates a new Person with the specified attributes.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @param gender    the person's gender (true for male, false for female)
     */
    public Person(String firstName, String lastName, boolean gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    /**
     * Returns the full name (first name + last name).
     *
     * @return the person's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return gender == person.gender &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, gender);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
