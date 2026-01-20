package tutorial.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionSyncDemo {
    public static void main(String[] args) {
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        synchronized (synchronizedList) {
            synchronizedList.add(1);
        }

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 42); // Thread-safe
    }
}
