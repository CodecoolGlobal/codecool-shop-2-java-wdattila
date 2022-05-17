package com.codecool.shop.service;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.mem.ShoppingCartDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.Map;

public class ShoppingCartService {
    private ShoppingCartDao shoppingCartDao;
    private ProductDao productDao;

    public ShoppingCartService(ShoppingCartDao shoppingCartDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.productDao = productDao;
    }

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
        ShoppingCart cart = shoppingCartDao.find(id);
        return cart == null ? null : cart.getProducts();
    }

    public BigDecimal getCartTotalPriceById(int id){
        ShoppingCart cart = shoppingCartDao.find(id);
        return cart == null ? BigDecimal.ZERO : cart.getTotalPrice();
    }

    public String getCartTotalPriceStringById(int id){
        ShoppingCart cart = shoppingCartDao.find(id);
        return cart == null ? "" : cart.getTotalPriceString();
    }
}
