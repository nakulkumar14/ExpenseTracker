package com.tracker.dao;

import com.tracker.model.ExpenseDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by nakulkumar on 24/6/16.
 */
public interface ExpenseDetailDao {

    void save(ExpenseDetail expenseDetail);

    ExpenseDetail getExpenseDetailById(Long id);

    boolean delete(Long id);

    ExpenseDetail updateExpenseDetail(ExpenseDetail expenseDetail);

    List<ExpenseDetail> getAllExpenseDetails();

    List<ExpenseDetail> getDetailsInRange(Date start, Date end);

    List<ExpenseDetail> getMonthlyExpenses(Date date);
}
