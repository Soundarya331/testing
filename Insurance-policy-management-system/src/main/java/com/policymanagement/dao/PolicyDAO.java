package com.policymanagement.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.policymanagement.entity.CustomerPolicy;
import com.policymanagement.entity.Policy;
import com.policymanagement.util.HibernateUtil;

@Repository
public class PolicyDAO {

    public Policy savePolicy(Policy policy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(policy);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return policy;
    }

    public List<Policy> getAllPolicies() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Policy", Policy.class).list();
        } finally {
            session.close();
        }
    }

    public Policy getPolicyById(int policyId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Policy.class, policyId);
        } finally {
            session.close();
        }
    }

    public boolean isPolicyNameExists(String policyName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                "SELECT COUNT(p) FROM Policy p WHERE p.policyName = :name", Long.class)
                .setParameter("name", policyName)
                .uniqueResult();
            return count > 0;
        } finally {
            session.close();
        }
    }

    
    public boolean isPolicyPurchased(int policyId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                "SELECT COUNT(cp) FROM CustomerPolicy cp WHERE cp.policy.policyId = :id", Long.class)
                .setParameter("id", policyId)
                .uniqueResult();
            return count > 0;
        } finally {
            session.close();
        }
    }

    public boolean deletePolicy(int policyId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Policy policy = session.get(Policy.class, policyId);
            if (policy != null) {
                session.delete(policy);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean existsByName(String policyName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                "SELECT COUNT(p) FROM Policy p WHERE p.policyName = :name", Long.class)
                .setParameter("name", policyName)
                .uniqueResult();
            return count > 0;
        } finally {
            session.close();
        }
    }
}