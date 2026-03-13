package com.policymanagement.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.policymanagement.entity.Customer;
import com.policymanagement.util.HibernateUtil;

@Repository
public class CustomerDAO {

    public Customer saveCustomer(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(customer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customer;
    }

    public Customer findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                "FROM Customer WHERE email = :email", Customer.class)
                .setParameter("email", email)
                .uniqueResult();
        } finally {
            session.close();
        }
    }

    public Customer findByEmailAndPassword(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                "FROM Customer WHERE email = :email AND password = :password", Customer.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
        } finally {
            session.close();
        }
    }

    public boolean existsByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                "SELECT COUNT(c) FROM Customer c WHERE c.email = :email", Long.class)
                .setParameter("email", email)
                .uniqueResult();
            return count > 0;
        } finally {
            session.close();
        }
    }

    public boolean existsByPhone(long phoneNumber) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                "SELECT COUNT(c) FROM Customer c WHERE c.phoneNumber = :phone", Long.class)
                .setParameter("phone", phoneNumber)
                .uniqueResult();
            return count > 0;
        } finally {
            session.close();
        }
    }
}