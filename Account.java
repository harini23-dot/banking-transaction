package com.banking.model;

public class Account {
    private int accountId;
    private String accountHolder;
    private String accountType; // SAVINGS / CURRENT
    private double balance;
    private String email;

    public Account() {}

    public Account(int accountId, String accountHolder, String accountType, double balance, String email) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.balance = balance;
        this.email = email;
    }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getAccountHolder() { return accountHolder; }
    public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Account{id=" + accountId + ", holder=" + accountHolder + ", balance=" + balance + "}";
    }
}
