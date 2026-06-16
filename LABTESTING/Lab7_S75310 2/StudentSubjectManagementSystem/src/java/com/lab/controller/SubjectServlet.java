package com.lab.controller;

import com.lab.bean.StudentBean;
import com.lab.bean.SubjectBean;
import com.lab.dao.SubjectDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SubjectDAO subjectDAO;

    @Override
    public void init() {
        subjectDAO = new SubjectDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showRegisterForm(request, response);
                    break;
                case "insert":
                    insertSubject(request, response, session);
                    break;
                case "delete":
                    deleteSubject(request, response, session);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateSubject(request, response, session);
                    break;
                case "list":
                default:
                    listSubjects(request, response, session);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listSubjects(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException, ServletException {
        StudentBean user = (StudentBean) session.getAttribute("loggedUser");
        String matricNo = user.getMatricNo();

        List<SubjectBean> listSubjects = subjectDAO.selectAllSubjectsByMatric(matricNo);
        request.setAttribute("listSubjects", listSubjects);

        // JSPs are in the web root, NOT in a /subject/ subfolder
        request.getRequestDispatcher("/viewSubject.jsp").forward(request, response);
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/registerSubject.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/SubjectServlet?action=list");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            SubjectBean existingSubject = subjectDAO.selectSubjectById(id);
            if (existingSubject != null) {
                request.setAttribute("subject", existingSubject);
                request.getRequestDispatcher("/updateSubject.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/SubjectServlet?action=list");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/SubjectServlet?action=list");
        }
    }

    private void insertSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {
        StudentBean user = (StudentBean) session.getAttribute("loggedUser");
        String matricNo = user.getMatricNo();

        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");

        SubjectBean newSubject = new SubjectBean();
        newSubject.setMatricNo(matricNo);
        newSubject.setSubjectCode(subjectCode);
        newSubject.setSubjectName(subjectName);

        subjectDAO.insertSubject(newSubject);
        response.sendRedirect(request.getContextPath() + "/SubjectServlet?action=list");
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {
        String idParam = request.getParameter("id");
        StudentBean user = (StudentBean) session.getAttribute("loggedUser");
        String matricNo = user.getMatricNo();

        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                SubjectBean subject = new SubjectBean();
                subject.setId(Integer.parseInt(idParam));
                subject.setMatricNo(matricNo);
                subject.setSubjectCode(subjectCode);
                subject.setSubjectName(subjectName);

                subjectDAO.updateSubject(subject);
            } catch (NumberFormatException e) {
                // Ignore malformed id
            }
        }
        response.sendRedirect(request.getContextPath() + "/SubjectServlet?action=list");
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {
        String idParam = request.getParameter("id");
        StudentBean user = (StudentBean) session.getAttribute("loggedUser");
        String matricNo = user.getMatricNo();

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                subjectDAO.deleteSubject(id, matricNo);
            } catch (NumberFormatException e) {
                // Ignore malformed id
            }
        }
        response.sendRedirect(request.getContextPath() + "/SubjectServlet?action=list");
    }
}