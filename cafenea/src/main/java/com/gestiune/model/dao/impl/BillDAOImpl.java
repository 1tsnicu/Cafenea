package com.gestiune.model.dao.impl;

import com.gestiune.model.entities.Bill;
import com.gestiune.model.dao.interfaces.BillDAO;
import com.gestiune.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class BillDAOImpl implements BillDAO {

    @Override
    public void insert(Bill bill) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(bill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Bill getById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Bill.class, id);
        }
    }

    @Override
    public List<Bill> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Bill", Bill.class).list();
        }
    }

    @Override
    public void update(Bill bill) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(bill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Bill bill = session.get(Bill.class, id);
            if (bill != null) {
                session.delete(bill);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Bill> searchByProductName(String productName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bill> query = session.createQuery("FROM Bill WHERE productName LIKE :productName", Bill.class);
            query.setParameter("productName", "%" + productName + "%");
            return query.list();
        }
    }

    @Override
    public List<Bill> searchByCategoryName(String categoryName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bill> query = session.createQuery("FROM Bill WHERE categoryName LIKE :categoryName", Bill.class);
            query.setParameter("categoryName", "%" + categoryName + "%");
            return query.list();
        }
    }

    @Override
    public List<Bill> searchByPriceRange(Double price) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bill> query = session.createQuery("FROM Bill WHERE price = :price", Bill.class);
            query.setParameter("price", price);
            return query.list();
        }
    }

    @Override
    public List<Bill> searchByOrderDateRange(Date orderDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bill> query = session.createQuery("FROM Bill WHERE orderDate = :orderDate", Bill.class);
            query.setParameter("orderDate", sdf.format(orderDate));
            return query.list();
        }
    }
}