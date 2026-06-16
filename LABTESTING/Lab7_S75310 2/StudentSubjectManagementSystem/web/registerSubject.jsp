<%@page import="com.lab.bean.StudentBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    StudentBean user = (StudentBean) session.getAttribute("loggedUser");

    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Subject</title>
    </head>
    <body>
        <h2>Add New Subject</h2>

        <form action="SubjectServlet" method="POST">
            <input type="hidden" name="action" value="insert">

            Subject Code:
            <input type="text" name="subjectCode" required>
            <br><br>

            Subject Name:
            <input type="text" name="subjectName" required>
            <br><br>

            <input type="submit" value="Add Subject">
        </form>

        <br>
        <a href="SubjectServlet?action=list">Back to Subject List</a>
    </body>
</html>