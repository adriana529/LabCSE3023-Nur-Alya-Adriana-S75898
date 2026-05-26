<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lab.bean.SubjectBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Registered Subjects</title>
</head>
<body>
    <h2>Subject Management Subsystem</h2>
    <%
        String sessionMatric = (String) session.getAttribute("matricNo");
        if(sessionMatric == null) { sessionMatric = (String) session.getAttribute("matric_no"); }
    %>
    <p>Logged in User Matric No: <strong><%= sessionMatric %></strong></p>
    
    <p><a href="registerSubject.jsp">[+] Register New Subject</a></p>
    
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr style="background-color: #e6f2ff;">
                <th>Subject Code</th>
                <th>Subject Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<SubjectBean> list = (List<SubjectBean>) request.getAttribute("subjectList");
                if (list != null && !list.isEmpty()) {
                    for (SubjectBean subject : list) {
            %>
            <tr>
                <td><%= subject.getSubjectCode() %></td>
                <td><%= subject.getSubjectName() %></td>
                <td>
                    <a href="SubjectServlet?action=edit&subjectCode=<%= subject.getSubjectCode() %>">Kemaskini</a> | 
                    <a href="SubjectServlet?action=delete&subjectCode=<%= subject.getSubjectCode() %>" 
                       onclick="return confirm('Are you sure you want to delete this course registration?');">Padam</a>
                </td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="3" style="text-align: center; color: gray;">No academic subjects registered yet.</td>
            </tr>
            <% 
                }
            %>
        </tbody>
    </table>
    <br/>
    <p><a href="dashboard.jsp">Return to Dashboard</a></p>
</body>
</html>