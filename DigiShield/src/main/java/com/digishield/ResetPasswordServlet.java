package com.digishield;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", "rishi1616");

            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, newPassword);
            pst.setString(2, username);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("index.html?msg=password_reset_success");
            } else {
                response.sendRedirect("forgot-password.html?error=invalid_user");
            }

            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("forgot-password.html?error=server_error");
        }
    }
}