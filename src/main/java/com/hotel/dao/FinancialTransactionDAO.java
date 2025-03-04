package com.hotel.dao;

import com.hotel.model.FinancialTransaction;
import com.hotel.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FinancialTransactionDAO {
    
    public void add(FinancialTransaction transaction) throws SQLException {
        String sql = "INSERT INTO financial_transactions (type, amount, description, transaction_date, category) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, transaction.getType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getDescription());
            pstmt.setTimestamp(4, Timestamp.valueOf(transaction.getTransactionDate() != null ? 
                                                  transaction.getTransactionDate() : 
                                                  LocalDateTime.now()));
            pstmt.setString(5, transaction.getCategory());
            
            pstmt.executeUpdate();
        }
    }

    public void update(FinancialTransaction transaction) throws SQLException {
        String sql = "UPDATE financial_transactions SET type = ?, amount = ?, description = ?, " +
                    "transaction_date = ?, category = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, transaction.getType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getDescription());
            pstmt.setTimestamp(4, Timestamp.valueOf(transaction.getTransactionDate()));
            pstmt.setString(5, transaction.getCategory());
            pstmt.setInt(6, transaction.getId());
            
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM financial_transactions WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteTransactionById(int id) throws SQLException {
        String sql = "DELETE FROM financial_transactions WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public FinancialTransaction getById(int id) throws SQLException {
        String sql = "SELECT * FROM financial_transactions WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractTransactionFromResultSet(rs);
            }
        }
        return null;
    }

    public List<FinancialTransaction> getAll() throws SQLException {
        List<FinancialTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM financial_transactions ORDER BY transaction_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                transactions.add(extractTransactionFromResultSet(rs));
            }
        }
        return transactions;
    }

    public List<FinancialTransaction> getAllTransactions() {
        List<FinancialTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM financial_transactions"; 

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FinancialTransaction transaction = new FinancialTransaction(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getTimestamp("transaction_date").toLocalDateTime(),
                    rs.getString("category")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public double getTotalByType(String type) throws SQLException {
        String sql = "SELECT NVL(SUM(amount), 0) FROM financial_transactions WHERE type = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        }
    }

    public double getTotalIncome() {
        double total = 0;
        String sql = "SELECT NVL(SUM(amount), 0) FROM financial_transactions WHERE type = 'INCOME'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public double getTotalExpenses() {
        double total = 0;
        String sql = "SELECT NVL(SUM(amount), 0) FROM financial_transactions WHERE type = 'EXPENSE'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private FinancialTransaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setId(rs.getInt("id"));
        transaction.setType(rs.getString("type"));
        transaction.setAmount(rs.getDouble("amount"));
        transaction.setDescription(rs.getString("description"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        transaction.setCategory(rs.getString("category"));
        return transaction;
    }
}
