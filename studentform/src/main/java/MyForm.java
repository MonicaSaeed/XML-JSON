import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

// Url: http://localhost:8080/studentform/form
public class MyForm extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Display the form to enter the number of students
        out.println("<html><head><title>Student Form</title></head><body>");
        out.println("<h2>Enter the number of students:</h2>");
        out.println("<form method='post' action='form'>");
        // to get the number of students from the user
        out.println("<input type='number' name='numberOfStudents' required>");
        out.println("<input type='submit' value='Next'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the number of students from the submitted form data
        String numberOfStudentsString = request.getParameter("numberOfStudents");
        int numberOfStudents = 0;
        // Convert the string value to an int after checking for null
        if (numberOfStudentsString != null && !numberOfStudentsString.isEmpty()) {
            numberOfStudents = Integer.parseInt(numberOfStudentsString);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Display the form with inputs for each student based on the entered number
        out.println("<html><head><title>Student Form</title></head><body>");
        out.println("<h2>Enter details for each student:</h2>");
        out.println("<form method='post' action='submit-form'>");

        // Add a hidden input to store the number of students to be retrieved later in
        // the submit-form servlet
        out.println("<input type='hidden' name='numberOfStudents' value='" + numberOfStudents + "'><br>");

        // Add inputs for each student
        for (int i = 1; i <= numberOfStudents; i++) {
            out.println("<p>Student " + i + ":</p>");
            // get input for id
            out.println("ID: <input type='text' name='id" + i + "' required><br>");
            // get input for first name is characters (a-z) only
            out.println(
                    "First Name: <input type='text' name='firstname" + i + "' pattern='[a-zA-Z]{1,32}' required><br>");
            // get input for last name is characters (a-z) only change to pattern
            // pattern="[A-Za-z]{1,32}"
            out.println("Last Name: <input type='text' name='lastname" + i + "' pattern='[^0-9]+' required><br>");
            out.println("Gender: <input type='text' name='gender" + i + "' required><br>");
            // get input for gpa with constraints <= 0 and >= 4 (float)
            out.println("GPA: <input type='number' name='gpa" + i + "' min='0' max='4' step='0.01' required><br>");
            // get input for level with constraints >= 1 and <= 4
            out.println("Level: <input type='number' name='level" + i + "' min='1' max='4' required><br>");
            // get input for address with characters (a-z) only
            out.println("Address: <input type='text' name='address" + i + "' pattern='[a-zA-Z]+' required><br>");
            out.println("");
        }
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    // }
}