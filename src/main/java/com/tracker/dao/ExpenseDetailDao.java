package com.tracker.dao;

import com.tracker.model.ExpenseDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by nakulkumar on 24/6/16.
 */
public interface ExpenseDetailDao {

    void save(ExpenseDetail expenseDetail);

    void deleteByDescription(String description);

    List<ExpenseDetail> getAll();

    List<ExpenseDetail> getDetailsInRange(Date start, Date end);

    List<ExpenseDetail> getMonthlyExpenses(Date date);
}
