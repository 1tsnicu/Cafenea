package com.gestiune.view;
import javax.swing.*;
import java.awt.*;
import com.gestiune.controller.*;

public class MainFrame extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(70, 70, 70);  // Dark gray
    private static final Color SECONDARY_COLOR = new Color(100, 100, 100);  // Lighter gray
    
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private CategoryPanel categoryPanel;
    private ProductsPanel productsPanel;
    private BillPanel billPanel;
    private JPanel welcomePanel;
    
    private BillController billController;
    private ProductsController productsController;
    private CategoryController categoryController;
    
    public MainFrame() {
        setTitle("Sistem de Gestiune");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);  // Smaller size
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR);
        menuBar.setBorder(BorderFactory.createEmptyBorder());
        
        JMenu fileMenu = createStyledMenu("Fișier");
        JMenuItem newItem = createStyledMenuItem("Nou");
        JMenuItem saveItem = createStyledMenuItem("Salvare");
        JMenuItem exitItem = createStyledMenuItem("Ieșire");
        
        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu viewMenu = createStyledMenu("Vizualizare");
        JMenuItem categoryItem = createStyledMenuItem("Categorie");
        JMenuItem productsItem = createStyledMenuItem("Produse");
        JMenuItem billItem = createStyledMenuItem("Vanzari");
        
        viewMenu.add(categoryItem);
        viewMenu.addSeparator();
        viewMenu.add(productsItem);
        viewMenu.addSeparator();
        viewMenu.add(billItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        
        setJMenuBar(menuBar);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        categoryPanel = new CategoryPanel();
        productsPanel = new ProductsPanel();
        billPanel = new BillPanel();
        welcomePanel = createWelcomePanel();
        
        mainPanel.add(welcomePanel, "WELCOME");
        mainPanel.add(categoryPanel, "CATEGORIE");
        mainPanel.add(productsPanel, "PRODUSE");
        mainPanel.add(billPanel, "VANZARI");
        
        add(mainPanel);
        
        cardLayout.show(mainPanel, "WELCOME");

        categoryItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "CATEGORIE");
            if (categoryController != null) {
                categoryController.loadData();
            }
        });

        productsItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "PRODUSE");
            if (productsController != null) {
                productsController.loadProducts();
            }
        });
        
        billItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "VANZARI");
            if (billController != null) {
                billController.loadBills();
            }
        });
        
        newItem.addActionListener(e -> handleNewAction());
        saveItem.addActionListener(e -> handleSaveAction());
        exitItem.addActionListener(e -> handleExitAction());
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 222, 179)); // Light beige background

        JLabel welcomeLabel = new JLabel("Bine ați venit la Sistemul de Gestiune al Cafenelei!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>Gestionați categoriile, produsele și vânzările cafenelei dvs. cu ușurință.<br>Utilizați meniul de mai sus pentru a naviga între diferitele secțiuni.</div></html>");
        descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(descriptionLabel, BorderLayout.CENTER);

        return panel;
    }
    
    private JMenu createStyledMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setForeground(Color.BLACK);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return menu;
    }
    
    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setBackground(PRIMARY_COLOR);
        item.setForeground(Color.BLACK);
        item.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item.setBackground(SECONDARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item.setBackground(PRIMARY_COLOR);
            }
        });
        
        return item;
    }
    
    private void handleNewAction() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Doriți să creați un nou document? Datele nesalvate vor fi pierdute.",
            "Confirmare",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Refresh current view
            String currentCard = ((CardLayout)mainPanel.getLayout()).toString();
            switch (currentCard) {
                case "VANZARI":
                    if (billController != null) billController.loadBills();
                    break;

                case "CATEGORIE":
                    if (categoryController != null) categoryController.loadData();
                    break;

                case "PRODUSE":
                    if (productsController != null) productsController.loadProducts();
                    break;
            }
        }
    }
    
    private void handleSaveAction() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            // TODO: Implement save functionality
            JOptionPane.showMessageDialog(
                this,
                "Datele au fost salvate cu succes!",
                "Salvare",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    private void handleExitAction() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Sunteți sigur că doriți să închideți aplicația?",
            "Confirmare ieșire",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
   
    public BillPanel getBillPanel() {
        return billPanel;
    }

    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    public ProductsPanel getProductsPanel() {
        return productsPanel;
    }

    public void setControllers(
            BillController builController,
            ProductsController productsController,
            CategoryController categoryController) {
        this.billController = builController;
        this.productsController = productsController;
        this.categoryController = categoryController;
    }
}

