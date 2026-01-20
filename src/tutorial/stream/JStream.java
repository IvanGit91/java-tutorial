package tutorial.stream;

import tutorial.models.city.City;
import tutorial.models.city.ParkingZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JStream {

    public static void main(String[] args) {
        // ---------- init --------------
        List<City> cities = new ArrayList<>(List.of(
                new City(1L, "Napoli", "NA", 1.0),
                new City(2L, "Caserta", "NA", 2.0),
                new City(3L, "Roma", "RM", 3.0),
                new City(4L, "Avellino", "NA", 4.0),
                new City(5L, "Milano", "MI", 5.0),
                new City(6L, "Lazio", "RM", 6.0)));
        List<ParkingZone> zones = new ArrayList<>(List.of(
                new ParkingZone("Zone1", "hello", cities.get(0)),
                new ParkingZone("Zone2", "hello2", cities.get(0))
        ));
        List<ParkingZone> zones2 = new ArrayList<>(List.of(
                new ParkingZone("Zone3", "hello", cities.get(2)),
                new ParkingZone("Zone4", "hello2", cities.get(2)),
                new ParkingZone("Zone4", "hello2", cities.get(2))
        ));
        cities.get(0).setParkingZoneList(zones);
        cities.get(2).setParkingZoneList(zones2);
        // --------------------------------

        grouping(cities);
        flatMap(cities);
        flatMapDistinct(cities);
        count(cities);
        sum(cities);
    }

    private static void grouping(List<City> cities) {
        Map<String, List<City>> citiesGroupedByProvince = cities.stream().collect(Collectors.groupingBy(City::getAdministrativeAreaLevel2Code));
        citiesGroupedByProvince.forEach((key, value) -> System.out.println("K: " + key + " V: " + value));
    }

    // Map the content of a sublist as a result
    private static void flatMap(List<City> cities) {
        cities.stream().flatMap(c -> c.getParkingZoneList().stream().map(p -> p.getName())).forEach(p -> System.out.println("ParkingZoneName: " + p));
    }

    // exclude duplicates
    private static void flatMapDistinct(List<City> cities) {
        cities.stream().flatMap(c -> c.getParkingZoneList().stream().map(p -> p.getName())).distinct().forEach(p -> System.out.println("ParkingZoneName: " + p));
    }

    private static Long count(List<City> cities) {
        return cities.stream().filter(c -> c.getAdministrativeAreaLevel2Code().equals("NA")).count();
    }

    private static Double sum(List<City> cities) {
        return cities.stream().filter(c -> c.getAdministrativeAreaLevel2Code().equals("NA")).mapToDouble(c -> c.getLatitude()).sum();
    }

}
