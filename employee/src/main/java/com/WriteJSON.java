package com;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet("/writeJSON")

public class WriteJSON extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // public static void writeNewEmp(String firstName, String lastName, String id,
        // String designation, int num,
        // String[] languageName, int[] scoreOutof100) {
        // First Employee

        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String id = request.getParameter("id");
        String designation = request.getParameter("designation");
        int num = Integer.parseInt(request.getParameter("num"));
        String[] languageName = new String[num];
        int[] scoreOutof100 = new int[num];
        for (int i = 0; i < num; i++) {
            languageName[i] = request.getParameter("languageName");
            scoreOutof100[i] = Integer.parseInt(request.getParameter("scoreOutof100"));
        }

        JSONObject employeeDetails = new JSONObject();
        // parse id as int
        int intid = Integer.parseInt(id);
        employeeDetails.put("FirstName", firstName);
        employeeDetails.put("LastName", lastName);
        employeeDetails.put("EmployeeID", intid);
        employeeDetails.put("Designation", designation);
        JSONArray knownLanguages = new JSONArray();
        for (int i = 0; i < num; i++) {
            JSONObject languageDetails = new JSONObject();
            languageDetails.put("LanguageName", languageName[i]);
            languageDetails.put("ScoreOutof100", scoreOutof100[i]);
            knownLanguages.add(languageDetails);
        }

        employeeDetails.put("KnownLanguages", knownLanguages);

        // Read existing employees from the file
        JSONArray employeeList = readExistingEmployees();

        // Add the new employee to the list
        employeeList.add(employeeDetails);

        // Write the updated employee list back to the file
        writeEmployeeListToFile(employeeList);

        // after writing data to the file, redirect the request to ShowEmployees
        response.sendRedirect("list");
    }

    private static JSONArray readExistingEmployees() {
        JSONArray employeeList = new JSONArray();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(
                "data\\Employee.json")) {
            Object obj = jsonParser.parse(reader);
            if (obj instanceof JSONArray) {
                employeeList = (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    private static void writeEmployeeListToFile(JSONArray employeeList) {
        try (FileWriter file = new FileWriter(
                "data\\Employee.json")) {
            file.write(employeeList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
