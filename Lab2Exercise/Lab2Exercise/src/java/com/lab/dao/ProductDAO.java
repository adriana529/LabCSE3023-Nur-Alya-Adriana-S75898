/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lab.dao;

import com.lab.model.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sazli osman 
 */
public class ProductDAO {
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
    public void insertProducts(Product product) {
        String sql = "INSERT INTO products (name, category, price, quantity)VALUES( ?,  ?,  ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Ambil satu pengguna berdasarkan ID (Untuk Update)
    public Product selectProduct(int id) {
        Product product = null;
        String sql = "SELECT id, name, category, price, quantity FROM products WHERE id =  ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                product = new Product(id, name, category, price, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // READ: Ambil senarai semua pengguna (Untuk View)
    public List<Product> selectAllProduct() {
        List<Product> prod = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");

                String name = rs.getString("name");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                prod.add(new Product(id, name, category, price, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prod;
    }

    // UPDATE: Kemaskini maklumat pengguna
    public boolean updateProducts(Product product) {
        boolean rowUpdated = false;
        String sql = "UPDATE product SET name = ?, category = ?, price =  ?, quantity = ? WHERE  id =  ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setInt(5, product.getId());
            rowUpdated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    // DELETE: Padam pengguna
    public boolean deleteProduct(int id) {
        boolean rowDeleted = false;
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rowDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
