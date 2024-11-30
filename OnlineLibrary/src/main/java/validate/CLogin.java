package validate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CLogin() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver is loaded");

            String userid = request.getParameter("uid");
            pw.println(userid);
            String username = request.getParameter("uname");
            pw.println(username);
            String password = request.getParameter("pass");
            pw.println(password);
            String rpass = request.getParameter("rpass");
            pw.println(rpass);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
            pw.println(sdf);
            java.util.Date utilDate = sdf.parse(request.getParameter("DOB"));
            pw.println(utilDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pw.println(sqlDate);

            String email = request.getParameter("email");
            pw.println(email);
            String gender = request.getParameter("GEN");
            pw.println(gender);
            String phone = request.getParameter("Pnumber");
            pw.println(phone);
            String address = request.getParameter("Address");
            pw.println(address);
            String designation = request.getParameter("designation");
            pw.println(designation);

            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");
            System.out.println("Connection is created");

            pstmt = con.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setString(1, userid);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, rpass);
            pstmt.setDate(5, sqlDate);
            pstmt.setString(6, email);
            pstmt.setString(7, gender);
            pstmt.setString(8, phone);
            pstmt.setString(9, address);
            pstmt.setString(10, designation);

            pstmt.executeUpdate();
            System.out.println("Values are inserted");
            pw.println("Values are inserted");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("Database driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("SQL error: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            pw.println("Date parsing error: " + e.getMessage());
        } finally {
            // Close resources in finally block to ensure they are closed even if an exception occurs
            try {
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
