DROP TABLE IF EXISTS Categories;
CREATE TABLE Categories(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    department varchar NOT NULL,
    description varchar
);

DROP TABLE IF EXISTS Suppliers;
CREATE TABLE Suppliers(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar
);

DROP TABLE IF EXISTS Products CASCADE;
CREATE TABLE Products(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    price int NOT NULL,
    currency varchar NOT NULL,
    description varchar,
    category_id int NOT NULL,
    supplier_id int NOT NULL
);

DROP TABLE IF EXISTS Users CASCADE ;
CREATE TABLE Users(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    password varchar NOT NULL
);

DROP TABLE IF EXISTS Carts CASCADE;
CREATE TABLE Carts(
    id SERIAL NOT NULL PRIMARY KEY,
    user_id int NOT NULL
);

DROP TABLE IF EXISTS Orders;
CREATE TABLE Orders(
    id SERIAL NOT NULL PRIMARY KEY,
    user_id int NOT NULL,
    cart_id int NOT NULL,
    First_name varchar NOT NULL,
    Last_name varchar NOT NULL,
    phone_number varchar NOT NULL,
    shipping_address varchar NOT NULL,
    payment_address varchar NOT NULL
);

DROP TABLE IF EXISTS Cart_content;
CREATE TABLE Cart_content(
    product_id int NOT NULL,
    quantity int NOT NULL,
    user_id int NOT NULL
);


ALTER TABLE ONLY Products
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES Categories(id);
ALTER TABLE ONLY Products
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES Suppliers(id);
ALTER TABLE ONLY Carts
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE ONLY Orders
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE ONLY Orders
    ADD CONSTRAINT fk_cart_id FOREIGN KEY (cart_id) REFERENCES Carts(id);
ALTER TABLE ONLY Cart_content
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES Products(id);
ALTER TABLE ONLY Cart_content
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(id);