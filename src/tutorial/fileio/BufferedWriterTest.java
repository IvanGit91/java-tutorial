package tutorial.fileio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BufferedWriterTest {

    private static final String PATH = "./users.csv";

    public static void main(String[] args) {
        //Get the file reference
        Path path = Paths.get(PATH);
        String ragionePattern;
        String passwordPatternOld = "userdem";
        String passwordPatternNew = "userdemo";
        Integer i;
        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (i = 1; i < 1000; i++) {
                ragionePattern = "userdemo";
                ragionePattern = ragionePattern + i;
                writer.write(ragionePattern + "@pec.it," + passwordPatternOld + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
