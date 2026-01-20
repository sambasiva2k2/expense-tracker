package com.sambasiva.finance_tracker.repositories;

import com.sambasiva.finance_tracker.models.Expense;
import com.sambasiva.finance_tracker.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Integer> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCreatedOnBetween(User user, LocalDateTime from, LocalDateTime to);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Expense p WHERE p.user= ?1 AND p.createdOn BETWEEN ?2 and ?3")
    Float getSumOfAmountByRange(User user, LocalDateTime from, LocalDateTime to);
}
