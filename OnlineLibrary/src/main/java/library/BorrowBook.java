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
import java.sql.Statement;

public class BorrowBook extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BorrowBook() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        String bookIDString = request.getParameter("book_id");
        int bookID;

        try {
            bookID = Integer.parseInt(bookIDString);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID format.");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        out.println("<html><body>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");

            // Get the next value from the sequence for BORROWID
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT borrow_id_seq.NEXTVAL AS new_id FROM dual");
            int borrowID = 0;
            if (rs.next()) {
                borrowID = rs.getInt("new_id");
            }

            // Insert the borrow record with RETURN_DATE as 14 days after BORROW_DATE
            String sql = "INSERT INTO borrow_history (BORROWID, USERID, BOOK_ID, BORROW_DATE, RETURN_DATE) " +
                         "VALUES (?, ?, ?, SYSDATE, SYSDATE + 14)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, borrowID);
            pstmt.setString(2, userID);
            pstmt.setInt(3, bookID);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<p>Book borrowed successfully! Return date is in 14 days.</p>");
            } else {
                out.println("<p>Error borrowing the book.</p>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("<a href='DisplayBooks'>Back to Book List</a>");
        out.println("</body></html>");
    }
}
