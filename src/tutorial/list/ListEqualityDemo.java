package tutorial.list;

import tutorial.models.vehicle.Engine;
import tutorial.models.vehicle.Vehicle;
import tutorial.models.vehicle.Wheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates list equality comparison with composite objects.
 * Shows how List.equals() works with custom objects that override equals/hashCode.
 */
public class ListEqualityDemo {

    public static void main(String[] args) {

        List<Vehicle> vehicleList1 = new ArrayList<>();
        vehicleList1.add(new Vehicle("A", "B", new Wheel("C", "D"), new Engine("E", 1, "G")));
        vehicleList1.add(new Vehicle("A", "B", new Wheel("C", "D"), new Engine("E", 1, "G")));
        vehicleList1.add(new Vehicle("A", "B", new Wheel("C", "D"), new Engine("E", 1, "G")));

        List<Vehicle> vehicleList2 = new ArrayList<>();
        vehicleList2.add(new Vehicle("A", "B", new Wheel("C", "D"), new Engine("E", 1, "G")));
        vehicleList2.add(new Vehicle("B", "B", new Wheel("C", "D"), new Engine("E", 1, "G"))); // Different model
        vehicleList2.add(new Vehicle("A", "B", new Wheel("C", "D"), new Engine("E", 1, "G")));

        System.out.println("Lists equal: " + vehicleList1.equals(vehicleList2));
    }
}
