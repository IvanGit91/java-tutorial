package json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Demonstrates JSON parsing and generation using both json-simple and Gson libraries.
 * Shows different approaches to working with JSON in Java.
 */
public class GenerateJson extends UtilJson {

    private static final Logger logger = LoggerFactory.getLogger(GenerateJson.class);
    private static final String OUTPUT_FILE = DATA_FOLDER + "output.json";

    public static void main(String[] args) {
        GenerateJson generator = new GenerateJson();

        // JSON-Simple examples (commented out by default)
        // generator.parseJsonSimple();
        // generator.writeJsonSimple();

        // Gson examples
        generator.demonstrateGsonParsing();
    }

    /**
     * Parses an individual employee JSON object.
     *
     * @param employee the JSON object containing employee data
     */
    private static void parseEmployeeObject(JSONObject employee) {
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        String firstName = (String) employeeObject.get("firstName");
        String lastName = (String) employeeObject.get("lastName");
        String website = (String) employeeObject.get("website");

        logger.info("Employee: {} {} - {}", firstName, lastName, website);
    }

    /**
     * Parses a JSON file using json-simple library.
     * Reads an array of employee objects from a JSON file.
     */
    @SuppressWarnings("unchecked")
    public void parseJsonSimple() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(OUTPUT_FILE)) {
            Object obj = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) obj;

            logger.info("Parsed employee list: {}", employeeList);

            employeeList.forEach(emp -> parseEmployeeObject((JSONObject) emp));

        } catch (IOException | ParseException e) {
            logger.error("Failed to parse JSON file: {}", OUTPUT_FILE, e);
        }
    }

    /**
     * Writes a JSON file using json-simple library.
     * Creates an array of employee objects and writes to a file.
     */
    @SuppressWarnings("unchecked")
    public void writeJsonSimple() {
        // First Employee
        JSONObject employee1Details = new JSONObject();
        employee1Details.put("firstName", "Lokesh");
        employee1Details.put("lastName", "Gupta");
        employee1Details.put("website", "howtodoinjava.com");

        JSONObject employee1 = new JSONObject();
        employee1.put("employee", employee1Details);

        // Second Employee
        JSONObject employee2Details = new JSONObject();
        employee2Details.put("firstName", "Brian");
        employee2Details.put("lastName", "Schultz");
        employee2Details.put("website", "example.com");

        JSONObject employee2 = new JSONObject();
        employee2.put("employee", employee2Details);

        // Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employee1);
        employeeList.add(employee2);

        // Write JSON file
        try (FileWriter file = new FileWriter(OUTPUT_FILE)) {
            file.write(employeeList.toJSONString());
            file.flush();
            logger.info("JSON file written successfully: {}", OUTPUT_FILE);
        } catch (IOException e) {
            logger.error("Failed to write JSON file: {}", OUTPUT_FILE, e);
        }
    }

    /**
     * Demonstrates JSON parsing and serialization using Gson library.
     */
    public void demonstrateGsonParsing() {
        Person person = new Person("John", "Doe", true);

        // Demonstrate different Gson approaches
        demonstrateGsonJsonTree(person);
        demonstrateGsonSerialization(person);
    }

    /**
     * Demonstrates using Gson's JsonElement tree for manual JSON navigation.
     *
     * @param person the person object to serialize
     */
    private void demonstrateGsonJsonTree(Person person) {
        Gson gson = createGson();
        String jsonString = gson.toJson(person);

        JsonElement jsonElement = gson.toJsonTree(person);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        logger.info("JSON output: {}", jsonString);
        logger.info("First name from JSON: {}", jsonObject.get("firstName").getAsString());
        logger.info("Last name from JSON: {}", jsonObject.get("lastName").getAsString());
        logger.info("Gender from JSON: {}", jsonObject.get("gender").getAsBoolean());
    }

    /**
     * Demonstrates Gson serialization and deserialization.
     *
     * @param person the person object to serialize
     */
    private void demonstrateGsonSerialization(Person person) {
        Gson gson = createGson();

        // Serialize to JSON string
        String jsonString = gson.toJson(person);
        logger.info("Serialized JSON: {}", jsonString);

        // Deserialize from JsonElement
        JsonElement jsonElement = gson.toJsonTree(person);
        Person fromElement = gson.fromJson(jsonElement, Person.class);
        logger.info("Deserialized from JsonElement - Last name: {}", fromElement.getLastName());

        // Deserialize from String
        Person fromString = gson.fromJson(jsonString, Person.class);
        logger.info("Deserialized from String - Last name: {}", fromString.getLastName());
    }
}
