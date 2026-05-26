<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lab.bean.SubjectBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kemaskini Subject</title>
</head>
<body>
    <h2>Update Subject Information</h2>
    <%
        SubjectBean subject = (SubjectBean) request.getAttribute("subject");
        if (subject != null) {
    %>
    <form action="SubjectServlet?action=update" method="POST">
        <table cellpadding="5">
            <tr>
                <td>Subject Code:</td>
                <td>
                    <input type="text" value="<%= subject.getSubjectCode() %>" disabled />
                    <input type="hidden" name="subjectCode" value="<%= subject.getSubjectCode() %>" />
                </td>
            </tr>
            <tr>
                <td>Subject Name:</td>
                <td><input type="text" name="subjectName" value="<%= subject.getSubjectName() %>" required /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Kemaskini" />
                    <input type="button" value="Cancel" onclick="window.location.href='SubjectServlet?action=view';" />
                </td>
            </tr>
        </table>
    </form>
    <% 
        } else { 
    %>
        <p style="color:red; font-weight:bold;">Error: Targeted subject data profile record was not populated correctly.</p>
        <a href="SubjectServlet?action=view">Back to List</a>
    <% 
        } 
    %>
</body>
</html>