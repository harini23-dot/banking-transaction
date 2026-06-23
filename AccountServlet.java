package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private AccountDAO accountDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
    }

    // Create new account
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("accountHolder");
        String type = request.getParameter("accountType");
        double balance = Double.parseDouble(request.getParameter("initialBalance"));
        String email = request.getParameter("email");

        Account account = new Account(0, name, type, balance, email);
        boolean created = accountDAO.createAccount(account);

        if (created) {
            out.println("{\"message\": \"Account created successfully!\"}");
        } else {
            out.println("{\"message\": \"Failed to create account!\"}");
        }
    }

    // Get all accounts
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<Account> accounts = accountDAO.getAllAccounts();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            json.append("{")
                .append("\"accountId\":").append(a.getAccountId()).append(",")
                .append("\"accountHolder\":\"").append(a.getAccountHolder()).append("\",")
                .append("\"accountType\":\"").append(a.getAccountType()).append("\",")
                .append("\"balance\":").append(a.getBalance())
                .append("}");
            if (i < accounts.size() - 1) json.append(",");
        }
        json.append("]");
        out.println(json.toString());
    }
}
