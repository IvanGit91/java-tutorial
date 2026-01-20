package xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Abstract utility class for XML document manipulation using DOM API.
 * Provides reusable methods for parsing, creating, and transforming XML documents.
 *
 * <p>Subclasses can use the protected methods to:
 * <ul>
 *   <li>Parse XML files into Document objects</li>
 *   <li>Create new XML documents programmatically</li>
 *   <li>Output XML to stdout, files, or strings</li>
 *   <li>Navigate and extract data from XML elements</li>
 * </ul>
 */
public abstract class UtilXML {

    private static final Logger logger = Logger.getLogger(UtilXML.class.getName());

    protected UtilXML() {
    }

    // ========================= Validation Utilities =========================

    /**
     * Checks if a string is not null and not empty (after trimming whitespace).
     *
     * @param s the string to check
     * @return true if the string has content, false otherwise
     */
    protected static boolean notNullAndNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // ========================= XML Parsing (Input) =========================

    /**
     * Parses an XML file and returns a normalized Document object.
     *
     * @param inputPath path to the XML file
     * @return parsed Document, or null if parsing fails
     */
    protected static Document initializeIn(String inputPath) {
        Document doc = null;
        try {
            File xmlFile = new File(inputPath);
            if (!xmlFile.exists()) {
                logger.error("XML file not found: " + inputPath);
                return null;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Security: Disable external entities to prevent XXE attacks
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            logger.info("Successfully parsed XML: " + inputPath);
        } catch (ParserConfigurationException e) {
            logger.error("XML parser configuration error", e);
        } catch (SAXException e) {
            logger.error("XML parsing error: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("IO error reading XML file: " + inputPath, e);
        }
        return doc;
    }

    // ========================= XML Generation (Output) =========================

    /**
     * Creates a new empty Document for XML generation.
     *
     * @return new Document, or null if creation fails
     */
    protected static Document initializeOut() {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (ParserConfigurationException e) {
            logger.error("Error creating XML document", e);
        }
        return doc;
    }

    /**
     * Outputs the XML document to various destinations.
     *
     * <p>Output modes:
     * <ul>
     *   <li>stdout: set outputPath to null and asString to false</li>
     *   <li>file: set outputPath to file path and asString to false</li>
     *   <li>string: set asString to true (outputPath is ignored)</li>
     * </ul>
     *
     * @param doc        the Document to output
     * @param outputPath file path for file output (null for stdout)
     * @param asString   if true, returns XML as String instead of writing
     * @return XML string if asString is true, null otherwise
     */
    protected static String finalizeOut(Document doc, String outputPath, boolean asString) {
        return finalizeOut(doc, outputPath, asString, true);
    }

    /**
     * Outputs the XML document with formatting options.
     *
     * @param doc        the Document to output
     * @param outputPath file path for file output (null for stdout)
     * @param asString   if true, returns XML as String
     * @param indent     if true, formats output with indentation
     * @return XML string if asString is true, null otherwise
     */
    protected static String finalizeOut(Document doc, String outputPath, boolean asString, boolean indent) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Configure pretty printing
            if (indent) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            }
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult;

            if (asString) {
                StringWriter writer = new StringWriter();
                streamResult = new StreamResult(writer);
                transformer.transform(domSource, streamResult);
                return writer.toString();
            } else if (outputPath == null) {
                // Output to stdout
                streamResult = new StreamResult(System.out);
                transformer.transform(domSource, streamResult);
                System.out.println(); // Add newline after output
            } else {
                // Output to file
                File outputFile = new File(outputPath);
                outputFile.getParentFile().mkdirs(); // Create directories if needed
                streamResult = new StreamResult(outputFile);
                transformer.transform(domSource, streamResult);
                logger.info("XML written to: " + outputPath);
            }
        } catch (TransformerException e) {
            logger.error("Error transforming XML document", e);
        }
        return null;
    }

    // ========================= Element Navigation =========================

    /**
     * Extracts the namespace prefix from an element's name.
     * Some XML documents use prefixes before field names (e.g., "ns:ElementName").
     *
     * @param element   the element to check
     * @param separator the separator character (typically ":")
     * @return the prefix including separator, or empty string if no prefix
     */
    protected static String getPrefix(Element element, String separator) {
        String nodeName = element.getNodeName();
        int separatorIndex = nodeName.indexOf(separator);
        if (separatorIndex != -1) {
            return nodeName.substring(0, separatorIndex + 1);
        }
        return "";
    }

    /**
     * Finds and prints an attribute value from an element.
     *
     * @param element   the element to search
     * @param attribute the attribute name to find
     */
    protected static void findAttribute(Element element, String attribute) {
        String value = element.getAttribute(attribute);
        if (notNullAndNotEmpty(value)) {
            System.out.println(attribute + ": " + value);
        }
    }

    /**
     * Gets an attribute value from an element.
     *
     * @param element   the element to search
     * @param attribute the attribute name
     * @return the attribute value, or null if not found or empty
     */
    protected static String getAttribute(Element element, String attribute) {
        String value = element.getAttribute(attribute);
        return notNullAndNotEmpty(value) ? value : null;
    }

    // ========================= Element Creation =========================

    /**
     * Creates and appends a root element to the document.
     *
     * @param doc  the Document
     * @param name the root element name
     * @return the created root Element
     */
    protected static Element createRoot(Document doc, String name) {
        Element root = doc.createElement(name);
        doc.appendChild(root);
        return root;
    }

    /**
     * Creates a child element under a parent element.
     * Optionally creates a text node with the specified content.
     *
     * @param doc         the Document
     * @param parent      the parent Element
     * @param name        the child element name
     * @param textContent text content for the element (null for empty element)
     * @return the created child Element
     */
    protected static Element createChild(Document doc, Element parent, String name, String textContent) {
        Element child = doc.createElement(name);
        if (textContent != null) {
            child.appendChild(doc.createTextNode(textContent));
        }
        parent.appendChild(child);
        return child;
    }

    /**
     * Sets an attribute on an element.
     *
     * @param doc       the Document
     * @param element   the Element to modify
     * @param attribute the attribute name
     * @param value     the attribute value
     */
    protected static void setAttribute(Document doc, Element element, String attribute, String value) {
        Attr attr = doc.createAttribute(attribute);
        attr.setValue(value);
        element.setAttributeNode(attr);
    }

    // ========================= Element Query Utilities =========================

    /**
     * Safely gets the text content of a child element by tag name.
     *
     * @param parent  the parent element
     * @param tagName the child element tag name
     * @return the text content, or null if element not found or empty
     */
    protected static String getChildTextContent(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes != null && nodes.getLength() > 0) {
            String content = nodes.item(0).getTextContent();
            return notNullAndNotEmpty(content) ? content.trim() : null;
        }
        return null;
    }

    /**
     * Safely gets a child element by tag name.
     *
     * @param parent  the parent element
     * @param tagName the child element tag name
     * @return the child Element, or null if not found
     */
    protected static Element getChildElement(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes != null && nodes.getLength() > 0) {
            return (Element) nodes.item(0);
        }
        return null;
    }

    /**
     * Checks if a child element exists and has non-empty text content.
     *
     * @param parent  the parent element
     * @param tagName the child element tag name
     * @return true if element exists with content
     */
    protected static boolean hasChildWithContent(Element parent, String tagName) {
        return getChildTextContent(parent, tagName) != null;
    }
}
