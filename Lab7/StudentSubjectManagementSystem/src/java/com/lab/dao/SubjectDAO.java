package com.lab.dao;

import com.lab.bean.SubjectBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/CSE3023", "root", "");
    }

    public boolean addSubject(SubjectBean subject) {
        String query = "INSERT INTO subjects (subjectCode, subjectName, matricNo) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, subject.getSubjectCode());
            ps.setString(2, subject.getSubjectName());
            ps.setString(3, subject.getMatricNo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SubjectBean> getSubjectsByMatric(String matricNo) {
        List<SubjectBean> list = new ArrayList<>();
        String query = "SELECT * FROM subjects WHERE matricNo = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, matricNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubjectBean sb = new SubjectBean();
                    sb.setSubjectCode(rs.getString("subjectCode"));
                    sb.setSubjectName(rs.getString("subjectName"));
                    sb.setMatricNo(rs.getString("matricNo"));
                    list.add(sb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public SubjectBean getSubjectByCodeAndMatric(String subjectCode, String matricNo) {
        String query = "SELECT * FROM subjects WHERE subjectCode = ? AND matricNo = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, subjectCode);
            ps.setString(2, matricNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SubjectBean sb = new SubjectBean();
                    sb.setSubjectCode(rs.getString("subjectCode"));
                    sb.setSubjectName(rs.getString("subjectName"));
                    sb.setMatricNo(rs.getString("matricNo"));
                    return sb;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSubject(SubjectBean subject) {
        String query = "UPDATE subjects SET subjectName = ? WHERE subjectCode = ? AND matricNo = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getSubjectCode());
            ps.setString(3, subject.getMatricNo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSubject(String subjectCode, String matricNo) {
        String query = "DELETE FROM subjects WHERE subjectCode = ? AND matricNo = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, subjectCode);
            ps.setString(2, matricNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}