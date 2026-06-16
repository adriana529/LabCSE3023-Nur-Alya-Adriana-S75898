<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.lab.bean.StudentBean"%>
<%
    response.setContentType("text/html;charset=UTF-8");

    StudentBean user = (StudentBean) session.getAttribute("loggedUser");

    if (user == null) {
        // FIX: was incorrectly "login.jsp" — the actual file is login.html
        response.sendRedirect(request.getContextPath() + "/login.html");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <%-- FIX: stylesheet was never linked in original --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

    <h2>Welcome, <%= user.getFullname() %>!</h2>

    <p><strong>Matric Number:</strong> <%= user.getMatricNo() %></p>

    <%-- Display Profile Image if available --%>
    <% if (user.getBase64Image() != null && !user.getBase64Image().isEmpty()) { %>
        <div>
            <img src="data:image/jpeg;base64,<%= user.getBase64Image() %>"
                 alt="Profile Image" width="150" style="border-radius: 50%;"/>
        </div>
    <% } %>

    <br/><br/>

    <a href="${pageContext.request.contextPath}/SubjectServlet?action=list"
       style="padding: 10px; background-color: #3498db; color: white; text-decoration: none; border-radius: 4px;">
        My Subjects
    </a>

    <a href="${pageContext.request.contextPath}/UserServlet?action=logout"
       style="padding: 10px; background-color: #f44336; color: white; text-decoration: none; border-radius: 4px; margin-left: 10px;">
        Logout
    </a>

    <a href="${pageContext.request.contextPath}/UserServlet?action=delete"
       onclick="return confirm('Are you sure you want to permanently delete your account?');"
       style="padding: 10px; background-color: #555555; color: white; text-decoration: none; border-radius: 4px; margin-left: 10px;">
        Delete Account
    </a>

</body>
</html>
