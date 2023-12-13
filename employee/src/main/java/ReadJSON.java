
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {
    public static void main(String[] args) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(
                "D:\\fcai\\fourth level #1\\SOA\\XML\\employee\\src\\main\\java\\Employee.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;

            // System.out.println(employeeList);

            for (int i = 0; i < employeeList.size(); i++) {
                System.out.println("Employee #" + i);
                parseEmployeeObject((JSONObject) employeeList.get(i));
                System.out.println("-----------------------------------------------");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseEmployeeObject(JSONObject employee) {
        // Get employee object within list
        JSONObject employeeObject = (JSONObject) employee;

        // Get employee first name
        String firstName = (String) employeeObject.get("FirstName");
        System.out.println("first name is: " + firstName);

        // Get employee last name
        String lastName = (String) employeeObject.get("LastName");
        System.out.println("last name is: " + lastName);

        // Get employee id
        Object id = employeeObject.get("EmployeeID");
        System.out.println("employee id is: " + id);

        // Get employee designation
        String designation = (String) employeeObject.get("Designation");
        System.out.println("Designation is: " + designation);

        // Get employee knownLanguages
        Object knownLanguages = employeeObject.get("KnownLanguages");
        System.out.println("KnownLanguages is: " + knownLanguages);
        // for (int i = 0; i < ((JSONArray) knownLanguages).size(); i++) {
        // // System.out.println(((JSONArray) knownLanguages).get(i));
        // for (int j = 0; j < ((JSONArray) knownLanguages).size(); j++) {
        // // print LanguageName from "KnownLanguages": [
        // // {
        // // "LanguageName": "Perl",
        // // "ScoreOutof100": 30
        // // },
        // // {
        // // "LanguageName": "Java",
        // // "ScoreOutof100": 65
        // // },
        // // {
        // // "LanguageName": "C++",
        // // "ScoreOutof100": 70
        // // }
        // // ]
        // System.out.println(i);
        // String languageName = (String) ((JSONObject) ((JSONArray)
        // knownLanguages).get(j)).get("LanguageName");
        // System.out.println("LanguageName is: " + languageName);
        // String scoreOutof100 = (String) ((JSONObject) ((JSONArray)
        // knownLanguages).get(j)).get("ScoreOutof100");
        // System.out.println("ScoreOutof100 is: " + scoreOutof100);
        // }
        // }

    }
}