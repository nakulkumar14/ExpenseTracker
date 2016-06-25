package com.tracker.util;

import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;
import org.apache.log4j.Logger;

/**
 * Created by nakulkumar on 24/6/16.
 */
public class ExpenseConvertor {

    private static Logger LOG = Logger.getLogger(ExpenseConvertor.class);

    public static ExpenseDetail getExpressDetailFromDTO(ExpenseDetailDTO dto) {
        LOG.info("Converting dto to entity for description : " + dto.getDescription() + " and created : " + dto.getCreated());
        ExpenseDetail expenseDetail = new ExpenseDetail(dto.getDescription(), dto.getAmount(), dto.getCreated(), dto.getUpdated());
        return expenseDetail;
    }
}
