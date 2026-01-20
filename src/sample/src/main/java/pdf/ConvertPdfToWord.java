package pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * Demonstrates converting PDF documents to Word format.
 * Extracts text from PDF pages and creates a Word document.
 */
public class ConvertPdfToWord {

    // Default folders - uses user's home directory
    public static final String DATA_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-output") + FileSystems.getDefault().getSeparator();
    public static final String OUTPUT_FILE_NAME = "pdfConverted.docx";
    public static final String PDF_NAME = "Attachment1.pdf";

    public static void main(String[] args) {
        ConvertPdfToWord converter = new ConvertPdfToWord();
        converter.convert();
    }

    /**
     * Converts a PDF file to Word document by extracting text from each page.
     */
    public void convert() {
        String pdfPath = DATA_FOLDER + PDF_NAME;
        String outputPath = DATA_FOLDER + OUTPUT_FILE_NAME;

        try (XWPFDocument doc = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(outputPath)) {

            PdfReader reader = new PdfReader(pdfPath);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                TextExtractionStrategy strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                String text = strategy.getResultantText();
                System.out.println("Page " + i + " processed successfully");

                XWPFParagraph p = doc.createParagraph();
                XWPFRun run = p.createRun();
                run.setText(text);
                run.addBreak(BreakType.PAGE);
            }

            doc.write(out);
            reader.close();
            System.out.println("Conversion completed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
