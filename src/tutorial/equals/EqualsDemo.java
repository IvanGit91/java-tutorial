package tutorial.equals;

import tutorial.models.vehicle.Engine;
import tutorial.models.vehicle.Vehicle;
import tutorial.models.vehicle.Wheel;

/**
 * Demonstrates proper equals/hashCode implementation with composite objects.
 * Shows how equality checks work with nested objects (Vehicle containing Wheel and Engine).
 */
public class EqualsDemo {

    public static void main(String[] args) {

        Vehicle v1 = new Vehicle("mod", "red", new Wheel("studded", "goodyear"), new Engine("punto", 1990, "550"));
        Vehicle v2 = new Vehicle("mod", "red", new Wheel("studded", "goodyear"), new Engine("punto", 1990, "550"));
        Vehicle v3 = new Vehicle("mod", "red", new Wheel("studded", "goodyears"), new Engine("punto", 1990, "550"));

        System.out.println("v1 equals v2 (same values): " + v1.equals(v2));
        System.out.println("v1 equals v3 (different wheel model): " + v1.equals(v3));
        System.out.println("v1 instanceof Vehicle: " + (v1 instanceof Vehicle));
    }
}
