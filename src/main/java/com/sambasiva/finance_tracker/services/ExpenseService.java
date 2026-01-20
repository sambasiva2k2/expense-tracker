package com.sambasiva.finance_tracker.services;

import com.sambasiva.finance_tracker.models.Expense;
import com.sambasiva.finance_tracker.models.User;
import com.sambasiva.finance_tracker.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository repository;

    public Expense addExpense(Expense exp) {
        return repository.save(exp);
    }

    public List<Expense> getAllExpenses(User user) {
        return repository.findByUser(user);
    }

    public Expense updateExpense(Expense exp) {
        return repository.save(exp);
    }

    public void deleteExpense(Expense exp) {
        repository.delete(exp);
    }

    public List<Expense> getExpensesbyRange(User user, LocalDateTime fromDate, LocalDateTime toDate) {
        return repository.findByUserAndCreatedOnBetween(user, fromDate, toDate);
    }

    public Map getDashboardExpensesByRange(User user, LocalDateTime fromDate, LocalDateTime toDate) {
        List<Expense> expenses = repository.findByUserAndCreatedOnBetween(user, fromDate, toDate);
        Float sum = repository.getSumOfAmountByRange(user, fromDate, toDate);
        LocalDateTime previousMonthFrom = fromDate.minusMonths(1);
        LocalDateTime previousMonthTo = toDate.minusMonths(1);
        Float previousMonthSum = repository.getSumOfAmountByRange(user, previousMonthFrom, previousMonthTo);
        Map map = new HashMap();
        map.put("expenses", expenses);
        map.put("sum", sum);
        map.put("previousSum", previousMonthSum);
        return map;
    }
}
