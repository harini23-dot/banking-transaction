package com.banking.dao;

import com.banking.model.Account;
import com.banking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDAO {

    private Connection conn;

    public AccountDAO() {
        this.conn = DBConnection.getInstance().getConnection();
    }

    // Create new account
    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (account_holder, account_type, balance, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getAccountHolder());
            ps.setString(2, account.getAccountType());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    // Get account by ID
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching account: " + e.getMessage());
        }
        return null;
    }

    // Get all accounts — uses ArrayList (Collections)
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(mapAccount(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }
        return accounts;
    }

    // Get accounts grouped by type — uses HashMap (Collections)
    public Map<String, List<Account>> getAccountsGroupedByType() {
        Map<String, List<Account>> map = new HashMap<>();
        List<Account> all = getAllAccounts();
        for (Account a : all) {
            map.computeIfAbsent(a.getAccountType(), k -> new ArrayList<>()).add(a);
        }
        return map;
    }

    // Update balance
    public boolean updateBalance(int accountId, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        return new Account(
            rs.getInt("account_id"),
            rs.getString("account_holder"),
            rs.getString("account_type"),
            rs.getDouble("balance"),
            rs.getString("email")
        );
    }
}
