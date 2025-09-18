// Expense Tracker Frontend (No server required)

// DOM Elements
const expensesList = document.getElementById('expensesList');
const totalAmount = document.getElementById('totalAmount');
const predictionAmount = document.getElementById('predictionAmount');
const predictionTrend = document.getElementById('predictionTrend');
const expenseFileInput = document.getElementById('expenseFile');
const predictionFileInput = document.getElementById('predictionFile');

// State
let expenses = [];

// Event listeners for file inputs
expenseFileInput.addEventListener('change', handleExpenseFile);
predictionFileInput.addEventListener('change', handlePredictionFile);

// Load CSV file selected by user
function handleExpenseFile(event) {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = function(e) {
        const text = e.target.result;
        parseExpensesCSV(text);
        updateExpensesUI();
    };
    reader.readAsText(file);
}

// Parse CSV content
function parseExpensesCSV(csvText) {
    const lines = csvText.trim().split('\n');
    expenses = [];
    for (let i = 1; i < lines.length; i++) {
        const [name, amount, date] = lines[i].split(',');
        expenses.push({ name, amount: parseFloat(amount), date });
    }
}

// Update expenses list and total
function updateExpensesUI() {
    if (expenses.length === 0) {
        expensesList.innerHTML = '<p>No expenses yet</p>';
        totalAmount.textContent = '$0.00';
        return;
    }
    expensesList.innerHTML = expenses.map(exp => `
        <div class="expense-item flex justify-between bg-gray-50 p-2 rounded">
            <span>${exp.name}</span>
            <span>$${exp.amount.toFixed(2)}</span>
        </div>
    `).join('');
    const total = expenses.reduce((sum, e) => sum + e.amount, 0);
    totalAmount.textContent = `$${total.toFixed(2)}`;
}

// Load prediction from text file
function handlePredictionFile(event) {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = function(e) {
        const text = e.target.result.trim();
        predictionAmount.textContent = `$${parseFloat(text).toFixed(2)}`;
        predictionTrend.textContent = '📈 Predicted spending next week';
    };
    reader.readAsText(file);
}
