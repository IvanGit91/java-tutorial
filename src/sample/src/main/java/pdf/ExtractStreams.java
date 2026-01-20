package pdf;

import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * Extracts stream objects from PDF files.
 * Demonstrates how to access and save raw PDF stream data.
 *
 * @author iText
 */
public class ExtractStreams {

    // Default paths - uses user's home directory
    public static final String DATA_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-output") + FileSystems.getDefault().getSeparator();
    public static final String SRC = DATA_FOLDER + "Attachment1.pdf";
    public static final String DEST = DATA_FOLDER + "Parsed" + FileSystems.getDefault().getSeparator() + "stream%s";

    public static void main(String[] args) throws IOException {
        File destFile = new File(String.format(DEST, ""));
        destFile.getParentFile().mkdirs();
        new ExtractStreams().parse(SRC, DEST);
    }

    /**
     * Parses a PDF file and extracts all stream objects to separate files.
     *
     * @param src  Source PDF file path
     * @param dest Destination pattern for output files (uses String.format with stream index)
     */
    public void parse(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        try {
            for (int i = 1; i <= reader.getXrefSize(); i++) {
                PdfObject obj = reader.getPdfObject(i);
                if (obj != null && obj.isStream()) {
                    PRStream stream = (PRStream) obj;
                    byte[] bytes;
                    try {
                        bytes = PdfReader.getStreamBytes(stream);
                    } catch (UnsupportedPdfException e) {
                        bytes = PdfReader.getStreamBytesRaw(stream);
                    }
                    try (FileOutputStream fos = new FileOutputStream(String.format(dest, i))) {
                        fos.write(bytes);
                    }
                }
            }
            System.out.println("Stream extraction completed successfully");
        } finally {
            reader.close();
        }
    }
}
