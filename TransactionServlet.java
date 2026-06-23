package com.banking.servlet;

import com.banking.service.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    private TransactionService transactionService;

    @Override
    public void init() {
        transactionService = new TransactionService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        String result;

        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            double amount = Double.parseDouble(request.getParameter("amount"));

            switch (action) {
                case "deposit":
                    result = transactionService.deposit(accountId, amount);
                    break;
                case "withdraw":
                    result = transactionService.withdraw(accountId, amount);
                    break;
                case "transfer":
                    int toAccountId = Integer.parseInt(request.getParameter("toAccountId"));
                    result = transactionService.transfer(accountId, toAccountId, amount);
                    break;
                default:
                    result = "Invalid action!";
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        out.println("{\"message\": \"" + result + "\"}");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
