package com.gestiune.model.dao.interfaces;

import com.gestiune.model.entities.Bill;
import java.util.List;
import java.util.Date;

public interface BillDAO {

    void insert(Bill bill);
    Bill getById(Integer id);
    List<Bill> getAll();
    void update(Bill bill);
    void delete(Integer id);
    List<Bill> searchByProductName(String productName);
    List<Bill> searchByCategoryName(String categoryName);
    List<Bill> searchByPriceRange(Double price);
    List<Bill> searchByOrderDateRange(Date orderDate);
}