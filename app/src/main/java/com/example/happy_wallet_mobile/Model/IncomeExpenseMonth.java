package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class IncomeExpenseMonth {
    private Date date;
    private BigDecimal income;
    private BigDecimal expense;
    public IncomeExpenseMonth(Date date, BigDecimal income, BigDecimal expense) {
        this.date = date;
        this.income = income;
        this.expense = expense;
    }

    public Date getDate() { return date; }
    public BigDecimal getIncome() { return income; }
    public BigDecimal getExpense() { return expense; }
}

