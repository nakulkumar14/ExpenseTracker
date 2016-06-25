package com.tracker.services;

import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;

import java.util.List;

/**
 * Created by nakulkumar on 24/6/16.
 */
public interface ExpenseService {
    void addExpense(ExpenseDetailDTO expenseDetailDTO);

    List<ExpenseDetail> getExpenses(String date);

    ExpenseDetail getExpenseDetailById(Long id);

    ExpenseDetail updateExpenseDetail(ExpenseDetailDTO expenseDetailDTO);

    boolean delete(Long id);

    List<ExpenseDetail> getAll();

    List<ExpenseDetail> getAllForToday();

    List<ExpenseDetail> getMonthlyExpenses(String month, String year);
}
