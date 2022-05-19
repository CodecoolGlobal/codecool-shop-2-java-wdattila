package com.codecool.shop.service;

import com.codecool.shop.controller.LoginController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.jdbc.*;
import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.mem.ShoppingCartDaoMem;
import com.codecool.shop.dao.implementation.mem.SupplierDaoMem;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private UserCartDao userCartDao;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

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
            this.productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
            this.supplierDao = new SupplierDaoJdbc(dataSource);
            this.shoppingCartDao = new ShoppingCartDaoJdbc(dataSource, productDao);
            this.productDao = new ProductDaoJdbc(dataSource, this.productCategoryDao, this.supplierDao);
            this.userDao = new UserDaoJdbc(dataSource);
            this.userCartDao = new UserCartDaoJdbc(dataSource);
        } else {
            this.productDao = ProductDaoMem.getInstance();
            this.productCategoryDao = ProductCategoryDaoMem.getInstance();
            this.supplierDao = SupplierDaoMem.getInstance();
            this.shoppingCartDao = ShoppingCartDaoMem.getInstance();
        }
    }

    private DataSource connect(Properties connProps) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = connProps.getProperty("database");
        String user = System.getProperty(connProps.getProperty("user"));
        String password = System.getProperty(connProps.getProperty("password"));

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    private Properties getConnectionConfig() throws IOException {
        Properties connProps = new Properties();
        connProps.load(getClass().getClassLoader().getResourceAsStream("connection.properties"));
        return connProps;
    }

    public ProductService getProductService(){
        return new ProductService(productDao, productCategoryDao, supplierDao);
    }

    public UserService getUserService(){
        return new UserService(userDao);
    }

    public ShoppingCartService getShoppingCartService(){
        return new ShoppingCartService(shoppingCartDao, productDao, userCartDao);
    }
}
