package library;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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

@WebServlet("/staffDetails")
public class Staffdetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Staffdetails() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver is loaded");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");
            System.out.println("Connection is established");

            // Corrected query to select users where designation is 'Staff'
            String query = "SELECT userid, username, email, designation FROM users WHERE designation = 'Staff'";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            pw.println("<html><body>");
            pw.println("<h2>Staff Details</h2>");
            pw.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Email</th><th>Designation</th><th>Action</th></tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getString("userid") + "</td>");
                pw.println("<td>" + rs.getString("username") + "</td>");
                pw.println("<td>" + rs.getString("email") + "</td>");
                pw.println("<td>" + rs.getString("designation") + "</td>");
                pw.println("<td><form method='post' action='staffDetails'><input type='hidden' name='userid' value='" + rs.getString("userid") + "'/><input type='submit' value='Delete'/></form></td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</body></html>");

        } catch (ClassNotFoundException | SQLException e) {
            pw.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String userid = request.getParameter("userid");

        if (userid != null && !userid.trim().isEmpty()) {
            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                System.out.println("Driver is loaded");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");
                System.out.println("Connection is established");

                String deleteQuery = "DELETE FROM users WHERE userid = ?";
                pstmt = con.prepareStatement(deleteQuery);
                pstmt.setString(1, userid);
                int rowsDeleted = pstmt.executeUpdate();

                if (rowsDeleted > 0) {
                    pw.println("<p>User with ID " + userid + " has been deleted successfully.</p>");
                } else {
                    pw.println("<p>Error: Unable to delete user with ID " + userid + ".</p>");
                }

            } catch (ClassNotFoundException | SQLException e) {
                pw.println("<p>Error: " + e.getMessage() + "</p>");
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            pw.println("<p>Error: User ID is not provided.</p>");
        }

        // Refresh the list of staff details after deletion
        doGet(request, response);
    }
}
