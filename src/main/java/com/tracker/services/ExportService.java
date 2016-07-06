package com.tracker.services;

import com.tracker.model.ExpenseDetail;

import java.util.List;

/**
 * Created by nakulkumar on 27/6/16.
 */
public interface ExportService {
    void exportToXLS(List<ExpenseDetail> expenseDetails, String date);
}
