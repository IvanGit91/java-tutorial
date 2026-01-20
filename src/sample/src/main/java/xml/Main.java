package xml;

/**
 * Entry point demonstrating XML generation and parsing capabilities.
 *
 * <p>Usage:
 * <pre>
 *   java Main                              - Run demo (generate XML to stdout)
 *   java Main generate [output_file]       - Generate sample XML
 *   java Main parse &lt;input_file&gt;           - Parse an XML file
 *   java Main help                         - Show this help
 * </pre>
 *
 * <p>Examples:
 * <pre>
 *   java Main generate /tmp/company.xml    - Generate XML to file
 *   java Main parse /tmp/input.xml         - Parse existing XML file
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            runDemo();
            return;
        }

        String command = args[0].toLowerCase();
        GeneraXML generator = new GeneraXML();

        switch (command) {
            case "generate" -> {
                String outputPath = args.length > 1 ? args[1] : null;
                String xml = generator.writeXML(outputPath);
                if (outputPath == null) {
                    System.out.println("\nGenerated XML:\n" + xml);
                } else {
                    System.out.println("XML written to: " + outputPath);
                }
            }
            case "parse" -> {
                if (args.length < 2) {
                    System.err.println("Error: parse command requires an input file path");
                    printUsage();
                    return;
                }
                generator.parseXML(args[1]);
            }
            case "help", "-h", "--help" -> printUsage();
            default -> {
                System.err.println("Unknown command: " + command);
                printUsage();
            }
        }
    }

    private static void runDemo() {
        System.out.println("===========================================");
        System.out.println("        XML Generation & Parsing Demo       ");
        System.out.println("===========================================\n");

        GeneraXML generator = new GeneraXML();

        // Demo 1: Generate XML and display
        System.out.println("--- Demo 1: Generate Sample XML ---\n");
        String xmlContent = generator.writeXML(null);

        System.out.println("\n--- Generated XML String ---");
        System.out.println(xmlContent);

        // Demo 2: Show how to save to file
        System.out.println("\n--- Demo 2: Save to File ---");
        System.out.println("To save XML to a file, run:");
        System.out.println("  java Main generate /path/to/output.xml\n");

        // Demo 3: Show how to parse
        System.out.println("--- Demo 3: Parse XML File ---");
        System.out.println("To parse an XML file, run:");
        System.out.println("  java Main parse /path/to/input.xml\n");

        System.out.println("===========================================");
        System.out.println("              Demo Complete                 ");
        System.out.println("===========================================");
    }

    private static void printUsage() {
        System.out.println("\nXML Utility - Generate and Parse XML Documents\n");
        System.out.println("Usage:");
        System.out.println("  java Main                         - Run demo");
        System.out.println("  java Main generate [output_file]  - Generate sample XML");
        System.out.println("  java Main parse <input_file>      - Parse an XML file");
        System.out.println("  java Main help                    - Show this help");
        System.out.println("\nExamples:");
        System.out.println("  java Main generate /tmp/company.xml");
        System.out.println("  java Main parse /tmp/employees.xml");
    }
}
