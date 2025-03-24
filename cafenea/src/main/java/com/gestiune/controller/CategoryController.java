package com.gestiune.controller;

import com.gestiune.model.entities.Category;
import com.gestiune.model.dao.impl.CategoryDAOImpl;
import com.gestiune.model.dao.interfaces.CategoryDAO;
import com.gestiune.view.CategoryPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CategoryController {
    private final CategoryPanel categoryPanel;
    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;
        this.categoryDAO = new CategoryDAOImpl();

        this.categoryPanel.getAddButton().addActionListener(e -> addCategory());
        this.categoryPanel.getUpdateButton().addActionListener(e -> updateCategory());
        this.categoryPanel.getDeleteButton().addActionListener(e -> deleteCategory());
        this.categoryPanel.getSearchButton().addActionListener(e -> searchCategory());
    }

    private void addCategory() {
        try {
            String name = categoryPanel.getNameField().getText();
            Category category = new Category();
            category.setName(name);
            categoryDAO.insert(category);
            loadData();
            JOptionPane.showMessageDialog(categoryPanel, "Category added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error adding category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCategory() {
        try {
            int selectedRow = categoryPanel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(categoryPanel, "Please select a category to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String name = categoryPanel.getNameField().getText();
            Category category = categoryDAO.getByName(name);
            category.setName(name);
            categoryDAO.update(category);
            loadData();
            JOptionPane.showMessageDialog(categoryPanel, "Category updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error updating category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCategory() {
        try {
            int selectedRow = categoryPanel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(categoryPanel, "Please select a category to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String name = name = (String) categoryPanel.getTable().getValueAt(selectedRow, 0);
            categoryDAO.delete(name);
            loadData();
            JOptionPane.showMessageDialog(categoryPanel, "Category deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error deleting category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCategory() {
        try {
            String searchTerm = categoryPanel.getSearchTextField().getText();
            List<Category> categories = categoryDAO.searchByName(searchTerm);
            updateTable(categories);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error searching category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData() {
        try {
            List<Category> categories = categoryDAO.getAll();
            updateTable(categories);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Category> categories) {
        DefaultTableModel tableModel = (DefaultTableModel) categoryPanel.getTable().getModel();
        tableModel.setRowCount(0);
        for (Category category : categories) {
            tableModel.addRow(new Object[]{category.getName()});
        }
    }
}