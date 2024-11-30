package library;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class BorrowedBooks
 */

public class BorrowedBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    // Database connection details for Oracle
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl"; 
    private static final String JDBC_USERNAME = "system";  
    private static final String JDBC_PASSWORD = "Laddu#99";  
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowedBooks() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            
            // Establish a connection to the Oracle database
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Query to retrieve borrowed books from the borrow_history table
            String query = "SELECT BORROWID, USERID, BOOK_ID, BORROW_DATE, RETURN_DATE FROM borrow_history";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            // Output HTML table
            out.println("<html><body>");
            out.println("<h2>Borrowed Books</h2>");
            out.println("<table border='1' width='70%' cellpadding='10' cellspacing='1'>");
            out.println("<tr><th>BORROWID</th><th>USERID</th><th>BOOK_ID</th><th>BORROW_DATE</th><th>RETURN_DATE</th></tr>");

            // Loop through the result set and print each row in the HTML table
            while (resultSet.next()) {
                String borrowId = resultSet.getString("BORROWID");
                String userId = resultSet.getString("USERID");
                int bookId = resultSet.getInt("BOOK_ID");
                String borrowDate = resultSet.getString("BORROW_DATE");
                String returnDate = resultSet.getString("RETURN_DATE");

                out.println("<tr>");
                out.println("<td>" + borrowId + "</td>");
                out.println("<td>" + userId + "</td>");
                out.println("<td>" + bookId + "</td>");
                out.println("<td>" + borrowDate + "</td>");
                out.println("<td>" + returnDate + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (ClassNotFoundException e) {
            out.println("Error: Unable to load Oracle JDBC driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("Error: Unable to connect to the database or execute query.");
            e.printStackTrace();
        } finally {
            try {
                // Close the result set, statement, and connection
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
