package xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Demonstrates XML parsing and generation using the UtilXML base class.
 *
 * <p>This class shows how to:
 * <ul>
 *   <li>Parse structured XML documents (Italian UNILAV employment format)</li>
 *   <li>Extract data from nested elements with namespaces</li>
 *   <li>Generate new XML documents programmatically</li>
 *   <li>Output XML to various destinations</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *   java GeneraXML generate [output_file]     - Generate sample XML
 *   java GeneraXML parse &lt;input_file&gt;         - Parse an XML file
 *   java GeneraXML                            - Run demo (generate to stdout)
 * </pre>
 *
 * <p>The parseXML method demonstrates parsing an Italian employment data format (UNILAV)
 * with the following structure:
 * <ul>
 *   <li>DatoreLavoro (Employer) - contains SedeLegale (Legal HQ) and SedeLavoro (Workplace)</li>
 *   <li>Lavoratore (Employee) - contains AnagraficaCompleta (Personal Data)</li>
 *   <li>InizioRapporto (Employment Start)</li>
 * </ul>
 */
public class GeneraXML extends UtilXML {

    private static final Logger logger = Logger.getLogger(GeneraXML.class);

    public GeneraXML() {
    }

    public static void main(String[] args) {
        GeneraXML generator = new GeneraXML();

        if (args.length == 0) {
            // Demo mode: generate sample XML to stdout
            runDemo(generator);
            return;
        }

        String command = args[0].toLowerCase();
        switch (command) {
            case "generate" -> {
                String outputPath = args.length > 1 ? args[1] : null;
                String xml = generator.writeXML(outputPath);
                if (outputPath == null) {
                    System.out.println("\nGenerated XML:\n" + xml);
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

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java GeneraXML                         - Run demo (generate to stdout)");
        System.out.println("  java GeneraXML generate [output_file]  - Generate sample XML");
        System.out.println("  java GeneraXML parse <input_file>      - Parse an XML file");
        System.out.println("  java GeneraXML help                    - Show this help");
    }

    private static void runDemo(GeneraXML generator) {
        System.out.println("=== XML Generation Demo ===\n");
        String xmlContent = generator.writeXML(null);
        System.out.println("\nGenerated XML content:\n" + xmlContent);
        System.out.println("\n=== Demo Complete ===");
        System.out.println("To write to a file: java GeneraXML generate /path/to/output.xml");
        System.out.println("To parse a file:    java GeneraXML parse /path/to/input.xml");
    }

    /**
     * Parses an XML document in Italian UNILAV format and extracts key data.
     * The UNILAV format is used for employer/employee data exchange in Italy.
     *
     * <p>Extracts the following information:
     * <ul>
     *   <li>Employer info (DatoreLavoro) with legal and work addresses</li>
     *   <li>Employee info (Lavoratore) with personal data</li>
     *   <li>Employment start data (InizioRapporto)</li>
     * </ul>
     *
     * @param inputFile path to the XML file to parse
     */
    public void parseXML(String inputFile) {
        Document doc = initializeIn(inputFile);
        if (doc == null) {
            logger.error("Failed to parse XML file: " + inputFile);
            return;
        }

        Element rootElement = doc.getDocumentElement();
        System.out.println("Root element: " + rootElement.getNodeName());

        // Get namespace prefix (some XML documents use prefixes like "ns:")
        String prefix = getPrefix(rootElement, ":");

        // Find submission date attribute
        findAttribute(rootElement, "dataInvio");

        // Process child elements
        NodeList childNodes = rootElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            Element element = (Element) node;
            String nodeName = node.getNodeName();
            System.out.println("\nProcessing element: " + nodeName);

            if (nodeName.equals(prefix + "DatoreLavoro")) {
                processEmployer(element, prefix);
            } else if (nodeName.equals(prefix + "Lavoratore")) {
                processEmployee(element, prefix);
            } else if (nodeName.equals(prefix + "InizioRapporto")) {
                processEmploymentStart(element, prefix);
            }
        }

        System.out.println("\nParsing complete.");
    }

    /**
     * Processes the Employer (DatoreLavoro) element.
     * Extracts legal headquarters (SedeLegale) and workplace (SedeLavoro) info.
     */
    private void processEmployer(Element employer, String prefix) {
        System.out.println("  --- Employer Data ---");

        // Legal headquarters (SedeLegale) and Workplace (SedeLavoro) are siblings
        Element legalHQ = getChildElement(employer, prefix + "SedeLegale");
        if (legalHQ != null) {
            System.out.println("  Legal Headquarters:");
            printContactInfo(legalHQ, prefix, "    ");
        }

        Element workplace = getChildElement(employer, prefix + "SedeLavoro");
        if (workplace != null) {
            System.out.println("  Workplace:");
            printContactInfo(workplace, prefix, "    ");
        }
    }

    /**
     * Prints contact information (phone, fax) from an address element.
     */
    private void printContactInfo(Element addressElement, String prefix, String indent) {
        String phone = getChildTextContent(addressElement, prefix + "telefono");
        String fax = getChildTextContent(addressElement, prefix + "fax");

        if (phone != null) {
            System.out.println(indent + "Phone: " + phone);
        }
        if (fax != null) {
            System.out.println(indent + "Fax: " + fax);
        }
    }

    /**
     * Processes the Employee (Lavoratore) element.
     * Extracts personal data from AnagraficaCompleta.
     */
    private void processEmployee(Element employee, String prefix) {
        System.out.println("  --- Employee Data ---");

        Element personalData = getChildElement(employee, prefix + "AnagraficaCompleta");
        if (personalData != null) {
            String nationality = getChildTextContent(personalData, prefix + "cittadinanza");
            if (nationality != null) {
                System.out.println("  Nationality: " + nationality);
            }
        }
    }

    /**
     * Processes the Employment Start (InizioRapporto) element.
     * Currently logs a placeholder message; extend as needed.
     */
    private void processEmploymentStart(Element employment, String prefix) {
        System.out.println("  --- Employment Start Data ---");
        String startDate = getChildTextContent(employment, prefix + "dataInizio");
        if (startDate != null) {
            System.out.println("  Start Date: " + startDate);
        }
    }

    // ========================= XML Generation =========================

    /**
     * Creates a sample XML document demonstrating the generation capabilities.
     *
     * @param outputFile optional path to write the XML file (null for stdout only)
     * @return the generated XML as a String
     */
    public String writeXML(String outputFile) {
        Document doc = initializeOut();
        if (doc == null) {
            logger.error("Failed to create XML document");
            return null;
        }

        // Create root element
        Element root = createRoot(doc, "company");

        // Create employees
        createEmployee(doc, root, new EmployeeData(
                "1", "James", "Edward", "Harley", "james@example.org", "Human Resources"));

        createEmployee(doc, root, new EmployeeData(
                "2", "Maria", "Anne", "Smith", "maria@example.org", "Engineering"));

        // Create third employee with nickname
        Element employee3 = createEmployee(doc, root, new EmployeeData(
                "3", "John", null, "Doe", "john@example.org", "Marketing"));
        Element firstName = getChildElement(employee3, "firstname");
        if (firstName != null) {
            Element nickname = createChild(doc, firstName, "nickname", "Johnny");
            setAttribute(doc, nickname, "preferred", "true");
        }

        // Output based on parameters
        if (outputFile != null) {
            finalizeOut(doc, outputFile, false);
            logger.info("XML written to: " + outputFile);
        } else {
            System.out.println("Output to stdout:");
            finalizeOut(doc, null, false);
        }

        // Return as string
        return finalizeOut(doc, null, true);
    }

    /**
     * Helper method to create an employee element with standard fields.
     */
    private Element createEmployee(Document doc, Element parent, EmployeeData data) {
        Element employee = createChild(doc, parent, "employee", null);
        setAttribute(doc, employee, "id", data.id());

        Element firstNameEl = createChild(doc, employee, "firstname", data.firstName());
        if (data.middleName() != null) {
            createChild(doc, firstNameEl, "middlename", data.middleName());
        }

        createChild(doc, employee, "lastname", data.lastName());
        createChild(doc, employee, "email", data.email());
        createChild(doc, employee, "department", data.department());

        return employee;
    }

    /**
     * Employee data for XML generation.
     */
    private record EmployeeData(String id, String firstName, String middleName,
                                String lastName, String email, String department) {
    }
}
