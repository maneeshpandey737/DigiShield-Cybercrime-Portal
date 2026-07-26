package com.digishield;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class TrackComplaintServlet
 */
@WebServlet("/TrackComplaint")
public class TrackComplaintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrackComplaintServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        
        String trackingIdInput = request.getParameter("trackingId");
        int id = 0;
        try {
            if(trackingIdInput != null && trackingIdInput.contains("-")) {
                id = Integer.parseInt(trackingIdInput.split("-")[1].trim());
            } else {
                id = Integer.parseInt(trackingIdInput.trim());
            }
        } catch(Exception e) {
            out.println("<script>alert('Invalid Tracking ID Format!'); window.location.href='track.html';</script>");
            return;
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/digishield", "root", "rishi@8561");
            
            String query = "SELECT status FROM complaints WHERE id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            
            java.sql.ResultSet rs = pst.executeQuery();
            
            out.println("<html><head><title>Complaint Status</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #0f172a, #1e1b4b); color: white; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".status-card { background: rgba(30, 41, 59, 0.7); padding: 30px; border-radius: 12px; text-align: center; border: 1px solid rgba(255, 255, 255, 0.1); width: 350px; box-shadow: 0 8px 32px 0 rgba(0,0,0,0.37); }");
            out.println("h2 { color: #38bdf8; }");
            out.println(".status-badge { background: #eab308; color: #0f172a; padding: 8px 15px; font-weight: bold; border-radius: 20px; display: inline-block; margin: 15px 0; }");
            out.println("a { display: block; margin-top: 20px; color: #94a3b8; text-decoration: none; }");
            out.println("a:hover { color: white; }");
            out.println("</style></head><body>");
            out.println("<div class='status-card'>");
            
            if(rs.next()) {
                String currentStatus = rs.getString("status");
                out.println("<h2>Complaint Found!</h2>");
                out.println("<p>Tracking ID: <b>" + trackingIdInput + "</b></p>");
                out.println("<p>Current Status:</p>");
                out.println("<div class='status-badge'>" + currentStatus.toUpperCase() + "</div>");
            } else {
                out.println("<h2 style='color:#ef4444;'>No Record Found</h2>");
                out.println("<p>No complaint found with Tracking ID: <b>" + trackingIdInput + "</b></p>");
            }
            
            out.println("<a href='track.html'>← Track Another Complaint</a>");
            out.println("</div></body></html>");
            
            conn.close();
        } catch(Exception e) {
            out.println("<h3 style='color:red; text-align:center;'>Error: " + e.getMessage() + "</h3>");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
