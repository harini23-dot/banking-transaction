<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Banking Transaction System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; color: #333; }
        header { background: #1a237e; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
        header h1 { font-size: 1.4rem; }
        .container { max-width: 1000px; margin: 2rem auto; padding: 0 1rem; }
        .grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; }
        .card { background: white; border-radius: 10px; padding: 1.5rem; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
        .card h2 { font-size: 1rem; color: #1a237e; margin-bottom: 1rem; border-bottom: 2px solid #e8eaf6; padding-bottom: 0.5rem; }
        label { font-size: 13px; color: #555; display: block; margin-bottom: 4px; margin-top: 10px; }
        input, select { width: 100%; padding: 8px 10px; border: 1px solid #ddd; border-radius: 6px; font-size: 14px; }
        input:focus, select:focus { outline: none; border-color: #1a237e; }
        button { margin-top: 1rem; width: 100%; padding: 10px; background: #1a237e; color: white; border: none; border-radius: 6px; font-size: 14px; cursor: pointer; }
        button:hover { background: #283593; }
        .result { margin-top: 1rem; padding: 0.75rem; border-radius: 6px; font-size: 13px; display: none; }
        .result.success { background: #e8f5e9; color: #2e7d32; border: 1px solid #c8e6c9; }
        .result.error { background: #ffebee; color: #c62828; border: 1px solid #ffcdd2; }
        .full { grid-column: 1 / -1; }
        table { width: 100%; border-collapse: collapse; font-size: 13px; }
        th { background: #e8eaf6; color: #1a237e; padding: 8px; text-align: left; }
        td { padding: 8px; border-bottom: 1px solid #f0f0f0; }
        tr:hover td { background: #fafafa; }
    </style>
</head>
<body>

<header>
    <h1>🏦 Banking Transaction System</h1>
    <span style="font-size:13px; opacity:0.8;">Java · Servlets · Collections · Multithreading</span>
</header>

<div class="container">
    <div class="grid">

        <!-- Create Account -->
        <div class="card">
            <h2>Create Account</h2>
            <label>Account Holder Name</label>
            <input type="text" id="holderName" placeholder="Enter full name"/>
            <label>Account Type</label>
            <select id="accountType">
                <option value="SAVINGS">Savings</option>
                <option value="CURRENT">Current</option>
            </select>
            <label>Initial Balance (₹)</label>
            <input type="number" id="initialBalance" placeholder="Enter initial balance"/>
            <label>Email</label>
            <input type="email" id="email" placeholder="Enter email"/>
            <button onclick="createAccount()">Create Account</button>
            <div class="result" id="createResult"></div>
        </div>

        <!-- Deposit / Withdraw -->
        <div class="card">
            <h2>Deposit / Withdraw</h2>
            <label>Account ID</label>
            <input type="number" id="txAccountId" placeholder="Enter account ID"/>
            <label>Amount (₹)</label>
            <input type="number" id="txAmount" placeholder="Enter amount"/>
            <label>Transaction Type</label>
            <select id="txType">
                <option value="deposit">Deposit</option>
                <option value="withdraw">Withdraw</option>
            </select>
            <button onclick="doTransaction()">Submit</button>
            <div class="result" id="txResult"></div>
        </div>

        <!-- Transfer -->
        <div class="card">
            <h2>Fund Transfer</h2>
            <label>From Account ID</label>
            <input type="number" id="fromId" placeholder="From account ID"/>
            <label>To Account ID</label>
            <input type="number" id="toId" placeholder="To account ID"/>
            <label>Amount (₹)</label>
            <input type="number" id="transferAmount" placeholder="Enter amount"/>
            <button onclick="doTransfer()">Transfer</button>
            <div class="result" id="transferResult"></div>
        </div>

        <!-- View Accounts -->
        <div class="card">
            <h2>Account Summary</h2>
            <button onclick="loadAccounts()" style="margin-top:0;">Refresh Accounts</button>
            <div style="margin-top:1rem; overflow-x:auto;">
                <table id="accountTable">
                    <thead>
                        <tr><th>ID</th><th>Holder</th><th>Type</th><th>Balance</th></tr>
                    </thead>
                    <tbody id="accountBody">
                        <tr><td colspan="4" style="text-align:center;color:#999;">Click refresh to load</td></tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<script>
    function showResult(id, message, isSuccess) {
        const el = document.getElementById(id);
        el.textContent = message;
        el.className = 'result ' + (isSuccess ? 'success' : 'error');
        el.style.display = 'block';
    }

    function createAccount() {
        const data = new URLSearchParams({
            accountHolder: document.getElementById('holderName').value,
            accountType: document.getElementById('accountType').value,
            initialBalance: document.getElementById('initialBalance').value,
            email: document.getElementById('email').value
        });
        fetch('account', { method: 'POST', body: data })
            .then(r => r.json())
            .then(d => showResult('createResult', d.message, d.message.includes('success')))
            .catch(() => showResult('createResult', 'Request failed!', false));
    }

    function doTransaction() {
        const data = new URLSearchParams({
            action: document.getElementById('txType').value,
            accountId: document.getElementById('txAccountId').value,
            amount: document.getElementById('txAmount').value
        });
        fetch('transaction', { method: 'POST', body: data })
            .then(r => r.json())
            .then(d => showResult('txResult', d.message, d.message.includes('success')))
            .catch(() => showResult('txResult', 'Request failed!', false));
    }

    function doTransfer() {
        const data = new URLSearchParams({
            action: 'transfer',
            accountId: document.getElementById('fromId').value,
            toAccountId: document.getElementById('toId').value,
            amount: document.getElementById('transferAmount').value
        });
        fetch('transaction', { method: 'POST', body: data })
            .then(r => r.json())
            .then(d => showResult('transferResult', d.message, d.message.includes('success')))
            .catch(() => showResult('transferResult', 'Request failed!', false));
    }

    function loadAccounts() {
        fetch('account')
            .then(r => r.json())
            .then(accounts => {
                const tbody = document.getElementById('accountBody');
                tbody.innerHTML = accounts.map(a =>
                    `<tr><td>${a.accountId}</td><td>${a.accountHolder}</td><td>${a.accountType}</td><td>₹${a.balance.toLocaleString()}</td></tr>`
                ).join('');
            })
            .catch(() => {
                document.getElementById('accountBody').innerHTML = '<tr><td colspan="4" style="color:red;">Failed to load</td></tr>';
            });
    }
</script>

</body>
</html>
