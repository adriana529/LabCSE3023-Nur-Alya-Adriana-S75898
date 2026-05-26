package com.lab.controller;

import com.lab.bean.SubjectBean;
import com.lab.dao.SubjectDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubjectServlet extends HttpServlet {
    private SubjectDAO subjectDAO;

    @Override
    public void init() throws ServletException {
        subjectDAO = new SubjectDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        // Fallback safety if the session key is named either 'matricNo' or 'matric_no'
        String matricNo = null;
        if (session != null) {
            matricNo = (String) session.getAttribute("matricNo");
            if (matricNo == null) {
                matricNo = (String) session.getAttribute("matric_no");
            }
        }
        
        if (matricNo == null) {
            response.sendRedirect("login.html");
            return;
        }
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {
            case "add":
                String code = request.getParameter("subjectCode");
                String name = request.getParameter("subjectName");
                
                SubjectBean newSubject = new SubjectBean();
                newSubject.setMatricNo(matricNo);
                newSubject.setSubjectCode(code);
                newSubject.setSubjectName(name);
                
                subjectDAO.addSubject(newSubject);
                response.sendRedirect("SubjectServlet?action=view");
                break;

            case "edit":
                String editCode = request.getParameter("subjectCode");
                SubjectBean existingSubject = subjectDAO.getSubjectByCodeAndMatric(editCode, matricNo);
                request.setAttribute("subject", existingSubject);
                // Dispatches directly to the root Web Pages file path matching your screenshot
                request.getRequestDispatcher("updateSubject.jsp").forward(request, response);
                break;

            case "update":
                String updateCode = request.getParameter("subjectCode");
                String updateName = request.getParameter("subjectName");
                
                SubjectBean updatedSubject = new SubjectBean();
                updatedSubject.setMatricNo(matricNo);
                updatedSubject.setSubjectCode(updateCode);
                updatedSubject.setSubjectName(updateName);
                
                subjectDAO.updateSubject(updatedSubject);
                response.sendRedirect("SubjectServlet?action=view");
                break;

            case "delete":
                String deleteCode = request.getParameter("subjectCode");
                subjectDAO.deleteSubject(deleteCode, matricNo);
                response.sendRedirect("SubjectServlet?action=view");
                break;

            case "view":
            default:
                List<SubjectBean> subjectList = subjectDAO.getSubjectsByMatric(matricNo);
                request.setAttribute("subjectList", subjectList);
                // Dispatches directly to viewSubject.jsp at the root Web Pages directory
                request.getRequestDispatcher("viewSubject.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}