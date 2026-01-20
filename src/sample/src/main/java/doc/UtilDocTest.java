package doc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class UtilDocTest {

    public static void main(String[] args) {
        String basePath = "src/main/java/javamavenproject/doc/";
        String inputFile = basePath + "test_input.docx";
        String outputFile = basePath + "test_output.docx";

        System.out.println("=== UtilDoc Test ===");
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + outputFile);

        try {
            // Read the input document
            FileInputStream fis = new FileInputStream(inputFile);
            XWPFDocument document = new XWPFDocument(fis);
            fis.close();

            System.out.println("\n--- Original Document Content ---");
            for (XWPFParagraph para : document.getParagraphs()) {
                System.out.println(para.getText());
            }

            // Use UtilDoc.replacePOI to replace placeholders
            UtilDoc.replacePOI(document, "$COMPANY_NAME$", "Acme Corporation");
            UtilDoc.replacePOI(document, "$ADDRESS$", "123 Main Street, New York");
            UtilDoc.replacePOI(document, "$DATE$", "2026-01-18");

            System.out.println("\n--- After Replacement ---");
            for (XWPFParagraph para : document.getParagraphs()) {
                System.out.println(para.getText());
            }

            // Write output
            FileOutputStream fos = new FileOutputStream(outputFile);
            document.write(fos);
            fos.close();
            document.close();

            System.out.println("\n=== SUCCESS ===");
            System.out.println("Output written to: " + outputFile);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
