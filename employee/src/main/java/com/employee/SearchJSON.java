package com.employee;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet("/searchJSON")
public class SearchJSON extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // Retrieve search parameters from the request
        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        System.out.println("Search type: " + searchType);
        System.out.println("Search value: " + searchValue);

        // Read and parse JSON data
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(
                "data\\Employee.json")) {
            Object obj = parser.parse(reader);
            JSONArray employeeArray = (JSONArray) obj;

            int matchCount = 0;

            // Iterate through the array to find matching records
            for (Object empObj : employeeArray) {
                JSONObject employee = (JSONObject) empObj;
                String fieldValue = (String) employee.get(searchType);

                if (fieldValue != null && fieldValue.equals(searchValue)) {
                    matchCount++;
                    // Match found, send the matched employee data to the web browser
                    out.println("<html><body>");
                    out.println("<h1>Match found:</h1>");
                    out.println("<p>" + employee.toJSONString() + "</p>");
                    out.println("</body></html>");

                }
            }
            if (matchCount == 0) {
                // If no match is found
                out.println("<html><body>");
                out.println("<h1>No match found for the specified criteria.</h1>");
                out.println("</body></html>");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
