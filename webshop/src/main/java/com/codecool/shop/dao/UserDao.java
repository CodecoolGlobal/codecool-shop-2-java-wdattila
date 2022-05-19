package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao {

    void add(User user);
    User find(int id);
    User find(String name, String email);
    void remove(int id);
}
