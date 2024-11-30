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

/**
 * Servlet implementation class AdminValidate
 */
public class AdminValidate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminValidate() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String username1 = request.getParameter("uname");
            String password = request.getParameter("password");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Laddu#99");
            pstmt = con.prepareStatement("select username, password from users where username = ?");
            pstmt.setString(1, username1);
            rs = pstmt.executeQuery();

            boolean isAuthenticated = false;

            while (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                if (username1.equals(dbUsername) && password.equals(dbPassword)) {
                    isAuthenticated = true;
                    HttpSession session = request.getSession();
                    session.setAttribute("uname", dbUsername);
                    request.setAttribute("uname", dbUsername);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Check.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }

            if (!isAuthenticated) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdministratorLogin.jsp");
                dispatcher.forward(request, response);
                pw.println("Please enter correct details");
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}


