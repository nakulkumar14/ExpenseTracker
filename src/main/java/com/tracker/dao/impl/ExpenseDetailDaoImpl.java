package com.tracker.dao.impl;

import com.tracker.dao.ExpenseDetailDao;
import com.tracker.model.ExpenseDetail;
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

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Transactional
    @Override
    public void save(ExpenseDetail expenseDetail) {
        getCurrentSession().save(expenseDetail);
    }

    @Transactional
    @Override
    public void deleteByDescription(String description) {

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
}
