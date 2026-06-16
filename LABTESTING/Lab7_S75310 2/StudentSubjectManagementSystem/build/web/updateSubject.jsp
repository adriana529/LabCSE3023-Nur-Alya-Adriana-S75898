<%@page import="com.lab.bean.StudentBean"%>
<%@page import="com.lab.bean.SubjectBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    StudentBean user = (StudentBean) session.getAttribute("loggedUser");

    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }

    SubjectBean subject = (SubjectBean) request.getAttribute("subject");

    if (subject == null) {
        response.sendRedirect("SubjectServlet?action=list");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Subject</title>
    </head>
    <body>
        <h2>Edit Subject</h2>

        <form action="SubjectServlet" method="POST">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= subject.getId() %>">

            Subject Code:
            <input type="text" name="subjectCode" value="<%= subject.getSubjectCode() %>" required>
            <br><br>

            Subject Name:
            <input type="text" name="subjectName" value="<%= subject.getSubjectName() %>" required>
            <br><br>

            <input type="submit" value="Update Subject">
        </form>

        <br>
        <a href="SubjectServlet?action=list">Back to Subject List</a>
    </body>
</html>