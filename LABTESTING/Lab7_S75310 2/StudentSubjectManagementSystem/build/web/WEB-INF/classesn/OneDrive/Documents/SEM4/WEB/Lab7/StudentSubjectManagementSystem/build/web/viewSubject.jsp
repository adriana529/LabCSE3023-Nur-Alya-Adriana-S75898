<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.lab.bean.StudentBean"%>
<%@page import="com.lab.bean.SubjectBean"%>
<%@page import="java.util.List"%>
<%
    StudentBean user = (StudentBean) session.getAttribute("loggedUser");

    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }

    // listSubjects is set by SubjectServlet, not fetched directly here
    List<SubjectBean> subjectList = (List<SubjectBean>) request.getAttribute("listSubjects");
    if (subjectList == null) {
        subjectList = new java.util.ArrayList<>();
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registered Subjects</title>
        <style>
            body { font-family: Arial, sans-serif; padding: 30px; }
            table { border-collapse: collapse; width: 80%; }
            th, td { border: 1px solid #999; padding: 10px; text-align: left; }
            th { background-color: #eee; }
            a { margin-right: 10px; }
        </style>
    </head>
    <body>
        <h2>Registered Subjects</h2>

        <p>Student: <strong><%= user.getFullname() %></strong></p>
        <p>Matric No: <strong><%= user.getMatricNo() %></strong></p>

        <a href="SubjectServlet?action=new">Add New Subject</a>
        <br><br>

        <table>
            <tr>
                <th>No</th>
                <th>Subject Code</th>
                <th>Subject Name</th>
                <th>Action</th>
            </tr>

            <%
                int no = 1;
                for (SubjectBean subject : subjectList) {
            %>
            <tr>
                <td><%= no++ %></td>
                <td><%= subject.getSubjectCode() %></td>
                <td><%= subject.getSubjectName() %></td>
                <td>
                    <a href="SubjectServlet?action=edit&id=<%= subject.getId() %>">Edit</a>
                    <a href="SubjectServlet?action=delete&id=<%= subject.getId() %>"
                       onclick="return confirm('Are you sure you want to delete this subject?');">
                        Delete
                    </a>
                </td>
            </tr>
            <%
                }
            %>
        </table>

        <br>
        <a href="dashboard.jsp">Back to Dashboard</a>
    </body>
</html>