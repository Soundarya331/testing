package com.policymanagement.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.policymanagement.entity.CustomerPolicy;
import com.policymanagement.util.HibernateUtil;

@Repository
public class CustomerPolicyDAO {

    public CustomerPolicy save(CustomerPolicy cp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(cp);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cp;
    }

    // Rule #7 — check duplicate purchase
    public boolean alreadyPurchased(int customerId, int policyId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                "SELECT COUNT(cp) FROM CustomerPolicy cp " +
                "WHERE cp.customer.customerId = :cid AND cp.policy.policyId = :pid", Long.class)
                .setParameter("cid", customerId)
                .setParameter("pid", policyId)
                .uniqueResult();
            return count > 0;
        } finally {
            session.close();
        }
    }

    // Rule #10 — customer sees only their policies
    public List<CustomerPolicy> getByCustomer(int customerId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                "FROM CustomerPolicy cp WHERE cp.customer.customerId = :cid", CustomerPolicy.class)
                .setParameter("cid", customerId)
                .list();
        } finally {
            session.close();
        }
    }

    public List<CustomerPolicy> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM CustomerPolicy", CustomerPolicy.class).list();
        } finally {
            session.close();
        }
    }
}