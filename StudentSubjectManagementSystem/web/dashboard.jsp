<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.lab.bean.StudentBean"%>
<%
    // 🛡️ SESSION SECURITY GUARD
    // This looks for the exact case-sensitive session token "loggedUser" set by your UserServlet.
    StudentBean loggedUser = (StudentBean) session.getAttribute("loggedUser");
    
    // If the session is empty or invalid, safely redirect back to login instead of crashing
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/login.html");
        return; 
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Dashboard</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 40px;
                background-color: #f4f7f6;
            }
            .dashboard-container {
                background-color: #ffffff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                max-width: 600px;
                margin: 0 auto;
            }
            .profile-section {
                display: flex;
                align-items: center;
                gap: 20px;
                margin-bottom: 30px;
                padding-bottom: 20px;
                border-bottom: 1px solid #eee;
            }
            .profile-pic {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #007bff;
                background-color: #eee;
            }
            .welcome-text h2 {
                margin: 0 0 5px 0;
                color: #333;
            }
            .welcome-text p {
                margin: 0;
                color: #666;
                font-size: 14px;
            }
            .menu-actions {
                display: flex;
                flex-direction: column;
                gap: 12px;
            }
            .btn {
                display: block;
                text-align: center;
                padding: 12px;
                text-decoration: none;
                font-weight: bold;
                border-radius: 5px;
                transition: background 0.2s;
            }
            .btn-primary {
                background-color: #007bff;
                color: white;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .btn-danger {
                background-color: #dc3545;
                color: white;
            }
            .btn-danger:hover {
                background-color: #bd2130;
            }
            .btn-secondary {
                background-color: #6c757d;
                color: white;
            }
            .btn-secondary:hover {
                background-color: #545b62;
            }
        </style>
    </head>
    <body>

        <div class="dashboard-container">
            
            <div class="profile-section">
                <% 
                    // Check if the user has a profile picture converted to Base64 in their bean
                    String imageStr = loggedUser.getBase64Image();
                    if (imageStr != null && !imageStr.isEmpty()) {
                %>
                    <img src="data:image/jpeg;base64,<%= imageStr %>" alt="Profile Picture" class="profile-pic">
                <% } else { %>
                    <div class="profile-pic" style="display: flex; align-items: center; justify-content: center; font-size: 12px; text-align: center; color: #888;">No Image</div>
                <% } %>
                
                <div class="welcome-text">
                    <h2>Welcome, <%= loggedUser.getFullName() %>!</h2>
                    <p><strong>Matric No:</strong> <%= loggedUser.getMatricNo() %></p>
                </div>
            </div>

            <div class="menu-actions">
                <a href="viewSubject.jsp" class="btn btn-primary">📖 Manage Registered Subjects</a>
                
                <a href="${pageContext.request.contextPath}/UserServlet?action=logout" class="btn btn-secondary">🚪 Logout Session</a>
                
                <a href="${pageContext.request.contextPath}/UserServlet?action=delete" 
                   class="btn btn-danger" 
                   onclick="return confirm('⚠️ Are you absolutely sure you want to permanently delete your student account? This action cannot be undone.');">
                   ❌ Terminate Account
                </a>
            </div>

        </div>

    </body>
</html>