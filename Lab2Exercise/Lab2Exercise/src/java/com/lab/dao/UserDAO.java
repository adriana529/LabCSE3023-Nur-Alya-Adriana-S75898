/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lab.dao;

import com.lab.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class UserDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/CSE3023";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    // Method untuk mendapatkan sambungan Database
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // CREATE: Masukkan pengguna baharu
    public void insertUser(User user) {
        String sql = "INSERT INTO users (username, password, roles)VALUES( ?,  ?,  ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRoles());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Ambil satu pengguna berdasarkan ID (Untuk Update)
    public User selectUser(int id) {
        User user = null;
        String sql = "SELECT id, username, password, roles FROM users WHERE id =  ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String roles = rs.getString("roles");
                user = new User(id, username, password, roles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // READ: Ambil senarai semua pengguna (Untuk View)
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");

                String username = rs.getString("username");
                String password = rs.getString("password");
                String roles = rs.getString("roles");
                users.add(new User(id, username, password, roles));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // UPDATE: Kemaskini maklumat pengguna
    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        String sql = "UPDATE users SET username = ?, password = ?, roles =  ? WHERE  id =  ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRoles());
            pstmt.setInt(4, user.getId());
            rowUpdated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    // DELETE: Padam pengguna
    public boolean deleteUser(int id) {
        boolean rowDeleted = false;
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rowDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
    
    

}
