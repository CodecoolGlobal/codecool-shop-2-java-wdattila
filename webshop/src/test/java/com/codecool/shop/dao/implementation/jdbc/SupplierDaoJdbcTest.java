package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    void add() {
    }

    @Test
    @Order(2)
    void find() {
    }

    @Test
    @Order(3)
    void remove() {
    }

    @Test
    @Order(4)
    void getAll() {
    }

    @Test
    @Order(5)
    void getMultipleById() {
    }
}