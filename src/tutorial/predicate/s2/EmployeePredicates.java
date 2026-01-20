package tutorial.predicate.s2;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@FunctionalInterface
interface TwoArgInterface {
    int operation(int a, int b);
}

@FunctionalInterface
interface OneArgInterface {
    String makeIt(String s);

}

public class EmployeePredicates {

    TwoArgInterface plusOperationRet = (a, b) -> (a + b);

    public static Predicate<Employee> isAdultMale() {
        return p -> p.getAge() > 21 && p.getGender().equalsIgnoreCase("M");
    }

    public static Predicate<Employee> isAdultFemale() {
        return p -> p.getAge() > 18 && p.getGender().equalsIgnoreCase("F");
    }

    public static Predicate<Employee> isAgeMoreThan(Integer age) {
        return p -> p.getAge() > age;
    }


    public static List<Employee> filterEmployees(List<Employee> employees, Predicate<Employee> predicate) {
        return employees.stream()
                .filter(predicate)
                .collect(Collectors.<Employee>toList());
    }

    public static Integer sumAges(List<Employee> employees, Predicate<Employee> predicate) {
        return employees.stream().map(p -> p.getAge()).reduce(0, (p1, p2) -> (p1 + p2));
    }
}   
