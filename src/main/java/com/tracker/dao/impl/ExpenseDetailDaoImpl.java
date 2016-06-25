package com.tracker.dao.impl;

import com.tracker.dao.ExpenseDetailDao;
import com.tracker.model.ExpenseDetail;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nakulkumar on 24/6/16.
 */
//@EnableTransactionManagement
@Repository("expenseDetailDao")
public class ExpenseDetailDaoImpl implements ExpenseDetailDao {

    private static Logger LOG = Logger.getLogger(ExpenseDetailDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Transactional
    @Override
    public void save(ExpenseDetail expenseDetail) {
        LOG.info("Saving expense details for description : " + expenseDetail.getDescription() + " and created : " + expenseDetail.getCreated());
        getCurrentSession().save(expenseDetail);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        ExpenseDetail expenseDetail = (ExpenseDetail) getCurrentSession().get(ExpenseDetail.class, id);
        if (expenseDetail != null) {
            getCurrentSession().delete(expenseDetail);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExpenseDetail> getAll() {
        return getCurrentSession().createQuery("from ExpenseDetail").list();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExpenseDetail> getDetailsInRange(Date start, Date end) {
        Criteria criteria = getCurrentSession().createCriteria(ExpenseDetail.class);
        criteria.add(Restrictions.between("created", start, end));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExpenseDetail> getMonthlyExpenses(Date date) {
        Query query = getCurrentSession().createQuery("from ExpenseDetail where created like concat(:created,'%')");
        query.setParameter("created", new SimpleDateFormat("yyyy-MM").format(date));
        return query.list();
    }

    @Transactional(readOnly = true)
    @Override
    public ExpenseDetail getExpenseDetailById(Long id) {
        return (ExpenseDetail) getCurrentSession().get(ExpenseDetail.class, id);
    }

    @Transactional
    @Override
    public ExpenseDetail updateExpenseDetail(ExpenseDetail expenseDetail) {
        LOG.info("Updating expense detail for id : " + expenseDetail.getId());
        return (ExpenseDetail) getCurrentSession().merge(expenseDetail);
    }
}
