package tutorial.maps;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TMaps {

    public static void main(String[] args) {

        Map<List<String>, String> map = new HashMap<>();

        List<String> lista = List.of("ciao", "hey");
        map.put(lista, "yo");

        List<String> listaNot = List.of("cill", "hey");
        System.out.println(map.containsKey(lista));
        System.out.println(map.containsKey(listaNot));

    }

}
