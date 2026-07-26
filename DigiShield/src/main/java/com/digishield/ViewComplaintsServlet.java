package com.digishield;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewComplaintsServlet")
public class ViewComplaintsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Complaints</title>");
        // AJAX Script jo bina page reload kiye status badal degi
        out.println("<script>");
        out.println("function updateStatus(btn, id) {");
        out.println("    var xhr = new XMLHttpRequest();");
        out.println("    xhr.open('POST', 'UpdateStatusServlet', true);");
        out.println("    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');");
        out.println("    xhr.onreadystatechange = function() {");
        out.println("        if(xhr.readyState == 4 && xhr.status == 200) {");
        out.println("            var statusSpan = document.getElementById('status-' + id);");
        out.println("            statusSpan.innerText = 'Action Taken';");
        out.println("            statusSpan.style.backgroundColor = '#10b981';");
        out.println("        }");
        out.println("    };");
        out.println("    xhr.send('complaintId=' + id);");
        out.println("}");
        out.println("</script>");
        
        // CSS Styling
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #0b1329; color: white; padding: 20px; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #1e293b; }");
        out.println("th { background-color: #1e293b; color: #38bdf8; }");
        out.println(".badge { padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 13px; color: white; }");
        out.println(".btn-action { background-color: #10b981; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-weight: bold; }");
        out.println("</style></head><body>");
        
        out.println("<h2>DigiShield - Live User Complaints Panel</h2>");
        out.println("<table><thead><tr><th>User Name</th><th>Complaint Details</th><th>Time</th><th>Evidence</th><th>Status</th><th>Action</th></tr></thead><tbody>");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", "rishi1616");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM complaints");
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String complaintText = rs.getString("complaint_text");
                String time = rs.getTimestamp("submission_time").toString();
                String status = rs.getString("status");
                String evidencePath = rs.getString("evidence");
                if(status == null) status = "Pending";
                
                String badgeColor = status.equals("Action Taken") ? "#10b981" : "#f59e0b";
                
                out.println("<tr>");
                out.println("<td>" + username + "</td>");
                
                out.println("<td>" + complaintText + "</td>");
                out.println("<td>" + time + "</td>");
                if (evidencePath != null && !evidencePath.trim().isEmpty()) {
                    out.println("<td><a href='" + evidencePath + "' target='_blank' style='color: #00d2ff; font-weight: bold;'>View Proof 📷</a></td>");
                } else {
                    out.println("<td><span style='color: #888;'>No File</span></td>");
                }
                // Status column ID ke sath taaki JavaScript ise live change kar sake
                out.println("<td><span id='status-" + id + "' class='badge' style='background-color: " + badgeColor + ";'>" + status + "</span></td>");
                // Button click hone par direct JavaScript trigger hogi
                out.println("<td><button class='btn-action' onclick='updateStatus(this, " + id + ")'>Take Action</button></td>");
                out.println("</tr>");
            }
            conn.close();
        } catch(Exception e) {
            out.println("<tr><td colspan='5' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
        }
        
        out.println("</tbody></table></body></html>");
    }
}