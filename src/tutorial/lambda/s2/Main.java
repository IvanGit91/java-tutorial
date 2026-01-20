package tutorial.lambda.s2;

public class Main {
    public static void main(String[] args) {

        List1 list = new List1(1);

        list.getElementi().add(new List2(2));

        list.getElementi().getFirst().getElementi().add(new List3(3));

        int sum = list.getElementi().stream()
                .mapToInt(e -> e.getElementi().stream()
                        .mapToInt(p -> p.getId()).sum()
                ).sum();


        System.out.println("Sum: " + sum);

    }
}
