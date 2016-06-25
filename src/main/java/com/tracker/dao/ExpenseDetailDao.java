package com.tracker.dao;

import com.tracker.model.ExpenseDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by nakulkumar on 24/6/16.
 */
public interface ExpenseDetailDao {

    void save(ExpenseDetail expenseDetail);

    boolean delete(Long id);

    List<ExpenseDetail> getAll();

    List<ExpenseDetail> getDetailsInRange(Date start, Date end);

    List<ExpenseDetail> getMonthlyExpenses(Date date);
}
