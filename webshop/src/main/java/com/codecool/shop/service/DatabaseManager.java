package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseManager {
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private ShoppingCartDao shoppingCartDao;

    public DatabaseManager() {
        try {
            setup();
        } catch (SQLException e){
            System.out.println("Couldn't connect to database");
        } catch (IOException e){
            System.out.println("Couldn't read properties file");
        }
    }

    public void setup() throws SQLException, IOException {
        Properties connProps = getConnectionConfig();
        if ("jdbc".equals(connProps.getProperty("dao"))) {
            DataSource dataSource = connect(connProps);
            this.productDao = new ProductDaoJdbc();
            this.productCategoryDao = new ProductCategoryDaoJdbc();
            this.supplierDao = new SupplierDaoJdbc();
            this.shoppingCartDao = new ShoppingCartDaoJdbc();
        } else {
            this.productDao = ProductDaoMem.getInstance();
            this.productCategoryDao = ProductCategoryDaoMem.getInstance();
            this.supplierDao = SupplierDaoMem.getInstance();
            this.shoppingCartDao = ShoppingCartDaoMem.getInstance();
        }
    }

    private DataSource connect(Properties connProps) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv(connProps.getProperty("database"));
        String user = System.getenv(connProps.getProperty("user"));
        String password = System.getenv(connProps.getProperty("password"));

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    private Properties getConnectionConfig() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String connConfigPath = rootPath + "connection.properties";

        Properties connProps = new Properties();
        connProps.load(new FileInputStream(connConfigPath));
        return connProps;
    }
}
