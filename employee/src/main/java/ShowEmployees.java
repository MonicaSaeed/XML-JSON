import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// URL: http://localhost:8080/employee/list
@WebServlet("/list")
public class ShowEmployees extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Employee Information</title></head>");
        out.println("<body>");

        // Call your existing code to read and parse JSON
        ReadJSON.readAndPrintEmployeeInfo(out);

        out.println("</body>");
        out.println("</html>");
    }
}
