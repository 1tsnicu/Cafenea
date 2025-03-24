package com.gestiune.model.dao.interfaces;

import com.gestiune.model.entities.Category;
import java.util.List;

public interface CategoryDAO {

    void insert(Category category);

    Category getByName(String Name);
    List<Category> getAll();

    void update(Category category);

    void delete(String name);

    List<Category> searchByName(String name);
}