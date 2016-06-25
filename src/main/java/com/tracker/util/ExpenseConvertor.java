package com.tracker.util;

import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;

/**
 * Created by nakulkumar on 24/6/16.
 */
public class ExpenseConvertor {
    public static ExpenseDetail getExpressDetailFromDTO(ExpenseDetailDTO dto) {
        ExpenseDetail expenseDetail = new ExpenseDetail(dto.getDescription(), dto.getAmount(), dto.getCreated(), dto.getUpdated());
        return expenseDetail;
    }
}
