package lombok.builder.advancedsearch;

import lombok.*;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Advanced search builder for dynamic query construction.
 * Demonstrates the Builder pattern with Lombok annotations.
 * <p>
 * This class is used to build complex search criteria for database queries,
 * supporting various input types (text, dropdown, checkbox, autocomplete, date).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AdvancedSearchBuilder {

    static Logger logger = Logger.getLogger(AdvancedSearchBuilder.class.getName());

    // UI-side fields
    private String value;                                       // The value entered by the user
    private Double numericValue;                                // The numeric value entered by the user
    private List<String> values = new ArrayList<>();            // Multiple values entered by the user
    private List<StringPair> valuePairs = new ArrayList<>();    // Value pairs entered by the user

    // Fields populated by constructor
    private String displayName;         // The symbolic name displayed in the view
    private int fieldType;              // To distinguish view cases: 1=text, 2=selectOneMenu, 3=selectManyMenu, 4=autocomplete, 5=date
    private char version;               // a,b,c,d... to distinguish various subtypes
    private List<String> fields = new ArrayList<>();            // Fields to search in the database (configured by bean)
    private List<String> databaseValues = new ArrayList<>();    // Values to display in the list, fetched from database by bean
    private boolean visible = true;     // Whether it should be visible on the page. Used for table filters activated from interface
    private boolean useJoin = false;    // Whether to perform joins on the "fields"
    private String searchField;         // The field to search in the joined table
    private List<StringPair> valueLabelPairs = new ArrayList<>();   // When view needs both value and label
    private List<StringPair> fieldAliasPairs = new ArrayList<>();   // The alias assigned to value for join calculated with function
    private String specificName;
    private String[] joinType = new String[]{"join"};
    private StringTokenizer tokenizer;
    private String customFrom = "";
    private List<String> customWhere = new ArrayList<>();
    private Date startDate, endDate;
    private List<String> operators = new ArrayList<>();
    private List<String> andOrOperators = new ArrayList<>();
    private boolean hideLabel = false;
    private Class<?> numericClass = null;

    // Other fields
    private Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault());
    private Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault());

    // Separator constructor
    public AdvancedSearchBuilder(boolean separator) {
        this.fieldType = -1;
    }

    public AdvancedSearchBuilder(boolean separator, String displayName) {
        this.displayName = displayName;
        this.fieldType = -1;
    }

    // CASE 1 a): Free text input that searches a database field
    public AdvancedSearchBuilder(String displayName, List<String> fields) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = false;
        this.fieldType = 1;
        this.version = 'a';
    }

    // CASE 1 b): Free numeric input that searches a database field
    public AdvancedSearchBuilder(String displayName, List<String> fields, Character version) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = false;
        numericClass = Integer.class;
        this.fieldType = 1;
        this.version = 'b';
    }

    // CASE 1 b): Free text input that searches a field with join in database
    /*
     * Example usage:
     * fields = UtilFunction.strings("apprentices");
     * search.add(new AdvancedSearchBuilder("Apprentice Name", Arrays.asList(fields), "naturalPerson.name"));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, String searchField) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = true;
        setAlias();
        this.searchField = searchField;
        this.fieldType = 1;
        this.version = 'b';
    }

    // CASE 1 c): Free text input that searches a field with outer join in database
    public AdvancedSearchBuilder(String displayName, List<String> fields, String searchField, boolean outerJoin) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = true;
        setAlias();
        this.searchField = searchField;
        this.joinType = new String[]{"left join", "left join"};
        this.fieldType = 1;
        this.version = 'c';
    }

    // CASE 1 d): Free text input with outer join and custom from/alias
    /*
     * Example usage:
     * fields = UtilFunction.strings("pf1", "pf2");
     * from = "left join c.project.internsType1 as po1 left join c.project.internsType2 as po2 left join po1.naturalPerson pf1 left join po2.naturalPerson pf2";
     * search.add(new AdvancedSearchBuilder("Intern Tax ID", Arrays.asList(fields), "taxId", from, Arrays.asList(fields)));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, String searchField, String customFrom, List<String> customAlias) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = true;
        this.searchField = searchField;
        this.customFrom = " " + customFrom;
        customAlias.forEach(e -> this.fieldAliasPairs.add(new StringPair(e, e)));
        this.fieldType = 1;
        this.version = 'd';
    }

    // CASE 2: SelectOneMenu for selecting a field without join
    /*
     * Example usage:
     * search.add(new AdvancedSearchBuilder("Corporate Group", Arrays.asList("legalPerson.corporateGroup.description"), Utils.corporateGroupDAO.getAllHQLSelectOne("description")));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, List<String> databaseValues) {
        this.displayName = displayName;
        this.fields = fields;
        this.databaseValues = databaseValues;
        this.useJoin = false;
        this.fieldType = 2;
        this.version = 'a';
    }

    // CASE 3 a): SelectCheckboxMenu for selecting a field without join
    /*
     * Example usage:
     * fields = new String[] {"intermediary1.name", "intermediary2.name"};
     * search.add(new AdvancedSearchBuilder("Intermediary", Arrays.asList(fields), true, Utils.intermediaryDAO.getAllHQLSelectOne("name")));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, boolean useJoin, List<String> databaseValues) {
        this.displayName = displayName;
        this.fields = fields;
        this.databaseValues = databaseValues;
        this.useJoin = false;
        this.fieldType = 3;
        this.version = 'a';
    }

    // CASE 3 b): SelectCheckboxMenu for multiple selection of databaseValues, also handles list selection and table join
    /*
     * Example usage:
     * fields = new String[] {"macroSectorList"};
     * search.add(new AdvancedSearchBuilder("Company Macro Sectors", Arrays.asList(fields), true, Utils.companyMacroSectorDAO.getAllHQLSelectOne("description"), "companyMacroSector.description"));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, boolean useJoin, List<String> databaseValues, String searchField) {
        this.displayName = displayName;
        this.fields = fields;
        this.databaseValues = databaseValues;
        this.searchField = searchField;
        setAlias();
        this.useJoin = useJoin;
        this.fieldType = 3;
        this.version = 'b';
    }

    // CASE 3 c): SelectCheckboxMenu for multiple selection with key-value databaseValues, also handles list selection and table join
    /*
     * Without join:
     * fields = new String[] {"legalPerson.municipality.description"};
     * search.add(new AdvancedSearchBuilder("Legal Headquarters", Arrays.asList(fields), false, Utils.atecoDAO.getAllHQLSelectMulti("code, description"), null, true));
     *
     * With join:
     * fields = new String[] {"atecoList"};
     * search.add(new AdvancedSearchBuilder("ATECO Code", Arrays.asList(fields), true, Utils.atecoDAO.getAllHQLSelectMulti("code,description"), "ateco.code", true));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, boolean useJoin, List<StringPair> keyValueDB, String searchField, boolean multi) {
        this.displayName = displayName;
        this.fields = fields;
        this.valueLabelPairs = keyValueDB;
        this.searchField = searchField;
        setAlias();
        this.useJoin = useJoin;
        this.fieldType = 3;
        this.version = 'c';
    }

    // CASE 3 d): SelectCheckboxMenu with manually mapped value-label fields
    /*
     * Example usage:
     * pair.add(new StringPair("Small","S"));
     * pair.add(new StringPair("Medium","M"));
     * pair.add(new StringPair("Large","L"));
     * fields = new String[] {"smeClassification"};
     * search.add(new AdvancedSearchBuilder("SME Classification", Arrays.asList(fields), pair, true));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, List<StringPair> valueLabelPairs, boolean manualMapping) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = false;
        this.valueLabelPairs = valueLabelPairs;
        this.fieldType = 3;
        this.version = 'd';
    }

    // CASE 3 e): SelectCheckboxMenu with manually mapped value-label fields and specific name
    public AdvancedSearchBuilder(String displayName, List<String> fields, List<StringPair> valueLabelPairs, boolean manualMapping, String specificName) {
        this.displayName = displayName;
        this.fields = fields;
        this.useJoin = false;
        this.valueLabelPairs = valueLabelPairs;
        this.fieldType = 3;
        this.version = 'e';
        this.specificName = specificName;
    }

    // CASE 4 a): Autocomplete for multiple selection with key-value databaseValues, handles list selection and table join
    /*
     * Example usage:
     * fields = new String[] {"atecoList"};
     * search.add(new AdvancedSearchBuilder("ATECO Code", Arrays.asList(fields), true, "ateco.code", true));
     */
    public AdvancedSearchBuilder(String displayName, List<String> fields, boolean useJoin, String searchField, String specificName) {
        this.displayName = displayName;
        this.fields = fields;
        this.searchField = searchField;
        setAlias();
        this.useJoin = useJoin;
        this.specificName = specificName;
        this.fieldType = 4;
        this.version = 'a';
    }

    // CASE 5 a)b): Single date input
    public AdvancedSearchBuilder(String displayName, List<String> fields, List<String> operators, boolean useJoin, int fieldType, char version) {
        this.displayName = displayName;
        this.fields = fields;
        this.operators = operators;
        this.useJoin = useJoin;
        this.fieldType = 5;
        this.version = version;
        if (version == 'b')
            this.hideLabel = true;
    }

    /**
     * Static factory method using Builder pattern for CASE 4a (autocomplete).
     */
    public static AdvancedSearchBuilder type4a(String displayName, List<String> fields, boolean useJoin, String searchField, String specificName) {
        return AdvancedSearchBuilder.builder()
                .displayName(displayName)
                .fields(fields)
                .useJoin(useJoin)
                .searchField(searchField)
                .specificName(specificName)
                .fieldType(4)
                .version('a')
                .build()
                .setAlias();
    }

    // Non-visible elements: Table filter fields to search
    public void setHiddenSearch(String displayName, List<String> fields, List<String> values) {
        this.displayName = displayName;
        this.fields = fields;
        this.values = values;
        this.visible = false;
        this.fieldType = 0;
        this.version = 'a';
    }

    // Non-visible elements: Table filter fields to search
    public void setHiddenSearch(String displayName, List<String> fields, String value) {
        this.displayName = displayName;
        this.fields = fields;
        this.value = value;
        this.visible = false;
        this.fieldType = 0;
        this.version = 'a';
    }

    // Sets aliases for joins, using random letters based on field length
    public AdvancedSearchBuilder setAlias() {
        for (String field : fields) {
            fieldAliasPairs.add(new StringPair(field, getAlias(field)));
        }
        return this;
    }

    // Sets aliases for joins, using random letters based on field length
    public String getAlias(String field) {
        String tempAlias = "";
        if (fields.size() == 1) {
            String[] alias = displayName.split(" ");
            tempAlias = alias.length > 1 ?
                    Character.toString(alias[0].charAt(0)).toLowerCase() + Character.toString(alias[1].charAt(0)).toLowerCase() :
                    Character.toString(alias[0].charAt(0)).toLowerCase() + Character.toString(alias[0].charAt(1)).toLowerCase();

        } else {
            tempAlias = Character.toString(field.charAt(0)).toLowerCase() +
                    Character.toString(field.charAt(field.length() / 2)).toLowerCase() +
                    Character.toString(field.charAt(field.length() - 1)).toLowerCase();
        }
        return tempAlias;
    }

    /**
     * Clears all user-entered values, resetting the search criteria.
     */
    public void clear() {
        value = null;
        numericValue = null;
        numericClass = null;
        values = new ArrayList<>();
        valuePairs = new ArrayList<>();
        startDate = null;
        endDate = null;
    }
}
