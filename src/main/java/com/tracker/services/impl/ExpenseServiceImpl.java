package com.tracker.services.impl;

import com.tracker.dao.ExpenseDetailDao;
import com.tracker.dto.ExpenseDetailDTO;
import com.tracker.model.ExpenseDetail;
import com.tracker.services.ExpenseService;
import com.tracker.util.ExpenseConvertor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nakulkumar on 24/6/16.
 */
@Service("expenseService")
public class ExpenseServiceImpl implements ExpenseService {

    private static Logger LOG = Logger.getLogger(ExpenseServiceImpl.class);

    @Autowired
    private ExpenseDetailDao expenseDetailDao;

    @Override
    public void addExpense(ExpenseDetailDTO expenseDetailDTO) {
        ExpenseDetail expenseDetail = ExpenseConvertor.getExpressDetailFromDTO(expenseDetailDTO);
//        expenseDetail.setCreated(new Date());
        expenseDetail.setUpdated(new Date());
        expenseDetailDao.save(expenseDetail);
    }

    @Override
    public List<ExpenseDetail> getExpenses(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date actualDate = format.parse(date);

            Calendar c = Calendar.getInstance();
            c.setTime(actualDate);
            c.add(Calendar.DATE, 1);
            Date newDate = c.getTime();
            LOG.info("Fetching expense detail in range : " + actualDate + " and " + newDate);
            return expenseDetailDao.getDetailsInRange(actualDate, newDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ExpenseDetail>();
        }
    }

    @Override
    public boolean delete(Long id) {
        return expenseDetailDao.delete(id);
    }

    @Override
    public List<ExpenseDetail> getAll() {
        return expenseDetailDao.getAll();
    }

    @Override
    public List<ExpenseDetail> getAllForToday() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = c.getTime();
        LOG.info("Getting expense details between : " + today + " and " + tomorrow);
        return expenseDetailDao.getDetailsInRange(today, tomorrow);
    }

    @Override
    public List<ExpenseDetail> getMonthlyExpenses(String month, String year) {
        String date = year + "-" + month;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date startDate = null;
        try {
            startDate = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenseDetailDao.getMonthlyExpenses(startDate);
    }

    @Override
    public ExpenseDetail getExpenseDetailById(Long id) {
        return expenseDetailDao.getExpenseDetailById(id);
    }

    @Override
    public ExpenseDetail updateExpenseDetail(ExpenseDetailDTO expenseDetailDTO) {
        ExpenseDetail expenseDetail = ExpenseConvertor.getExpressDetailFromDTO(expenseDetailDTO);
        expenseDetail.setId(expenseDetailDTO.getId());
        expenseDetail.setUpdated(new Date());
        return expenseDetailDao.updateExpenseDetail(expenseDetail);
    }
}
