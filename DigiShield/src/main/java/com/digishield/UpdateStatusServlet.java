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

@WebServlet("/UpdateStatusServlet")
public class UpdateStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String complaintId = request.getParameter("complaintId");
        
        if (complaintId != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", "rishi@8561");
                
                String query = "UPDATE complaints SET status = 'Action Taken' WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(complaintId));
                
                pstmt.executeUpdate();
                
                pstmt.close();
                conn.close();
                
                // JavaScript ko success message bhejna
                response.getWriter().write("SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        }
    }
}