package com.gestiune.controller;

import com.gestiune.model.entities.Product;
import com.gestiune.model.entities.Category;
import com.gestiune.model.dao.impl.ProductsDAOImpl;
import com.gestiune.model.dao.interfaces.ProductsDAO;
import com.gestiune.view.ProductsPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductsController {
    private final ProductsPanel productsPanel;
    private final ProductsDAO productsDAO;

    public ProductsController(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;
        this.productsDAO = new ProductsDAOImpl();

        this.productsPanel.getAddButton().addActionListener(e -> addProduct());
        this.productsPanel.getUpdateButton().addActionListener(e -> updateProduct());
        this.productsPanel.getDeleteButton().addActionListener(e -> deleteProduct());
        this.productsPanel.getSearchButton().addActionListener(e -> searchProduct());
    }

    private void addProduct() {
        try {
            String name = productsPanel.getNameField().getText();
            String categoryName = productsPanel.getCategoryNameField().getText();
            double price = Double.parseDouble(productsPanel.getPriceField().getText());

            Category category = new Category();
            category.setName(categoryName);

            Product product = new Product();
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);

            productsDAO.insert(product);
            loadProducts();
            JOptionPane.showMessageDialog(productsPanel, "Produs adăugat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productsPanel, "Prețul trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productsPanel, "Eroare la adăugarea produsului: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        try {
            int selectedRow = productsPanel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(productsPanel, "Vă rugăm să selectați un produs din tabel pentru modificare.", "Avertizare", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(productsPanel.getIdField().getText());
            String name = productsPanel.getNameField().getText();
            String categoryName = productsPanel.getCategoryNameField().getText();
            double price = Double.parseDouble(productsPanel.getPriceField().getText());

            Category category = new Category();
            category.setName(categoryName);

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);

            productsDAO.update(product);
            loadProducts();
            JOptionPane.showMessageDialog(productsPanel, "Produs actualizat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productsPanel, "Prețul trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productsPanel, "Eroare la actualizarea produsului: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(productsPanel.getIdField().getText());
            int confirm = JOptionPane.showConfirmDialog(productsPanel, "Sigur doriți să ștergeți acest produs?", "Confirmare ștergere", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                productsDAO.delete(id);
                loadProducts();
                JOptionPane.showMessageDialog(productsPanel, "Produs șters cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productsPanel, "Eroare la ștergerea produsului: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchProduct() {
        try {
            String searchTerm = productsPanel.getSearchTextField().getText();
            String searchType = (String) productsPanel.getIdCombo().getSelectedItem();

            List<Product> products;
            switch (searchType) {
                case "Nume":
                    products = productsDAO.searchByName(searchTerm);
                    break;
                case "Categorie":
                    products = productsDAO.searchByCategory(searchTerm);
                    break;
                case "Pret":
                    products = productsDAO.searchByPrice(Double.parseDouble(searchTerm));
                    break;
                default:
                    products = productsDAO.getAll();
            }

            if (products == null || products.isEmpty()) {
                JOptionPane.showMessageDialog(productsPanel, "Nu s-au găsit rezultate pentru căutarea efectuată.", "Informație", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            updateTable(products);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productsPanel, "Vă rugăm introduceți un preț valid pentru căutarea după preț.", "Eroare", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productsPanel, "Eroare la căutarea produsului: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadProducts() {
        try {
            List<Product> products = productsDAO.getAll();
            updateTable(products);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productsPanel, "Eroare la încărcarea datelor: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Product> products) {
        DefaultTableModel tableModel = (DefaultTableModel) productsPanel.getTable().getModel();
        tableModel.setRowCount(0);

        for (Product product : products) {
            tableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice()
            });
        }
    }
}