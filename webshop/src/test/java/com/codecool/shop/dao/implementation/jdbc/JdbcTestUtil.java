package com.codecool.shop.dao.implementation.jdbc;

import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTestUtil {
    static DataSource getSource() throws SQLException {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:productCategories");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }
}
