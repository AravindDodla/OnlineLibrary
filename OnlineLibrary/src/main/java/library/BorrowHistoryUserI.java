package library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BorrowHistoryUserI extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Oracle DB credentials
    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String DB_USER = "system";
    private final String DB_PASS = "Laddu#99";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get the userID from the session
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        
        // HTML structure for the table
        out.println("<html><head><title>Borrowing History</title></head><body>");
        out.println("<h3>Borrowing History for User ID: " + userID + "</h3>");
        out.println("<table border='1' cellpadding='10'>");
        out.println("<tr><th>Borrow ID</th><th>Book ID</th><th>Borrow Date</th><th>Return Date</th></tr>");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the database connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Prepare SQL query to fetch borrowing history for the specific userID
            String sql = "SELECT BORROWID, BOOK_ID, BORROW_DATE, RETURN_DATE FROM borrow_history WHERE USERID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);

            // Execute the query
            rs = ps.executeQuery();

            // Iterate through the result set and display the records in a table
            while (rs.next()) {
                String borrowID = rs.getString("BORROWID");
                int bookID = rs.getInt("BOOK_ID");
                String borrowDate = rs.getDate("BORROW_DATE").toString();
                String returnDate = rs.getDate("RETURN_DATE") != null ? rs.getDate("RETURN_DATE").toString() : "N/A";

                out.println("<tr><td>" + borrowID + "</td><td>" + bookID + "</td><td>" + borrowDate + "</td><td>" + returnDate + "</td></tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching borrowing history: " + e.getMessage() + "</p>");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}
