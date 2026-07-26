package com.digishield;

import java.io.IOException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/SubmitComplaintServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)

public class SubmitComplaintServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Frontend form se data uthana
        String uName = request.getParameter("username");
        String cText = request.getParameter("complaint_text");
     // Upload hui photo ko form se padhna aur computer mein save karna
        Part filePart = request.getPart("evidence");
        String fileName = (filePart != null) ? filePart.getSubmittedFileName() : "";
        String filePath = "";

        if (fileName != null && !fileName.trim().isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + "uploads";
            java.io.File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            
            filePath = "uploads/" + fileName;
            filePart.write(uploadPath + java.io.File.separator + fileName);
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", "rishi1616");
            
            // Database mein data save karne ki SQL query
            String query = "INSERT INTO complaints (username, complaint_text, evidence) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, uName);
            pst.setString(2, cText);
            pst.setString(3, filePath);
            
            int row = pst.executeUpdate();
            
            if(row > 0) {
                // Generate hui ID nikalne ke liye
                java.sql.ResultSet rs = pst.getGeneratedKeys();
                int complaintId = 0;
                if (rs.next()) {
                    complaintId = rs.getInt(1);
                }
                
                // User ko alert popup dikha kar portal par bhejega
                out.println("<script type='text/javascript'>");
                out.println("alert('Complaint Registered Successfully! Your Tracking ID is: DS-" + complaintId + "');");
                out.println("window.location.href='portal.html';");
                out.println("</script>");
           
            } else {
                out.println("<h3 style='color:red; text-align:center;'>Failed to register complaint. Please try again.</h3>");
            }
            
            conn.close();
        } catch(Exception e) {
            out.println("<h3 style='color:red; text-align:center;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}