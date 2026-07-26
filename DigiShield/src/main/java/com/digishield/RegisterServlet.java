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

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // HTML Form inputs matching:
        // Full Name, Username, Password
        String fullName = request.getParameter("fullname"); 
        String uName = request.getParameter("username"); 
        String pass = request.getParameter("password");

        // Fallback agar 'fullname' HTML field name alag ho
        if (fullName == null) fullName = uName;

        // DHYAN DEIN: Yahan "root" ki jagah apna actual MySQL password likhein
        String dbPass = "rishi1616"; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", dbPass);

            // Users table mein insert query
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, uName);
            pst.setString(2, pass);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                // Success hone par Login page par bhej do
                response.sendRedirect("index.html?registration=success");
            } else {
                response.sendRedirect("signup.html?error=failed");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("signup.html?error=server");
        }
    }
}