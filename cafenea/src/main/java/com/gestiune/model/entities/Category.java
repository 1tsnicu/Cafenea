package com.gestiune.model.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    // Constructors
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
