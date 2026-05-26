<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Daftar Subject</title>
</head>
<body>
    <h2>Register Academic Subject</h2>
    <form action="SubjectServlet?action=add" method="POST">
        <table cellpadding="5">
            <tr>
                <td>Subject Code:</td>
                <td><input type="text" name="subjectCode" placeholder="e.g., CSE3023" required /></td>
            </tr>
            <tr>
                <td>Subject Name:</td>
                <td><input type="text" name="subjectName" required /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Daftar" />
                    <input type="button" value="Cancel" onclick="window.location.href='SubjectServlet?action=view';" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>