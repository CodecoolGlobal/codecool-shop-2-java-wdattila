DROP TABLE IF EXISTS Categories CASCADE;
CREATE TABLE Categories(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    department varchar NOT NULL,
    description varchar
);

DROP TABLE IF EXISTS Suppliers CASCADE;
CREATE TABLE Suppliers(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar
);

DROP TABLE IF EXISTS Products CASCADE;
CREATE TABLE Products(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    price decimal NOT NULL,
    currency varchar NOT NULL,
    description varchar,
    category_id int NOT NULL,
    supplier_id int NOT NULL
);

DROP TABLE IF EXISTS Users CASCADE ;
CREATE TABLE Users(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    password varchar NOT NULL,
    email varchar NOT NULL,
    salt varchar NOT NULL
);

DROP TABLE IF EXISTS Carts CASCADE;
CREATE TABLE Carts(
    id SERIAL NOT NULL PRIMARY KEY,
    user_id int NOT NULL
);

DROP TABLE IF EXISTS Orders CASCADE;
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

DROP TABLE IF EXISTS Cart_content CASCADE;
CREATE TABLE Cart_content(
    product_id int NOT NULL,
    quantity int NOT NULL,
    cart_id int NOT NULL
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

INSERT INTO Categories(name, department, description) VALUES ('RPG','Video Game', 'Role playing video game type');
INSERT INTO Categories(name, department, description) VALUES ('Platformer','Video Game', 'Jumping and puzzle solving video game type.');
INSERT INTO Categories(name, department, description) VALUES ('Action','Video Game', 'Action type video game.');
INSERT INTO Categories(name, department, description) VALUES ('Strategy','Video Game', 'Strategic type video game.');

INSERT INTO Suppliers(name, description) VALUES ('Nintendont', 'Digital content and services');
INSERT INTO Suppliers(name, description) VALUES ('Gamma Freak', 'RPG Video game developer');
INSERT INTO Suppliers(name, description) VALUES ('Camco', 'Video game developer');
INSERT INTO Suppliers(name, description) VALUES ('Smegma', 'Platformer Video game developer');

INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Chinpokomon: Myrrh', 29.9, 'EUR', 'he next addition of the famous Chinpokomon series. The Myrrh edition contains the legendary chinpokomon Donkeytron.', 1, 2);
INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Chinpokomon: Platinum', 29.9, 'EUR', 'The next addition of the famous Chinpokomon series. The Platinum edition contains the legendary chinpokomon Lambtron.', 1, 2);
INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Muckman', 15, 'EUR', 'The Original first addition of the Muckman video game series', 3, 3);
INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Sanic', 26.9, 'EUR', 'How fast? Sanic fast. Be faster than light with Sanic the hedgehog', 2, 4);
INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Supra Mayro', 59.99, 'EUR', 'The best seller 90s super classic, Supra Mayro is finally avaliable on Smoke', 2, 1);
INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Somari', 30, 'EUR', 'The fastest man alive is here. Test your reflexes in a fast paced environment with Somari on Smoke', 2, 1);

INSERT INTO Users(name, password, email, salt) VALUES ('test','test','test@test.com','[-85, 0, 120, 88, -104, -41, -36, -11, 81, -117, -1, -75, 2, -98, -51, -88]');

INSERT INTO Carts(user_id) VALUES (1);

INSERT INTO Cart_content(product_id, quantity, cart_id) VALUES (1, 2, 1);
INSERT INTO cart_content(product_id, quantity, cart_id) VALUES (5, 1, 1);