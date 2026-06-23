package com.banking.service;

import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

// Multithreading: Handles concurrent transactions safely using locks
public class TransactionService {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    // ReentrantLock ensures thread-safe balance updates
    private final ReentrantLock lock = new ReentrantLock();

    // Thread pool for processing multiple transactions concurrently
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public TransactionService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Deposit — thread safe
    public String deposit(int accountId, double amount) {
        lock.lock();
        try {
            Account account = accountDAO.getAccountById(accountId);
            if (account == null) return "Account not found!";
            if (amount <= 0) return "Invalid amount!";

            double newBalance = account.getBalance() + amount;
            accountDAO.updateBalance(accountId, newBalance);

            Transaction t = new Transaction(accountId, "DEPOSIT", amount, "Deposit of ₹" + amount);
            transactionDAO.saveTransaction(t);

            return "Deposit successful! New balance: ₹" + newBalance;
        } finally {
            lock.unlock();
        }
    }

    // Withdraw — thread safe
    public String withdraw(int accountId, double amount) {
        lock.lock();
        try {
            Account account = accountDAO.getAccountById(accountId);
            if (account == null) return "Account not found!";
            if (amount <= 0) return "Invalid amount!";
            if (account.getBalance() < amount) return "Insufficient balance!";

            double newBalance = account.getBalance() - amount;
            accountDAO.updateBalance(accountId, newBalance);

            Transaction t = new Transaction(accountId, "WITHDRAW", amount, "Withdrawal of ₹" + amount);
            transactionDAO.saveTransaction(t);

            return "Withdrawal successful! New balance: ₹" + newBalance;
        } finally {
            lock.unlock();
        }
    }

    // Transfer between accounts — thread safe
    public String transfer(int fromAccountId, int toAccountId, double amount) {
        lock.lock();
        try {
            Account from = accountDAO.getAccountById(fromAccountId);
            Account to = accountDAO.getAccountById(toAccountId);

            if (from == null || to == null) return "One or both accounts not found!";
            if (amount <= 0) return "Invalid amount!";
            if (from.getBalance() < amount) return "Insufficient balance!";

            accountDAO.updateBalance(fromAccountId, from.getBalance() - amount);
            accountDAO.updateBalance(toAccountId, to.getBalance() + amount);

            transactionDAO.saveTransaction(new Transaction(fromAccountId, "TRANSFER", amount, "Transfer to Account #" + toAccountId));
            transactionDAO.saveTransaction(new Transaction(toAccountId, "TRANSFER", amount, "Transfer from Account #" + fromAccountId));

            return "Transfer successful!";
        } finally {
            lock.unlock();
        }
    }

    // Async transaction processing using thread pool
    public void processAsync(Runnable task) {
        executor.submit(task);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
