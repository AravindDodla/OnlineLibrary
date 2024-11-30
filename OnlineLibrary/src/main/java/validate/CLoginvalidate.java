package validate;

import jakarta.servlet.RequestDispatcher;
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
 * Servlet implementation class CLoginvalidate
 */
public class CLoginvalidate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CLoginvalidate() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load and register the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver is loaded");

            // Retrieve username and password from the request
            String username1 = request.getParameter("uname");
            String password = request.getParameter("password");

            // Establish a database connection
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");
            System.out.println("Connection is created");

            // Prepare SQL statement to retrieve user details
            pstmt = con.prepareStatement("SELECT userid, username, password FROM users WHERE username = ?");
            pstmt.setString(1, username1);
            rs = pstmt.executeQuery();

            // Check if user exists and credentials match
            if (rs.next()) {
                String userID = rs.getString("userid");  // Fetch the user_id as String from the result set
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                if (username1.equals(dbUsername) && password.equals(dbPassword)) {
                    // Valid user, create a session and store user details
                    HttpSession session = request.getSession();
                    session.setAttribute("uname", dbUsername);
                    session.setAttribute("userID", userID); // Store userID (String) in the session

                    // Forward to the success page
                    RequestDispatcher dispatcher = request.getRequestDispatcher("CLoginSuccess.jsp");
                    dispatcher.forward(request, response);
                } else {
                    pw.println("Please enter correct details");
                }
            } else {
                pw.println("User not found. Please enter correct details.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("An error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
