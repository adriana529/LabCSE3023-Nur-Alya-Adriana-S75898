package com.lab.controller;

import com.lab.bean.StudentBean;
import com.lab.dao.StudentDAO;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/*
 * FIX: @WebServlet and @MultipartConfig annotations are used here,
 * so the UserServlet entry in web.xml must NOT have a <multipart-config>
 * block or a <servlet-mapping> — annotations take full priority.
 * Having both causes a deployment conflict in Tomcat.
 */
@WebServlet("/UserServlet")
@MultipartConfig(maxFileSize = 16177215) // ~16 MB max for image upload
public class UserServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            // --- HANDLE REGISTRATION ---
            StudentBean newStudent = new StudentBean();
            newStudent.setMatricNo(request.getParameter("matricNo"));
            newStudent.setFullname(request.getParameter("fullname"));
            newStudent.setPassword(request.getParameter("password"));

            InputStream inputStream = null;
            Part filePart = request.getPart("profileImage");
            if (filePart != null && filePart.getSize() > 0) {
                inputStream = filePart.getInputStream();
            }

            boolean isSuccess = studentDAO.registerStudent(newStudent, inputStream);
            if (isSuccess) {
                // FIX: was redirecting to "login.jsp" which does not exist — changed to login.html
                response.getWriter().println(
                    "<h3>Registration Successful! <a href='" +
                    request.getContextPath() + "/login.html'>Login here</a></h3>"
                );
            } else {
                response.getWriter().println(
                    "<h3>Registration Failed! <a href='" +
                    request.getContextPath() + "/register.html'>Try Again</a></h3>"
                );
            }

        } else if ("login".equals(action)) {
            // --- HANDLE LOGIN ---
            String matric = request.getParameter("matricNo");
            String pass   = request.getParameter("password");

            StudentBean student = studentDAO.loginStudent(matric, pass);
            if (student != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", student);
                // Redirect to dashboard after successful login
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            } else {
                // FIX: was linking back to "login.jsp" — changed to login.html
                response.getWriter().println(
                    "<h3>Invalid Credentials! <a href='" +
                    request.getContextPath() + "/login.html'>Try Again</a></h3>"
                );
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if ("logout".equals(action)) {
            // --- HANDLE LOGOUT ---
            if (session != null) {
                session.invalidate();
            }
            // FIX: was redirecting to "login.jsp" — changed to login.html
            response.sendRedirect(request.getContextPath() + "/login.html");

        } else if ("delete".equals(action)) {
            // --- HANDLE DELETE ACCOUNT ---
            if (session != null && session.getAttribute("loggedUser") != null) {
                StudentBean user = (StudentBean) session.getAttribute("loggedUser");
                studentDAO.deleteStudent(user.getMatricNo());
                session.invalidate();
            }
            // FIX: was redirecting to "register.jsp" — changed to register.html
            response.sendRedirect(request.getContextPath() + "/register.html");
        }
    }
}