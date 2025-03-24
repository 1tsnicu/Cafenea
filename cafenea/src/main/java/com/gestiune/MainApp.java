package com.gestiune;

import com.gestiune.controller.*;
import com.gestiune.view.*;
import javax.swing.*;

public class MainApp {
    private MainFrame mainFrame;
    private BillController billController;
    private ProductsController productsController;
    private CategoryController categoryController;

    public MainApp() {
        initializeUI();
        initializeControllers();
    }

    private void initializeUI() {
        mainFrame = new MainFrame();
        mainFrame.setSize(1200, 800);
        mainFrame.setLocationRelativeTo(null);
    }

    private void initializeControllers() {
        billController = new BillController(mainFrame.getBillPanel());
        productsController = new ProductsController(mainFrame.getProductsPanel());
        categoryController = new CategoryController(mainFrame.getCategoryPanel());

        mainFrame.setControllers(
            billController,
            productsController,
            categoryController
        );
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.show();
        });
    }
}