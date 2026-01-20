package newfeatures.java19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Java19 {
    public static void main(String[] args) {
        // 1 New Methods to Create Preallocated HashMaps
        List<String> list = new ArrayList<>(120);

        // Intuitively, one would think that this HashMap offers space for 120 mappings.
        Map<String, Integer> map = new HashMap<>(120);
        // This is because the HashMap is initialized with a default load factor of 0.75.
        // This means that as soon as the HashMap is 75% full, it is rebuilt ("rehashed") with double the size
        // So a HashMap for 120 mappings had to be created as follows:
        // for 120 mappings: 120 / 0.75 = 160
        Map<String, Integer> map2 = new HashMap<>(160);
        // we can now write the following instead:
        Map<String, Integer> map3 = HashMap.newHashMap(120);
    }
}
