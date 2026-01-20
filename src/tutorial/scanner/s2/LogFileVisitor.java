package tutorial.scanner.s2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class LogFileVisitor implements FileVisitor<Path> {

    private static final String FOLDER_TO_SKIP = "unlink";

    private final ArrayList<Path> pathFolders = new ArrayList<>();

    private final ArrayList<Path> pathFiles = new ArrayList<>();

    public LogFileVisitor() {
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (dir.getFileName().toString().equals(FOLDER_TO_SKIP)) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        pathFolders.add(dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile())
            pathFiles.add(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public ArrayList<Path> getPathFiles() {
        return pathFiles;
    }

}