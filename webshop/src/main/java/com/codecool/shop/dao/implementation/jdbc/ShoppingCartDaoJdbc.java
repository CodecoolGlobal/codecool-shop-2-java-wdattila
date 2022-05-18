package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.ShoppingCart;

import javax.sql.DataSource;
import java.util.List;

public class ShoppingCartDaoJdbc implements ShoppingCartDao {
    private DataSource dataSource;
    private ProductDao productDao;

    public ShoppingCartDaoJdbc(DataSource dataSource, ProductDao productDao) {
        this.dataSource = dataSource;
        this.productDao = productDao;
    }

    @Override
    public void add(ShoppingCart shoppingCart) {

    }

    @Override
    public ShoppingCart find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ShoppingCart> getAll() {
        return null;
    }

    @Override
    public List<ShoppingCart> getMultipleById(String ids) {
        return null;
    }
}
