package newfeatures.v13;

/**
 * Java 13 Features Demo
 * <p>
 * Java 13 introduced several preview features:
 * - Text Blocks (Preview) - Multi-line string literals
 * - Switch Expressions (Preview, 2nd iteration) - Enhanced switch with yield
 * - Dynamic CDS Archives - Improved Class Data Sharing
 * - ZGC: Uncommit Unused Memory - GC improvement
 * - Reimplement Legacy Socket API
 */
public class Java13 {
    public static void main(String[] args) {
        // 1. Text Blocks (Preview Feature)
        // Text blocks allow multi-line strings without escape sequences
        // Note: This was a preview feature in Java 13, finalized in Java 15
        String textBlock = """
                SELECT id, name, email
                FROM users
                WHERE status = 'active'
                ORDER BY name;
                """;
        System.out.println("Text Block Example:");
        System.out.println(textBlock);

        // 2. Switch Expressions with yield (Preview Feature)
        // The yield keyword was introduced to return values from switch blocks
        // Note: This was a preview feature, finalized in Java 14
        int dayNumber = 3;
        String dayType = switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> "Weekday";
            case 6, 7 -> "Weekend";
            default -> {
                System.out.println("Invalid day number");
                yield "Unknown";
            }
        };
        System.out.println("Day " + dayNumber + " is a: " + dayType);

        // 3. Traditional switch with yield
        int month = 8;
        int daysInMonth = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> 28; // Simplified, not accounting for leap years
            default -> {
                System.out.println("Invalid month");
                yield 0;
            }
        };
        System.out.println("Month " + month + " has " + daysInMonth + " days");

        // 4. String methods enhanced (from Java 11-12, commonly used with text blocks)
        String html = """
                <html>
                    <body>
                        <p>Hello, World!</p>
                    </body>
                </html>
                """;

        // stripIndent() - Removes incidental white space
        System.out.println("HTML with stripped indent:");
        System.out.println(html.stripIndent());

        // translateEscapes() - Translates escape sequences
        String escaped = "Hello\\nWorld";
        System.out.println("Translated escapes: " + escaped.translateEscapes());
    }
}
