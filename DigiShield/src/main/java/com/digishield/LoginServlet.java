package com.digishield;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String uName = request.getParameter("username");
        String uPass = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // NOTE: Yahan bhi apna actual MySQL password check kar lein
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", "rishi1616");

            String query = "SELECT username FROM users WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, uName);
            pst.setString(2, uPass);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Database se specific logged-in admin ka naam nikalna
                String dbAdminName = rs.getString("username");
                
                // Session mein store karna
                HttpSession session = request.getSession();
                session.setAttribute("adminName", dbAdminName);
                
                // JSP Dashboard par redirect karna
                response.sendRedirect("welcome.jsp");
            } else {
                response.sendRedirect("index.html?error=invalid");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?error=server");
        }
    }
}