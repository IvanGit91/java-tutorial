package pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * Parses PDF files and extracts text content.
 * Demonstrates basic PDF text extraction using iText.
 */
public class Parse {

    // Default paths - uses user's home directory
    public static final String DATA_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-output") + FileSystems.getDefault().getSeparator();
    public static final String SRC = DATA_FOLDER + "Attachment1.pdf";
    public static final String DEST = DATA_FOLDER + "Parsed" + FileSystems.getDefault().getSeparator() + "pdfParse.txt";

    public static void main(String[] args) throws IOException {
        File destFile = new File(DEST);
        destFile.getParentFile().mkdirs();
        new Parse().parse(SRC);
    }

    /**
     * Parses a PDF file and extracts text content to a text file.
     *
     * @param filename Path to the source PDF file
     */
    public void parse(String filename) throws IOException {
        PdfReader reader = new PdfReader(filename);
        try (FileOutputStream fos = new FileOutputStream(DEST)) {
            int totalPages = reader.getNumberOfPages();
            for (int page = 1; page <= totalPages; page++) {
                String text = PdfTextExtractor.getTextFromPage(reader, page);
                fos.write(text.getBytes(StandardCharsets.UTF_8));
                fos.write("\n--- Page ".getBytes(StandardCharsets.UTF_8));
                fos.write(String.valueOf(page).getBytes(StandardCharsets.UTF_8));
                fos.write(" ---\n".getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("Text extraction completed: " + totalPages + " pages processed");
        } finally {
            reader.close();
        }
    }
}
