package tutorial.string;

public class SprintfSample {
    public static void main(String[] args) {
        String s1 = "ciao";
        int x = 4;
        // First param will have length of 15
        // Second param will add 0 to the number for a max of 3
        System.out.printf("%-15s%03d%n", s1, x);
    }
}
