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

public class SearchBook extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchBook() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookName = request.getParameter("bookname");
        String authorName = request.getParameter("authorname");
        String bookId = request.getParameter("bookid");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        out.println("<html><body>");
        out.println("<h2>Search Results:</h2>");
        out.println("<table border='1'><tr><th>Book ID</th><th>Title</th><th>Genre</th><th>Publication Year</th><th>Author ID</th></tr>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");

            // Building the dynamic SQL query
            StringBuilder sql = new StringBuilder("SELECT book_id, title, genre, publication_year, author_id FROM books WHERE 1=1");
            
            if (bookName != null && !bookName.isEmpty()) {
                sql.append(" AND title LIKE ?");
            }
            if (authorName != null && !authorName.isEmpty()) {
                sql.append(" AND author_id IN (SELECT author_id FROM authors WHERE author_name LIKE ?)");
            }
            if (bookId != null && !bookId.isEmpty()) {
                sql.append(" AND book_id = ?");
            }

            pstmt = conn.prepareStatement(sql.toString());
            
            int paramIndex = 1;
            if (bookName != null && !bookName.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + bookName + "%");
            }
            if (authorName != null && !authorName.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + authorName + "%");
            }
            if (bookId != null && !bookId.isEmpty()) {
                pstmt.setInt(paramIndex, Integer.parseInt(bookId));
            }

            rs = pstmt.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                int bookID = rs.getInt("book_id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int publicationYear = rs.getInt("publication_year");
                int authorID = rs.getInt("author_id");

                // Output each row
                out.println("<tr><td>" + bookID + "</td><td>" + title + "</td><td>" + genre + "</td><td>" + publicationYear + "</td><td>" + authorID + "</td></tr>");
            }

            // If no results found
            if (!hasResults) {
                out.println("<tr><td colspan='5'>No results found</td></tr>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<tr><td colspan='5'>Error: " + e.getMessage() + "</td></tr>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
