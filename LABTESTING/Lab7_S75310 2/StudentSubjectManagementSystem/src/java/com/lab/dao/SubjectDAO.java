package com.lab.dao;

import com.lab.bean.SubjectBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    private final String dbURL      = "jdbc:mysql://localhost:3306/ssms";
    private final String dbUser     = "root";
    private final String dbPassword = "";

    protected Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertSubject(SubjectBean subject) throws SQLException {
        String sql = "INSERT INTO registered_subjects (matric_no, subject_code, subject_name) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, subject.getMatricNo());
            ps.setString(2, subject.getSubjectCode());
            ps.setString(3, subject.getSubjectName());
            ps.executeUpdate();
        }
    }

    public List<SubjectBean> selectAllSubjectsByMatric(String matricNo) throws SQLException {
        List<SubjectBean> subjects = new ArrayList<>();
        String sql = "SELECT * FROM registered_subjects WHERE matric_no = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricNo);
            // FIX: ResultSet now properly closed via try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubjectBean subject = new SubjectBean();
                    subject.setId(rs.getInt("id"));
                    subject.setMatricNo(rs.getString("matric_no"));
                    subject.setSubjectCode(rs.getString("subject_code"));
                    subject.setSubjectName(rs.getString("subject_name"));
                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }

    public SubjectBean selectSubjectById(int id) throws SQLException {
        SubjectBean subject = null;
        String sql = "SELECT * FROM registered_subjects WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            // FIX: ResultSet was not in try-with-resources in the original — resource leak
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    subject = new SubjectBean();
                    subject.setId(rs.getInt("id"));
                    subject.setMatricNo(rs.getString("matric_no"));
                    subject.setSubjectCode(rs.getString("subject_code"));
                    subject.setSubjectName(rs.getString("subject_name"));
                }
            }
        }
        return subject;
    }

    public boolean updateSubject(SubjectBean subject) throws SQLException {
        String sql = "UPDATE registered_subjects SET subject_code = ?, subject_name = ? WHERE id = ? AND matric_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, subject.getSubjectCode());
            ps.setString(2, subject.getSubjectName());
            ps.setInt(3, subject.getId());
            ps.setString(4, subject.getMatricNo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteSubject(int id, String matricNo) throws SQLException {
        String sql = "DELETE FROM registered_subjects WHERE id = ? AND matric_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, matricNo);
            return ps.executeUpdate() > 0;
        }
    }
}