package tutorial.string;

public class Concat {

    public static void main(String[] args) {
        String s1 = "a";   // Both can also be empty, doesn't change anything
        String s2 = "a";
        s1 = s1.concat("");  // concat receives empty string, no new object is created
        s2 = s2.concat("");
        if (s1 == s2)
            System.out.print("Printed1\n"); // Executed, same references
        s1 = s1.concat("o");  // concat creates a new object -> reference changes
        if (s1 == s2)
            System.out.print("Printed2\n"); // Not executed, different references and values
        s2 = s2.concat("o");
        if (s1 == s2)
            System.out.print("Printed3\n"); // Not executed, different references but same value

        String s3 = "";

        q(s3.concat("a"));

    }

    static void q(String s1) {
        String s2 = "a";
        if (s1 == s2)  // Same value but different references
            System.out.print("Printed4\n");  // Not executed
    }
}
