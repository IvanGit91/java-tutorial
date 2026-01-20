package pdf;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * Simple utility for copying PDF files.
 * Demonstrates basic file I/O operations with buffered streams.
 */
public class CopyPdf {

    // Default paths - uses user's home directory
    public static final String DATA_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-output") + FileSystems.getDefault().getSeparator();
    public static final String INPUT = DATA_FOLDER + "input.pdf";
    public static final String OUTPUT = DATA_FOLDER + "Output" + FileSystems.getDefault().getSeparator() + "copy.pdf";

    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        File inputFile = new File(INPUT);
        File outputFile = new File(OUTPUT);

        // Create output directory if it doesn't exist
        outputFile.getParentFile().mkdirs();

        try (InputStream is = new FileInputStream(inputFile);
             OutputStream os = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = is.read(buffer, 0, buffer.length)) > 0) {
                os.write(buffer, 0, bytesRead);
            }

            System.out.println("File copy completed successfully");
            System.out.println("Input: " + inputFile.getAbsolutePath());
            System.out.println("Output: " + outputFile.getAbsolutePath());
            System.out.println("Size: " + outputFile.length() + " bytes");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
