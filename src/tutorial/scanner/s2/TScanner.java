package tutorial.scanner.s2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class TScanner {

    public static void main(String[] args) {

        String folderName = "path-to-folder";
        String line;

        LogFileVisitor logFiles = new LogFileVisitor();
        try {
            Files.walkFileTree(Paths.get(folderName), logFiles);
            if (!logFiles.getPathFiles().isEmpty()) {
                for (Path f : logFiles.getPathFiles()) {
                    try (Scanner file = new Scanner(f)) {
                        while (file.hasNext()) {
                            line = file.nextLine();
                            if (line.contains("stringa")) {
                                Stream.of(line).map(l -> l.split(" ")).map(l -> l[1].split("\\|"))
                                        .forEach(l -> System.out.println("L: " + l));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
