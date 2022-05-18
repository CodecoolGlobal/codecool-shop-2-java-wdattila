package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupplierDaoJdbcTest {

    private static SupplierDao supplierDao;
    private static void initDatabase() throws SQLException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Suppliers(" +
                    "    id SERIAL NOT NULL PRIMARY KEY," +
                    "    name varchar NOT NULL," +
                    "    description varchar" +
                    ")");
            connection.commit();
            statement.executeUpdate("INSERT INTO Suppliers(name, description) VALUES ('Gamma Freak', 'RPG Video game developer')");
            statement.executeUpdate("INSERT INTO Suppliers(name, description) VALUES ('Nintendont', 'Digital content and services');");
            statement.executeUpdate("INSERT INTO Suppliers(name, description) VALUES ('Smegma', 'Platformer Video game developer')");
            statement.executeUpdate("INSERT INTO Suppliers(name, description) VALUES ('Camco', 'Video game developer')");
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
        supplierDao = new SupplierDaoJdbc(JdbcTestUtil.getSource());
    }

    @AfterAll
    public static void destroy() throws SQLException, ClassNotFoundException, IOException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE Suppliers");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void getSupplierByIndex() {
        Supplier supplier = supplierDao.find(1);
        assertEquals("Gamma Freak", supplier.getName());
        assertEquals("RPG Video game developer", supplier.getDescription());
    }

    @Test
    @Order(2)
    void addSupplier() {
        Supplier supplier = new Supplier("Test", "test");
        supplierDao.add(supplier);
        Supplier testSupplier = supplierDao.find(supplier.getId());

        assertEquals("Test", testSupplier.getName());
        assertEquals("test", testSupplier.getDescription());
    }

    @Test
    @Order(3)
    void removeSupplierById() {
        supplierDao.remove(1);

        assertNull(supplierDao.find(1));
    }

    @Test
    @Order(4)
    void gettingAllSuppliersMatchNumberOfThem() {
        List<Supplier> suppliers = supplierDao.getAll();

        assertEquals(4, suppliers.size());
    }

    @Test
    @Order(5)
    void getMultipleById() {
    }
}