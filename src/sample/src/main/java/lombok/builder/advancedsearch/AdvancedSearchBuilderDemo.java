package lombok.builder.advancedsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the usage of AdvancedSearchBuilder with different construction approaches:
 * 1. Traditional constructor
 * 2. Lombok @Builder pattern
 * 3. Static factory method combined with Builder pattern
 */
public class AdvancedSearchBuilderDemo {

    private static final List<AdvancedSearchBuilder> searchList = new ArrayList<>();
    private static final List<AdvancedSearchBuilder> searchListBuilder = new ArrayList<>();

    public static void main(String[] args) {
        String[] fields;
        List<StringPair> pair = new ArrayList<>();
        fields = new String[]{"atecoList"};

        // Using constructor
        searchList.add(new AdvancedSearchBuilder("ATECO Code", List.of(fields), true, "ateco.code", "ateco"));

        // Using Builder pattern
        searchListBuilder.add(AdvancedSearchBuilder.builder()
                .displayName("ATECO Code")
                .fields(List.of(fields))
                .useJoin(true)
                .searchField("ateco.code")
                .specificName("ateco")
                .build());

        // Using static factory method with Builder pattern
        searchListBuilder.add(AdvancedSearchBuilder.type4a("ATECO Code", List.of(fields), true, "ateco.code", "ateco"));

        System.out.println("Search list (constructor): " + searchList);
        System.out.println("Search list (builder): " + searchListBuilder);
    }
}
