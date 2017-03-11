package com.tracker.util;

import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by nakulkumar on 24/6/16.
 */
@Service("expenseConvertor")
public class ExpenseConvertor {

    private static final Logger LOG = Logger.getLogger(ExpenseConvertor.class);

    public ExpenseDetail getExpressDetailFromDTO(ExpenseDetailDTO dto) {
        LOG.info("Converting dto to entity for description : " + dto.getDescription() + " and created : " + dto.getCreated());
        ExpenseDetail expenseDetail = new ExpenseDetail(dto.getDescription(), dto.getAmount(), dto.getCreated(), dto.getUpdated());
        return expenseDetail;
    }
}
