# 🏦 Banking Transaction System

A full-stack Java web application for managing banking operations including account management, deposits, withdrawals, and fund transfers — built with Servlets, JSP, JDBC, and MySQL.

## 🚀 Features

- Create and manage bank accounts (Savings / Current)
- Deposit and Withdraw funds
- Fund Transfer between accounts
- Real-time account balance updates
- Thread-safe concurrent transaction processing
- Web-based UI using JSP and Servlets

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Language** | Core Java |
| **Web Layer** | Servlets, JSP |
| **Database** | MySQL + JDBC |
| **Concurrency** | Multithreading, ReentrantLock, ExecutorService |
| **Collections** | ArrayList, HashMap |
| **Design Pattern** | Singleton (DBConnection), DAO Pattern |
| **Frontend** | HTML, CSS, JavaScript |

## 💡 Key Concepts Demonstrated

### Collections Framework
- `ArrayList` — storing and iterating account/transaction lists
- `HashMap` — grouping accounts by type for fast lookup

### Multithreading
- `ReentrantLock` — ensures thread-safe balance updates during concurrent transactions
- `ExecutorService` with fixed thread pool — handles async transaction processing
- Prevents race conditions during simultaneous deposits/withdrawals

### Servlets
- `AccountServlet` — handles account creation (POST) and retrieval (GET)
- `TransactionServlet` — handles deposit, withdraw, and transfer operations
- `@WebServlet` annotation-based mapping

### Database (SQL)
- DDL: CREATE TABLE with constraints and foreign keys
- DML: INSERT, SELECT, UPDATE operations
- Normalization: Separate accounts and transactions tables

## 📁 Project Structure

```
BankingSystem/
├── src/main/java/com/banking/
│   ├── model/          ← Account.java, Transaction.java
│   ├── dao/            ← AccountDAO.java, TransactionDAO.java
│   ├── service/        ← TransactionService.java (Multithreading)
│   ├── servlet/        ← AccountServlet.java, TransactionServlet.java
│   └── util/           ← DBConnection.java (Singleton)
├── src/main/webapp/
│   └── index.jsp       ← Web UI
├── sql/
│   └── schema.sql      ← Database schema + sample data
└── README.md
```

## ⚙️ Setup Instructions

### Prerequisites
- Java JDK 11+
- MySQL 8.0+
- Apache Tomcat 9+
- IDE: Eclipse / IntelliJ IDEA

### 1. Create Database
```sql
mysql -u root -p < sql/schema.sql
```

### 2. Configure DB Connection
Edit `src/main/java/com/banking/util/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/banking_db";
private static final String USER = "Harini";
private static final String PASSWORD = "Harini@23";
```

### 3. Deploy on Tomcat
- Export as WAR file
- Drop in Tomcat `webapps/` folder
- Start Tomcat
- Visit: `http://localhost:8080/BankingSystem`

## 👩‍💻 Author
Harini S | MCA Student @ VIT Vellore
