package library;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class DisplayBooks
 */
public class DisplayBooks extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userID = (session != null) ? (String) session.getAttribute("userID") : null;

        if (userID == null) {
            // No valid session with userID, redirect to login page
            response.sendRedirect("Login.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        out.println("<html><body>");
        out.println("<h1>Available Books</h1>");
        out.println("<p>Welcome, " + session.getAttribute("uname") + "!</p>");

        try {
            // Load and register the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");

            // Query to fetch all books
            String sql = "SELECT book_id, title, genre, publication_year, author_id FROM books";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Create the table to display books
            out.println("<table border='1'>");
            out.println("<tr><th>Book ID</th><th>Title</th><th>Genre</th><th>Publication Year</th><th>Author ID</th><th>Borrow</th></tr>");

            while (rs.next()) {
                int bookID = rs.getInt("book_id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int publicationYear = rs.getInt("publication_year");
                int authorID = rs.getInt("author_id");

                out.println("<tr>");
                out.println("<td>" + bookID + "</td>");
                out.println("<td>" + title + "</td>");
                out.println("<td>" + genre + "</td>");
                out.println("<td>" + publicationYear + "</td>");
                out.println("<td>" + authorID + "</td>");
                out.println("<td>");
                out.println("<form action='BorrowBook' method='post'>");
                out.println("<input type='hidden' name='book_id' value='" + bookID + "'>");
                out.println("<input type='submit' value='Borrow'>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error retrieving books: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</body></html>");
    }
}
