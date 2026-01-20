package newfeatures.java16;

import jdk.incubator.vector.IntVector;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Java16 {
    public static void main(String[] args) {
        // 2. Invoke Default Methods From Proxy Instances (JDK-8159746)

        // 3. Day Period Support (JDK-8247781)
        // A new addition to the DateTimeFormatter is the period-of-day symbol “B“, which provides an alternative to the am/pm format:
        LocalTime date = LocalTime.parse("15:25:08.690791");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h B");
        //assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");

        // 4. Add Stream.toList Method (JDK-8180352)
        List<String> integersAsString = Arrays.asList("1", "2", "3");
        // old
        List<Integer> ints = integersAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
        // new
        List<Integer> intsEquivalent = integersAsString.stream().map(Integer::parseInt).toList();

        // 5. Vector API Incubator (JEP-338)
        int[] a = {1, 2, 3, 4};
        int[] b = {5, 6, 7, 8};
        // old
        var c = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] * b[i];
        }
        // new
        var vectorA = IntVector.fromArray(IntVector.SPECIES_128, a, 0);
        var vectorB = IntVector.fromArray(IntVector.SPECIES_128, b, 0);
        var vectorC = vectorA.mul(vectorB);
        vectorC.intoArray(c, 0);

        // 7. Pattern Matching for instanceof (JEP-394)
        Object obj = "TEST";
        // old
        if (obj instanceof String t) {
            // do some logic...
        }
        // new
        if (obj instanceof String t) {
            // do some logic
        }
    }
}
