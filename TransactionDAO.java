package com.banking.dao;

import com.banking.model.Transaction;
import com.banking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private Connection conn;

    public TransactionDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }

    // Save transaction
    public boolean saveTransaction(Transaction t) {
        String sql = "INSERT INTO transactions (account_id, type, amount, status, description, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getAccountId());
            ps.setString(2, t.getType());
            ps.setDouble(3, t.getAmount());
            ps.setString(4, t.getStatus());
            ps.setString(5, t.getDescription());
            ps.setTimestamp(6, Timestamp.valueOf(t.getTimestamp()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            return false;
        }
    }

    // Get transactions by account — uses ArrayList (Collections)
    public List<Transaction> getTransactionsByAccount(int accountId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAccountId(rs.getInt("account_id"));
                t.setType(rs.getString("type"));
                t.setAmount(rs.getDouble("amount"));
                t.setStatus(rs.getString("status"));
                t.setDescription(rs.getString("description"));
                t.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }
        return list;
    }
}
