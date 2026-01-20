package newfeatures.java7;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Java7 {
    public static void main(String[] args) {
        // 1.1  Strings in switch's Selector
        String day = "SAT";
        switch (day) {  // switch on String selector (JDK 7)
            case "MON":
            case "TUE":
            case "WED":
            case "THU":
                System.out.println("Working Day");
                break;
            case "FRI":
                System.out.println("Thank God It's Friday");
                break;
            case "SAT":
            case "SUN":
                System.out.println("Gone Fishing");
                break;
            default:
                System.out.println("Invalid");
        }

        // 1.2  Binary Integer Literals with Prefix "0b" and Underscore in Numeric Literals
        // Some 32-bit 'int' literal values
        int anInt1 = 0b0101_0000_1010_0010_1101_0000_1010_0010;
        int anInt2 = 0b0011_1000;

        // An 8-bit 'byte' literal value. By default, literal values are 'int'.
        // Need to cast to 'byte'
        byte aByte = (byte) 0b0110_1101;

        // A 16-bit 'short' literal value
        short aShort = (short) 0b0111_0101_0000_0101;

        // A 64-bit 'long' literal value. Long literals requires suffix "L".
        long aLong = 0b1000_0101_0001_0110_1000_0101_0000_1010_0010_1101_0100_0101_1010_0001_0100_0101L;

        // Formatted output: "%d" for integer in decimal, "%x" in hexadecimal, "%o" in octal.
        // Take note that "%b" prints true or false (for null), NOT binary.
        System.out.printf("%d(%x)(%o)(%b)\n", anInt1, anInt1, anInt1, anInt1);
        System.out.printf("%d(%x)(%o)(%b)\n", aByte, aByte, aByte, aByte);

        // 1.3  Catching Multiple Exception Types and Re-throwing Exceptions with Improved Type Checking
        try {
            System.out.println("Hello World");
            ClassLoader.getPlatformClassLoader().loadClass("com.google.common.collect.ImmutableList");
            SQLException sqlException = null;
            throw sqlException;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        // 1.4  Automatic Resource Management in try-with-resources Statement
        // JDK 7 introduces a try-with-resources statement, which ensures that each of the resources in try(resources) is properly closed at the end of the statement. This results in cleaner codes.
        try (BufferedReader src = new BufferedReader(new FileReader("in.txt"));
             BufferedWriter dest = new BufferedWriter(new FileWriter("out.txt"))) {
            String line;
            while ((line = src.readLine()) != null) {
                System.out.println(line);
                dest.write(line);
                dest.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // src and dest automatically close.
        // No need for finally to explicitly close the resources.

        // 1.5  Improved Type Inference for Generic Instance Creation with the Diamond Operator <>
        // Pre-JDK 7
        List<String> lst1 = new ArrayList<String>();
        // JDK 7 supports limited type inference for generic instance creation with diamond operator <>
        List<String> lst2 = new ArrayList<>();

        // 1.6  Simplified varargs Method Declaration with @SafeVarargs Annotation
    }
}
