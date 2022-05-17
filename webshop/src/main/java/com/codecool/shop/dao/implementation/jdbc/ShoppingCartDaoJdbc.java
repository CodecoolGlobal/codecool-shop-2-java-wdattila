package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.ShoppingCart;

import javax.sql.DataSource;
import java.util.List;

public class ShoppingCartDaoJdbc implements ShoppingCartDao {
    private DataSource dataSource;

    public ShoppingCartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
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
