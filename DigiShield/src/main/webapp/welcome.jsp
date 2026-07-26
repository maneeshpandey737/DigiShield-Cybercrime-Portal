<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Database / LoginServlet se session mein aaya hua admin name fetch karna
    String currentAdmin = (String) session.getAttribute("adminName");
    
    // Agar session khali ho toh default name
    if (currentAdmin == null || currentAdmin.trim().isEmpty()) {
        currentAdmin = "Rishi Pandey";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DigiShield - Admin Command Center</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #020c1b;
            color: #ffffff;
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Navigation */
        .sidebar {
            width: 260px;
            background: #0a192f;
            border-right: 1px solid rgba(0, 210, 255, 0.2);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 20px 0;
        }

        .brand-section {
            padding: 0 20px 20px 20px;
            border-bottom: 1px solid rgba(0, 210, 255, 0.1);
        }

        .brand-title {
            color: #00d2ff;
            font-size: 20px;
            font-weight: bold;
            letter-spacing: 1.5px;
        }

        .brand-subtitle {
            color: #8892b0;
            font-size: 11px;
            margin-top: 4px;
            text-transform: uppercase;
        }

        /* Admin Officer Card inside Sidebar */
        .officer-card {
            margin: 20px;
            padding: 15px;
            background: rgba(0, 210, 255, 0.05);
            border: 1px solid rgba(0, 210, 255, 0.2);
            border-radius: 12px;
            text-align: center;
        }

        .officer-avatar {
            width: 65px;
            height: 65px;
            border-radius: 50%;
            border: 2px solid #00d2ff;
            object-fit: cover;
            margin-bottom: 10px;
            cursor: pointer;
            box-shadow: 0 0 10px rgba(0, 210, 255, 0.3);
        }

        .officer-fullname {
            font-size: 15px;
            font-weight: bold;
            color: #ffffff;
        }

        .officer-role {
            font-size: 11px;
            color: #00d2ff;
            margin-top: 2px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .nav-menu {
            list-style: none;
            padding: 10px 0;
        }

        .nav-item a {
            display: flex;
            align-items: center;
            padding: 12px 25px;
            color: #8892b0;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: 0.3s;
        }

        .nav-item.active a, .nav-item a:hover {
            color: #00d2ff;
            background: rgba(0, 210, 255, 0.1);
            border-left: 4px solid #00d2ff;
        }

        /* Main Content Layout */
        .main-wrapper {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        /* Top Header Navbar */
        .header-nav {
            background: #0f2244;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #00d2ff;
            box-shadow: 0 4px 20px rgba(0, 210, 255, 0.15);
        }

        .header-title {
            font-size: 18px;
            color: #00d2ff;
            font-weight: 600;
        }

        .header-controls {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .status-badge {
            background: rgba(0, 210, 255, 0.1);
            border: 1px solid #00d2ff;
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 12px;
            color: #00d2ff;
            font-weight: bold;
        }

        .logout-btn {
            background: linear-gradient(135deg, #ff4d4d, #cc0000);
            color: white;
            padding: 8px 18px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
            box-shadow: 0 2px 10px rgba(255, 77, 77, 0.3);
            transition: 0.3s;
        }

        .logout-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(255, 77, 77, 0.5);
        }

        /* Dashboard Container */
        .container {
            padding: 35px;
            max-width: 1200px;
        }

        .welcome-header {
            margin-bottom: 30px;
        }

        .welcome-title {
            font-size: 30px;
            font-weight: 700;
        }

        .welcome-title span {
            color: #00d2ff;
        }

        .welcome-subtitle {
            color: #8892b0;
            margin-top: 5px;
            font-size: 14px;
        }

        /* Analytics Grid Cards */
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 35px;
        }

        .card {
            background: #0a192f;
            border: 1px solid rgba(0, 210, 255, 0.2);
            border-radius: 12px;
            padding: 25px;
            position: relative;
            box-shadow: 0 10px 30px -15px rgba(2, 12, 27, 0.7);
            transition: 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
            border-color: #00d2ff;
            box-shadow: 0 15px 35px -10px rgba(0, 210, 255, 0.25);
        }

        .card h3 {
            color: #8892b0;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .card .value {
            font-size: 36px;
            font-weight: bold;
            color: #00d2ff;
            margin: 12px 0;
        }

        .card p {
            color: #64ffda;
            font-size: 13px;
        }

        /* Main Action Buttons */
        .action-group {
            display: flex;
            gap: 15px;
        }

        .btn {
            padding: 12px 28px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            font-size: 14px;
            transition: 0.3s;
        }

        .btn-primary {
            background: #00d2ff;
            color: #020c1b;
            box-shadow: 0 4px 15px rgba(0, 210, 255, 0.3);
        }

        .btn-secondary {
            background: transparent;
            border: 1px solid #ff4d4d;
            color: #ff4d4d;
        }

        .btn:hover {
            transform: translateY(-2px);
            opacity: 0.9;
        }
    </style>
</head>
<body>

    <!-- Left Sidebar -->
    <div class="sidebar">
        <div>
            <div class="brand-section">
                <div class="brand-title">DIGISHIELD</div>
                <div class="brand-subtitle">Cyber Crime Portal</div>
            </div>

            <!-- Profile Info Box -->
            <div class="officer-card">
                <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%2300d2ff'><path d='M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z'/></svg>" 
                     id="profileImg" class="officer-avatar" alt="Officer Avatar" title="Click to change photo" onclick="document.getElementById('imgInput').click();">
                <input type="file" id="imgInput" accept="image/*" style="display: none;" onchange="loadImg(event)">
                
                <!-- Dynamic Full Name Display -->
                <div class="officer-fullname">Officer <%= currentAdmin %></div>
                <div class="officer-role">Cyber Safety Officer</div>
            </div>

            <!-- Side Nav Links -->
            <ul class="nav-menu">
                <li class="nav-item active"><a href="#">📊 Dashboard</a></li>
                <li class="nav-item"><a href="ViewComplaintsServlet">📑 Incident Reports</a></li>
                
            </ul>
        </div>

        
    </div>

    <!-- Main Wrapper -->
    <div class="main-wrapper">
        <!-- Top Navbar -->
        <div class="header-nav">
            <div class="header-title">ADMIN COMMAND CENTER</div>
            
            <div class="header-controls">
                <div class="status-badge">⚡ SECURE NODE: ACTIVE</div>
                <a href="index.html" class="logout-btn">Logout</a>
            </div>
        </div>

        <!-- Dashboard Content -->
        <div class="container">
            <div class="welcome-header">
                <!-- Welcome Title with Dynamic Name -->
                <h1 class="welcome-title">Welcome Back, <span>Officer <%= currentAdmin %></span></h1>
                <p class="welcome-subtitle">DigiShield cyber crime monitoring system is operational and active.</p>
            </div>

            <!-- Analytics Cards -->
            <div class="dashboard-grid">
                <div class="card">
                    <h3>System Shield Status</h3>
                    <div class="value">SECURE</div>
                    <p>✓ All firewalls operational</p>
                </div>

                <div class="card">
                    <h3>Threats Blocked</h3>
                    <div class="value">1,482</div>
                    <p>↑ 12% deflection rate</p>
                </div>

                <div class="card">
                    <h3>Network Load</h3>
                    <div class="value">34%</div>
                    <p>✓ Optimal bandwidth</p>
                </div>
            </div>

            <!-- Main Quick Buttons -->
            <div class="action-group">
                <a href="ViewComplaintsServlet" class="btn btn-primary">View Complaints Database</a>
                
            </div>
        </div>
    </div>

    <script>
        function loadImg(event) {
            var output = document.getElementById('profileImg');
            if (event.target.files && event.target.files[0]) {
                output.src = URL.createObjectURL(event.target.files[0]);
            }
        }
    </script>
</body>
</html>