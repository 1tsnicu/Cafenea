-- Crearea bazei de date dacă nu există
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'cafenea')
BEGIN
    CREATE DATABASE cafenea;
END
GO

USE cafenea;
GO

-- Crearea tabelului 'category'
IF OBJECT_ID('category', 'U') IS NULL
BEGIN
    CREATE TABLE category (
        name VARCHAR(255) NOT NULL PRIMARY KEY
    );
END
GO


-- Crearea tabelului 'product'
IF OBJECT_ID('product', 'U') IS NULL
BEGIN
    CREATE TABLE product (
        id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        category_name VARCHAR(255) NOT NULL,
        price DECIMAL(10,2) NOT NULL,
        CONSTRAINT FK_product_category FOREIGN KEY (category_name) REFERENCES category(name)
    );
END
GO

-- Crearea tabelului 'bill'
IF OBJECT_ID('bill', 'U') IS NULL
BEGIN
    CREATE TABLE bill (
        id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
        product_name VARCHAR(255) NOT NULL,
        category_name VARCHAR(255) NOT NULL,
        price DECIMAL(10,2) NOT NULL,
        order_date DATETIME NOT NULL
    );
END
GO

-- Inserarea datelor în 'category'
INSERT INTO category (name) VALUES 
('Cafea'), ('Ceai'), ('Sucuri'), ('Patiserie'), ('Deserturi');

-- Inserarea datelor în 'product'
INSERT INTO product (name, category_name, price) VALUES
('Espresso', 'Cafea', 10.50),
('Cappuccino', 'Cafea', 12.00),
('Latte', 'Cafea', 14.00),
('Ceai Verde', 'Ceai', 8.00),
('Ceai Negru', 'Ceai', 9.00),
('Limonadă', 'Sucuri', 11.50),
('Fresh Portocale', 'Sucuri', 13.00),
('Croissant', 'Patiserie', 7.50),
('Ecler', 'Deserturi', 10.00),
('Tiramisu', 'Deserturi', 15.00),
('Frappe', 'Cafea', 16.00),
('Americano', 'Cafea', 9.50),
('Macchiato', 'Cafea', 13.50),
('Ceai de Mușețel', 'Ceai', 8.50),
('Ceai de Mentă', 'Ceai', 8.50),
('Suc de Mere', 'Sucuri', 9.50),
('Suc de Cireșe', 'Sucuri', 10.00),
('Brioșă', 'Patiserie', 6.50),
('Tartă cu Fructe', 'Deserturi', 12.50),
('Cheesecake', 'Deserturi', 14.50),
('Espresso Dublu', 'Cafea', 12.50),
('Cafea cu Lapte', 'Cafea', 11.50),
('Latte Macchiato', 'Cafea', 15.50),
('Ceai Alb', 'Ceai', 9.50),
('Ceai Rooibos', 'Ceai', 10.50),
('Suc de Struguri', 'Sucuri', 11.00),
('Cornulețe', 'Patiserie', 5.50),
('Muffin cu Ciocolată', 'Patiserie', 8.50),
('Mille-Feuille', 'Deserturi', 16.50);

-- Inserarea datelor în 'bill'
INSERT INTO bill (product_name, category_name, price, order_date) VALUES
('Espresso', 'Cafea', 10.50, GETDATE()),
('Cappuccino', 'Cafea', 12.00, GETDATE()),
('Latte', 'Cafea', 14.00, GETDATE()),
('Ceai Verde', 'Ceai', 8.00, GETDATE()),
('Ceai Negru', 'Ceai', 9.00, GETDATE()),
('Limonadă', 'Sucuri', 11.50, GETDATE()),
('Fresh Portocale', 'Sucuri', 13.00, GETDATE()),
('Croissant', 'Patiserie', 7.50, GETDATE()),
('Ecler', 'Deserturi', 10.00, GETDATE()),
('Tiramisu', 'Deserturi', 15.00, GETDATE()),
('Frappe', 'Cafea', 16.00, GETDATE()),
('Americano', 'Cafea', 9.50, GETDATE()),
('Macchiato', 'Cafea', 13.50, GETDATE()),
('Ceai de Mușețel', 'Ceai', 8.50, GETDATE()),
('Ceai de Mentă', 'Ceai', 8.50, GETDATE()),
('Suc de Mere', 'Sucuri', 9.50, GETDATE()),
('Suc de Cireșe', 'Sucuri', 10.00, GETDATE()),
('Brioșă', 'Patiserie', 6.50, GETDATE()),
('Tartă cu Fructe', 'Deserturi', 12.50, GETDATE()),
('Cheesecake', 'Deserturi', 14.50, GETDATE()),
('Espresso Dublu', 'Cafea', 12.50, GETDATE()),
('Cafea cu Lapte', 'Cafea', 11.50, GETDATE()),
('Latte Macchiato', 'Cafea', 15.50, GETDATE()),
('Ceai Alb', 'Ceai', 9.50, GETDATE()),
('Ceai Rooibos', 'Ceai', 10.50, GETDATE()),
('Suc de Struguri', 'Sucuri', 11.00, GETDATE()),
('Cornulețe', 'Patiserie', 5.50, GETDATE()),
('Muffin cu Ciocolată', 'Patiserie', 8.50, GETDATE()),
('Mille-Feuille', 'Deserturi', 16.50, GETDATE());
