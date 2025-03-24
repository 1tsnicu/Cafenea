package com.gestiune.controller;

import com.gestiune.model.entities.Bill;
import com.gestiune.model.dao.impl.BillDAOImpl;
import com.gestiune.view.BillPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillController {
    private final BillPanel billPanel;
    private final BillDAOImpl billDAO;
    private final SimpleDateFormat dateFormat;

    public BillController(BillPanel billPanel) {
        this.billPanel = billPanel;
        this.billDAO = new BillDAOImpl();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.billPanel.getAddButton().addActionListener(e -> addBill());
        this.billPanel.getUpdateButton().addActionListener(e -> updateBill());
        this.billPanel.getDeleteButton().addActionListener(e -> deleteBill());
        this.billPanel.getSearchButton().addActionListener(e -> searchBill());

        this.billPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadBillDetails();
            }
        });

        loadBills();
    }

    private void loadBillDetails() {
        int selectedRow = billPanel.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) billPanel.getTable().getModel();
            billPanel.getIdField().setText(model.getValueAt(selectedRow, 0).toString());
            billPanel.getProductNameField().setText((String) model.getValueAt(selectedRow, 1));
            billPanel.getCategoryNameField().setText((String) model.getValueAt(selectedRow, 2));
            billPanel.getPriceField().setText(model.getValueAt(selectedRow, 3).toString());
            billPanel.getOrderDateField().setText((String) model.getValueAt(selectedRow, 4));
        }
    }

    private void addBill() {
        try {
            Bill bill = new Bill();
            bill.setId(Integer.parseInt(billPanel.getIdField().getText()));
            bill.setProductName(billPanel.getProductNameField().getText());
            bill.setCategoryName(billPanel.getCategoryNameField().getText());
            bill.setPrice(Double.parseDouble(billPanel.getPriceField().getText()));
            bill.setOrderDate(dateFormat.parse(billPanel.getOrderDateField().getText()));

            if (validateBill(bill)) {
                billDAO.insert(bill);
                loadBills();
                clearForm();
                JOptionPane.showMessageDialog(billPanel, "Bill added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(billPanel, "Please fill in all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(billPanel, "Error adding bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBill() {
        try {
            int selectedRow = billPanel.getTable().getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(billPanel, "Please select a bill to update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = (DefaultTableModel) billPanel.getTable().getModel();
            Integer id = (Integer) model.getValueAt(selectedRow, 0);

            Bill bill = billDAO.getById(id);
            if (bill != null) {
                bill.setProductName(billPanel.getProductNameField().getText());
                bill.setCategoryName(billPanel.getCategoryNameField().getText());
                bill.setPrice(Double.parseDouble(billPanel.getPriceField().getText()));
                bill.setOrderDate(dateFormat.parse(billPanel.getOrderDateField().getText()));

                if (validateBill(bill)) {
                    billDAO.update(bill);
                    loadBills();
                    clearForm();
                    JOptionPane.showMessageDialog(billPanel, "Bill updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(billPanel, "Please fill in all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(billPanel, "Error updating bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBill() {
        try {
            int selectedRow = billPanel.getTable().getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(billPanel, "Please select a bill to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = (DefaultTableModel) billPanel.getTable().getModel();
            Integer id = (Integer) model.getValueAt(selectedRow, 0);

            int confirmation = JOptionPane.showConfirmDialog(billPanel, 
                "Are you sure you want to delete this bill?", 
                "Delete Confirmation", 
                JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                billDAO.delete(id);
                loadBills();
                clearForm();
                JOptionPane.showMessageDialog(billPanel, "Bill deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(billPanel, "Error deleting bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBill() {
        try {
            String searchText = billPanel.getSearchTextField().getText();
            List<Bill> bills;
            switch (billPanel.getIdCombo().getSelectedItem().toString()) {
                case "Id":
                    bills = List.of(billDAO.getById(Integer.parseInt(searchText)));
                    break;
                case "Nume":
                    bills = billDAO.searchByProductName(searchText);
                    break;
                case "Categorie":
                    bills = billDAO.searchByCategoryName(searchText);
                    break;
                case "Pret":
                    bills = billDAO.searchByPriceRange(Double.parseDouble(searchText));
                    break;
                case "Data":
                    bills = billDAO.searchByOrderDateRange(dateFormat.parse(searchText));
                    break;
                default:
                    bills = billDAO.getAll();
            }
            updateTable(bills);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(billPanel, "Error searching bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadBills() {
        try {
            List<Bill> bills = billDAO.getAll();
            updateTable(bills);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(billPanel, "Error loading bills: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Bill> bills) {
        DefaultTableModel tableModel = (DefaultTableModel) billPanel.getTable().getModel();
        tableModel.setRowCount(0);

        for (Bill bill : bills) {
            tableModel.addRow(new Object[]{
                bill.getId(),
                bill.getProductName(),
                bill.getCategoryName(),
                bill.getPrice(),
                dateFormat.format(bill.getOrderDate())
            });
        }
    }

    private void clearForm() {
        billPanel.getIdField().setText("");
        billPanel.getProductNameField().setText("");
        billPanel.getCategoryNameField().setText("");
        billPanel.getPriceField().setText("");
        billPanel.getOrderDateField().setText("");
        billPanel.getTable().clearSelection();
    }

    private boolean validateBill(Bill bill) {
        if (bill == null) return false;
        if (bill.getProductName() == null || bill.getProductName().trim().isEmpty()) return false;
        if (bill.getCategoryName() == null || bill.getCategoryName().trim().isEmpty()) return false;
        if (bill.getPrice() == null) return false;
        if (bill.getOrderDate() == null) return false;
        return true;
    }
}