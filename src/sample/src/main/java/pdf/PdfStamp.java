package pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * Demonstrates PDF stamping operations using iText.
 * Can be used to modify existing PDF files (add watermarks, annotations, etc.)
 */
public class PdfStamp {

    // Default paths - uses user's home directory
    public static final String DATA_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-output") + FileSystems.getDefault().getSeparator();
    public static final String INPUT = DATA_FOLDER + "input.pdf";
    public static final String OUTPUT = DATA_FOLDER + "Output" + FileSystems.getDefault().getSeparator() + "stamped.pdf";

    public static void main(String[] args) {
        File inputFile = new File(INPUT);
        File outputFile = new File(OUTPUT);

        // Create output directory if it doesn't exist
        outputFile.getParentFile().mkdirs();

        try (FileInputStream is = new FileInputStream(inputFile);
             FileOutputStream fileOutput = new FileOutputStream(outputFile)) {

            PdfReader reader = new PdfReader(is);
            PdfStamper stamper = new PdfStamper(reader, fileOutput);

            // Add stamping operations here (watermarks, annotations, form filling, etc.)

            stamper.close();
            reader.close();

            System.out.println("PDF stamping completed. Output size: " + outputFile.length() + " bytes");

        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error processing PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
