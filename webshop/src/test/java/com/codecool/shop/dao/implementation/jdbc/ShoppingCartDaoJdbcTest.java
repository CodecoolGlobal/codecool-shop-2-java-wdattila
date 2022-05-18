package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartDaoJdbcTest {
    private static ShoppingCartDao shoppingCartDao;
    private static void initDatabase() throws SQLException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Carts( " +
                    "    id SERIAL NOT NULL PRIMARY KEY, " +
                    "    user_id int NOT NULL " +
                    ");");
            connection.commit();
            statement.executeUpdate("INSERT INTO Carts(user_id) VALUES (1);");
            connection.commit();

            statement.execute("CREATE TABLE Cart_content( " +
                    "    product_id int NOT NULL, " +
                    "    quantity int NOT NULL, " +
                    "    cart_id int NOT NULL " +
                    ");");
            connection.commit();
            statement.executeUpdate("INSERT INTO cart_content(product_id, quantity, cart_id) VALUES (5, 1, 1);");
            statement.executeUpdate("INSERT INTO Cart_content(product_id, quantity, cart_id) VALUES (1, 2, 1);");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestProductDao implements ProductDao{
        @Override
        public void add(Product product) {

        }

        @Override
        public Product find(int id) {
            return null;
        }

        @Override
        public void remove(int id) {

        }

        @Override
        public List<Product> getAll() {
            return null;
        }

        @Override
        public List<Product> getBy(Supplier supplier) {
            return null;
        }

        @Override
        public List<Product> getBy(ProductCategory productCategory) {
            return null;
        }

        @Override
        public List<Product> getMultipleByCategories(List<ProductCategory> productCategories) {
            return null;
        }

        @Override
        public List<Product> getMultipleBySuppliers(List<Supplier> suppliers) {
            return null;
        }

        @Override
        public List<Product> getMultipleByCategoriesSuppliers(List<ProductCategory> productCategories, List<Supplier> suppliers) {
            return null;
        }

        @Override
        public List<Product> getMultipleById(String ids) {
            return null;
        }
    }

    @BeforeAll
    public static void init() throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // initialize database
        initDatabase();
        shoppingCartDao = new ShoppingCartDaoJdbc(JdbcTestUtil.getSource(), new TestProductDao());
    }

    @AfterAll
    public static void destroy() throws SQLException, ClassNotFoundException, IOException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE Carts");
            connection.commit();
            statement.executeUpdate("DROP TABLE Cart_content");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void add() {
    }

    @Test
    void find() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getMultipleById() {
    }
}