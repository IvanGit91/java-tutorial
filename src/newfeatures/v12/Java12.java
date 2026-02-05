package newfeatures.v12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java12 {
    public static void main(String[] args) throws IOException {
        // 2.1. String Class New Methods
        // The first one – indent adjusts the indentation of each line based on the integer parameter. If the parameter is greater than zero, new spaces will be inserted at the beginning of each line. On the other hand, if the parameter is less than zero, it removes spaces from the begging of each line.
        String text = "Hello Baeldung!\nThis is Java 12 article.";

        text = text.indent(4);
        System.out.println(text);

        text = text.indent(-10);
        System.out.println(text);

        String text2 = "Baeldung";
        String transformed = text2.transform(value ->
                new StringBuilder(value).reverse().toString()
        );

        // 2.2. File::mismatch Method
        /*
        The method is used to compare two files and find the position of the first mismatched byte in their contents.
        The return value will be in the inclusive range of 0L up to the byte size of the smaller file or -1L if the files are identical.
        Now let’s take a look at two examples. In the first one, we’ll create two identical files and try to find a mismatch. The return value should be -1L:
        */

        Path filePath1 = Files.createTempFile("file1", ".txt");
        Path filePath2 = Files.createTempFile("file2", ".txt");
        Files.writeString(filePath1, "Java 12 Article");
        Files.writeString(filePath2, "Java 12 Article");

        long mismatch = Files.mismatch(filePath1, filePath2);
        //assertEquals(-1, mismatch);

        Path filePath3 = Files.createTempFile("file3", ".txt");
        Path filePath4 = Files.createTempFile("file4", ".txt");
        Files.writeString(filePath3, "Java 12 Article");
        Files.writeString(filePath4, "Java 12 Tutorial");

        long mismatch2 = Files.mismatch(filePath3, filePath4);
        //assertEquals(8, mismatch2);

        // 2.3. Teeing Collector
        // It is a composite of two downstream collectors. Every element is processed by both downstream collectors. Then their results are passed to the merge function and transformed into the final result.
        double mean = Stream.of(1, 2, 3, 4, 5)
                .collect(
                        Collectors.teeing(
                                Collectors.summingDouble(i -> i),
                                Collectors.counting(),
                                (sum, count) -> sum / count
                        )
                );
        //assertEquals(3.0, mean);

        // 2.4. Compact Number Formatting
        // Java 12 comes with a new number formatter – the CompactNumberFormat. It’s designed to represent a number in a shorter form, based on the patterns provided by a given locale.
        NumberFormat likesShort =
                NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.SHORT);
        likesShort.setMaximumFractionDigits(2);
        //assertEquals("2.59K", likesShort.format(2592));

        NumberFormat likesLong =
                NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.LONG);
        likesLong.setMaximumFractionDigits(2);
        //assertEquals("2.59 thousand", likesLong.format(2592));
    }
}
