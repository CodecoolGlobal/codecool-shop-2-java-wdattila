package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class ProductDaoJdbcTest {
    private static ProductDao productDao;
    private static void initDatabase() throws SQLException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Products( " +
                    "    id SERIAL NOT NULL PRIMARY KEY, " +
                    "    name varchar NOT NULL, " +
                    "    price int NOT NULL, " +
                    "    currency varchar NOT NULL, " +
                    "    description varchar, " +
                    "    category_id int NOT NULL, " +
                    "    supplier_id int NOT NULL " +
                    ")");
            connection.commit();
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Chinpokomon: Myrrh', 29.9, 'EUR', 'he next addition of the famous Chinpokomon series. The Myrrh edition contains the legendary chinpokomon Donkeytron.', 1, 2);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Chinpokomon: Platinum', 29.9, 'EUR', 'The next addition of the famous Chinpokomon series. The Platinum edition contains the legendary chinpokomon Lambtron.', 1, 2);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Muckman', 15, 'EUR', 'The Original first addition of the Muckman video game series', 3, 3);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Sanic', 26.9, 'EUR', 'How fast? Sanic fast. Be faster than light with Sanic the hedgehog', 2, 4);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Supra Mayro', 59.99, 'EUR', 'The best seller 90s super classic, Supra Mayro is finally avaliable on Smoke', 2, 1);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Somari', 30, 'EUR', 'The fastest man alive is here. Test your reflexes in a fast paced environment with Somari on Smoke', 2, 1);");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestProductCategoryDao implements ProductCategoryDao{

        @Override
        public void add(ProductCategory category) {

        }

        @Override
        public ProductCategory find(int id) {
            return null;
        }

        @Override
        public void remove(int id) {

        }

        @Override
        public List<ProductCategory> getAll() {
            return null;
        }

        @Override
        public List<ProductCategory> getMultipleById(String ids) {
            return null;
        }
    }

    private static class TestSupplierDao implements SupplierDao {

        @Override
        public void add(Supplier supplier) {

        }

        @Override
        public Supplier find(int id) {
            return null;
        }

        @Override
        public void remove(int id) {

        }

        @Override
        public List<Supplier> getAll() {
            return null;
        }

        @Override
        public List<Supplier> getMultipleById(String ids) {
            return null;
        }
    }

    @BeforeAll
    public static void init() throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // initialize database
        initDatabase();
        productDao = new ProductDaoJdbc(JdbcTestUtil.getSource(), new TestProductCategoryDao(), new TestSupplierDao());
    }

    @AfterAll
    public static void destroy() throws SQLException, ClassNotFoundException, IOException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE Products");
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
    void getBy() {
    }

    @Test
    void testGetBy() {
    }

    @Test
    void getMultipleByCategories() {
    }

    @Test
    void getMultipleBySuppliers() {
    }

    @Test
    void getMultipleByCategoriesSuppliers() {
    }

    @Test
    void getMultipleById() {
    }
}