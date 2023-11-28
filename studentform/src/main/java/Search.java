import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@WebServlet("/search")
public class Search extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // Display the search form
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search Form</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Search Form</h2>");
        out.println("<form action='search' method='post'>");
        out.println("<label for='searchType'>Search Type:</label>");
        out.println("<select name='searchType' id='searchType'>");
        out.println("<option value='ID'>id</option>");
        out.println("<option value='FirstName'>First Name</option>");
        out.println("<option value='LastName'>Last Name</option>");
        out.println("<option value='Gender'>Gender</option>");
        out.println("<option value='GPA'>GPA</option>");
        out.println("<option value='Level'>Level</option>");
        out.println("<option value='Address'>Address</option>");

        out.println("</select>");
        out.println("<br>");
        out.println("<label for='searchTerm'>Search Term:</label>");
        out.println("<input type='text' name='searchTerm' id='searchTerm' required>");
        out.println("<br>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // Retrieve search parameters from the form
        String searchType = request.getParameter("searchType");
        String searchTerm = request.getParameter("searchTerm");

        // Search the XML file based on the specified criteria
        List<Student> searchResults = performSearch(searchType, searchTerm);

        // Display search results
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search Results</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Search Results</h2>");

        if (searchResults.isEmpty()) {
            out.println("<p>No results found for the specified criteria.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>First Name</th>");
            out.println("<th>Last Name</th>");
            out.println("<th>Gender</th>");
            out.println("<th>GPA</th>");
            out.println("<th>Level</th>");
            out.println("<th>Address</th>");
            out.println("</tr>");

            for (Student student : searchResults) {
                out.println("<form method='post' action='edit'>");
                out.println("<tr>");
                out.println("<td>" + student.ID + "</td>");
                // make avaliable to click on the first name to edit the student first name
                // out.println("<td><a href='edit?ID=" + student.ID + "'>" + student.firstName
                // + "</a></td>");
                out.println("<input type='hidden' name='id' value='" + student.ID + "'>");
                out.println(
                        "<td><input type='text' name='firstname' value ='" + student.firstName + "' required></td>");
                out.println("<td><input type='text' name='lastname' value ='" + student.lastName + "' required></td>");
                out.println("<td><input type='text' name='gender' value ='" + student.gender + "' required></td>");
                out.println("<td><input type='text' name='gpa' value ='" + student.GPA + "' required></td>");
                out.println("<td><input type='text' name='level' value ='" + student.level + "' required></td>");
                out.println("<td><input type='text' name='address' value ='" + student.address + "' required></td>");

                // Add a delete button for each row
                out.println("<td>");
                out.println("<input type='submit' value='Edit'>");
                out.println("<form action='delete' method='post'>");
                // hidden input to store the ID of the student to delete
                out.println("<input type='hidden' name='id' value='" + student.ID + "'>");
                out.println("<input type='submit' value='Delete'>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");

                out.println("</form>");
            }

            out.println("</table>");
        }

        out.println("</body>");
        out.println("</html>");
    }

    private List<Student> performSearch(String searchType, String searchTerm) {
        List<Student> searchResults = new ArrayList<>();

        try {
            // Load the XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("data/Students.xml");

            // Normalize the XML structure
            doc.getDocumentElement().normalize();

            // Get the root element
            Element rootElement = doc.getDocumentElement();

            // Get all student nodes
            NodeList studentNodes = rootElement.getElementsByTagName("Student");

            // Iterate through the student nodes
            for (int i = 0; i < studentNodes.getLength(); i++) {
                Node studentNode = studentNodes.item(i);

                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;

                    // Retrieve values from XML

                    String id = studentElement.getAttribute("ID");
                    String firstName = studentElement.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = studentElement.getElementsByTagName("lastName").item(0).getTextContent();
                    String gender = studentElement.getElementsByTagName("Gender").item(0).getTextContent();
                    String level = studentElement.getElementsByTagName("level").item(0).getTextContent();
                    String address = studentElement.getElementsByTagName("address").item(0).getTextContent();
                    String gpa = studentElement.getElementsByTagName("gpa").item(0).getTextContent();

                    // Perform the search based on the specified criteria
                    if (("FirstName".equalsIgnoreCase(searchType) && firstName.contains(searchTerm))) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    } else if (("GPA".equalsIgnoreCase(searchType) && gpa.equals(searchTerm))) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    } else if (("LastName").equalsIgnoreCase(searchType) && lastName.contains(searchTerm)) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    } else if (("Gender").equalsIgnoreCase(searchType) && gender.contains(searchTerm)) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    } else if (("Level").equalsIgnoreCase(searchType) && level.contains(searchTerm)) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    } else if (("Address").equalsIgnoreCase(searchType) && address.contains(searchTerm)) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    } else if (("ID").equalsIgnoreCase(searchType) && id.contains(searchTerm)) {
                        Student student = new Student(id, firstName, lastName, gender, gpa, level, address);
                        searchResults.add(student);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchResults;
    }
}
