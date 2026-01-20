package com.sambasiva.finance_tracker.controllers;

import com.sambasiva.finance_tracker.models.Expense;
import com.sambasiva.finance_tracker.models.UserPrincipal;
import com.sambasiva.finance_tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

class DateRange {
    private String fromDate;
    private String toDate;

    public DateRange() {
    }

    public DateRange(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService service;


    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense exp, @AuthenticationPrincipal UserPrincipal principal) {
        exp.setUser(principal.getUser());
        return service.addExpense(exp);
    }

    @GetMapping("/all")
    public List<Expense> getAllExpenses(@AuthenticationPrincipal UserPrincipal principal) {
        return service.getAllExpenses(principal.getUser());
    }

    @PostMapping("/range")
    public List<Expense> getExpensesByMonth(@AuthenticationPrincipal UserPrincipal principal, @RequestBody DateRange range) {
        LocalDate fromDate = LocalDate.parse(range.getFromDate());
        LocalDateTime from = fromDate.atStartOfDay();

        LocalDate toDate = LocalDate.parse(range.getToDate());
        LocalDateTime to = toDate.atTime(LocalTime.MAX);

        return service.getExpensesbyRange(principal.getUser(), from, to);
    }

    @PostMapping("/dashboard/range")
    public Map getDashboardByRange(@AuthenticationPrincipal UserPrincipal principal, @RequestBody DateRange range) {
        LocalDate fromDate = LocalDate.parse(range.getFromDate());
        LocalDateTime from = fromDate.atStartOfDay();

        LocalDate toDate = LocalDate.parse(range.getToDate());
        LocalDateTime to = toDate.atTime(LocalTime.MAX);

        return service.getDashboardExpensesByRange(principal.getUser(), from, to);
    }

    @PutMapping("/update")
    public Expense updateExpense(@RequestBody Expense exp, @AuthenticationPrincipal UserPrincipal principal) {
        exp.setUser(principal.getUser());
        return service.updateExpense(exp);
    }

    @DeleteMapping("/delete")
    public Expense deleteExpense(@RequestBody Expense exp) {
        service.deleteExpense(exp);
        return exp;
    }

}
