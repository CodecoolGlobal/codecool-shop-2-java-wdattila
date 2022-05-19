package com.codecool.shop.dao;

import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import java.util.List;

public interface UserCartDao {
    void add(User user, ShoppingCart shoppingCart);
    int findCartId(int userId);
    void remove(int id);
}
