package com.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addNewEmployee")
public class AddNewEmp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Add New Employee</title>");
        out.println("<script>");
        out.println("function generateLanguageFields() {");
        out.println("var numLanguages = document.getElementById('numLanguages').value;");
        out.println("var languagesDiv = document.getElementById('languagesDiv');");
        out.println("languagesDiv.innerHTML = '';");

        out.println("for (var i = 1; i <= numLanguages; i++) {");
        out.println(
                "languagesDiv.innerHTML += 'Language Name ' + i + ': <input type=\"text\" name=\"languageName\" required><br>';");
        out.println(
                "languagesDiv.innerHTML += 'Score Out of 100 ' + i + ': <input type=\"number\" name=\"scoreOutof100\" required><br>';");
        out.println("}");

        out.println("}</script>");

        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Add New Employee</h1>");
        out.println("<form action='writeJSON' method='post'>");
        out.println("First Name: <input type='text' name='firstname' required><br>");
        out.println("Last Name: <input type='text' name='lastname' required><br>");
        out.println("Employee ID: <input type='text' name='id' required><br>");
        out.println("Designation: <input type='text' name='designation' required><br>");
        out.println(
                "Number of Languages Known: <input type='number' id='numLanguages' name='num' required oninput='generateLanguageFields()'><br>");
        out.println("<div id='languagesDiv'></div>");
        out.println("<input type='submit' value='Add'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
