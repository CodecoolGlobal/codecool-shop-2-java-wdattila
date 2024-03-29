package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.hsqldb.jdbc.JDBCCommonDataSource;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryDaoJdbcTest {
    private static ProductCategoryDao productCategoryDao;
    private static void initDatabase() throws SQLException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Categories(" +
                    "    id SERIAL NOT NULL PRIMARY KEY," +
                    "    name varchar NOT NULL," +
                    "    department varchar NOT NULL," +
                    "    description varchar" +
                    ")");
            connection.commit();
            statement.executeUpdate("INSERT INTO Categories(id, name, department, description) VALUES (1, 'RPG','Video Game', 'Role playing video game type')");
            statement.executeUpdate("INSERT INTO Categories(id, name, department, description) VALUES (2, 'Platformer','Video Game', 'Jumping and puzzle solving video game type.')");
            statement.executeUpdate("INSERT INTO Categories(id, name, department, description) VALUES (3, 'Action','Video Game', 'Action type video game.')");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void init() throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // initialize database
        initDatabase();
        productCategoryDao = new ProductCategoryDaoJdbc(JdbcTestUtil.getSource());
    }

    @AfterAll
    public static void destroy() throws SQLException, ClassNotFoundException, IOException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE Categories");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void getCategoryByIndex() {
        ProductCategory productCategory = productCategoryDao.find(1);
        assertEquals(productCategory.getDepartment(), "Video Game");
        assertEquals(productCategory.getName(), "RPG");
    }

    @Test
    @Order(2)
    void getNonExistentCategoryByIndex() {
        ProductCategory productCategory = productCategoryDao.find(4);
        assertNull(productCategory);
    }

    @Test
    @Order(3)
    void addCategory() {
        ProductCategory productCategory = new ProductCategory("Test", "test", "test");
        productCategoryDao.add(productCategory);
        ProductCategory testCategory = productCategoryDao.find(4);

        assertEquals(productCategory.getName(), testCategory.getName());
        assertEquals(productCategory.getDepartment(), testCategory.getDepartment());
        assertEquals(productCategory.getDescription(), testCategory.getDescription());
    }

    @Test
    @Order(4)
    void removeCategoryById() {
        productCategoryDao.remove(3);

        assertNull(productCategoryDao.find(3));
    }

    @Test
    @Order(5)
    void gettingAllCategoriesMatchNumberOfThem() {
        List<ProductCategory> productCategories = productCategoryDao.getAll();

        assertEquals(3, productCategories.size());
    }

    @Test
    @Order(6)
    void getMultipleCategoriesWithSpecifiedIds() {
        List<ProductCategory> productCategories = productCategoryDao.getMultipleById("1,2");

        assertEquals(2, productCategories.size());
    }
}