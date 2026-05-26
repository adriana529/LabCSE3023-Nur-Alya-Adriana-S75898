package com.lab.dao;

import com.lab.bean.StudentBean;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class StudentDAO {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/CSE3023", "root", "");
    }

    public boolean registerStudent(StudentBean student, InputStream imageStream) {
        // ✅ MATCHES SCHEMA: matricNo, fullName, password, profile_picture
        String sql = "INSERT INTO students (matricNo, fullName, password, profile_picture) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getMatricNo());
            pstmt.setString(2, student.getFullName());    
            pstmt.setString(3, student.getPassword());    
            pstmt.setBlob(4, imageStream);               

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public StudentBean loginStudent(String matricNo, String password) {
        StudentBean student = null;
        String sql = "SELECT * FROM students WHERE matricNo = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricNo);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    student = new StudentBean();
                    student.setMatricNo(rs.getString("matricNo"));
                    student.setFullName(rs.getString("fullName")); 

                    Blob blob = rs.getBlob("profile_picture");      
                    if (blob != null) {
                        try (InputStream inputStream = blob.getBinaryStream();
                             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            student.setBase64Image(
                                Base64.getEncoder().encodeToString(outputStream.toByteArray())
                            );
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public boolean deleteStudent(String matricNo) {
        String sql = "DELETE FROM students WHERE matricNo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricNo);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}