package com.gestiune.model.dao.interfaces;

import com.gestiune.model.entities.Product;
import java.util.List;

public interface ProductsDAO {
    void insert(Product product);

    Product getByName(String name);
    List<Product> getAll();

    void update(Product product);

    void delete(Integer id);

    // Add search methods for all fields
    List<Product> searchByName(String name);
    List<Product> searchByCategory(String categoryName);
    List<Product> searchByPrice(Double price);
}