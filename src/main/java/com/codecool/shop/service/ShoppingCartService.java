package com.codecool.shop.service;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;

import java.util.List;
import java.util.Map;

public class ShoppingCartService {
    private ShoppingCartDao shoppingCartDao;
    private ProductDao productDao;

    public ShoppingCartService() {
        shoppingCartDao = ShoppingCartDaoMem.getInstance();
        productDao = ProductDaoMem.getInstance();
    }

    public void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartDao.add(shoppingCart);
    }

    public void addProductToCartById(int cartId, int productId, int quantity){
        shoppingCartDao.find(cartId).addProduct(productDao.find(productId), quantity);
    }

    public Map<Product, Integer> getCartDataById(int id){
        return shoppingCartDao.find(id).getProducts();
    }
}
