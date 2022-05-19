package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.ProductCategory;

public interface OrderDao {
    void add(Order order);
    Order find(int id);
    void remove(int id);

}
