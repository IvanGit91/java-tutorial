package tutorial.models.common;

/**
 * Represents a person with a table ID.
 */
public class Person {

    String field1 = "";

    String field2 = "";

    private Integer tableId = 22;

    public Person() {
    }

    public Person(Integer tableId) {
        this.tableId = tableId;
    }

    public void sayHello() {
        System.out.println("HELLO: " + Person.class.getName());
        System.out.println(Person.class.getSimpleName());
        System.out.println(Person.class.getDeclaredMethods()[1].getName());
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}
