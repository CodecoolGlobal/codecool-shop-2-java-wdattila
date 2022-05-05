package com.codecool.shop.service;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;

public class ShoppingCartService {
    private ShoppingCartDao shoppingCartDao;

    public ShoppingCartService() {
        this.shoppingCartDao = ShoppingCartDaoMem.getInstance();
    }
}
