package com.codecool.shop.dao.implementation.jdbc;

import org.hsqldb.jdbc.JDBCCommonDataSource;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoJdbcTest {
    private static void initDatabase() throws SQLException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
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
    void getCategoryByIndex() {
    }

    @Test
    void add() {
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