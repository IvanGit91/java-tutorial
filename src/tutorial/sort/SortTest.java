package tutorial.sort;

import java.util.*;

public class SortTest {

    public static void main(String[] args) {

        List<String> li = new ArrayList<String>();
        li.add("Zorro");
        li.add("Andrea");
        li.add("Gabriele");
        li.add("Batti");

        Map<String, Integer> map = new HashMap<String, Integer>();

        Map<String, Integer> newmap = new HashMap<String, Integer>();

        map.put("Zorro", 0);
        map.put("Andrea", 1);
        map.put("Gabriele", 2);
        map.put("Batti", 3);

        Collections.sort(li);

        int index = 0;
        for (String string : li) {
            System.out.println("String: " + string + " Index: " + (index++));
            System.out.println("MAP: " + map.get(string));
        }
    }
}
