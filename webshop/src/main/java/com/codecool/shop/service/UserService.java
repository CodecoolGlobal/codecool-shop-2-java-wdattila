package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user){
        userDao.add(user);
    }

    public User getUserById(int id){
        return userDao.find(id);
    }

    public User getUserByNameOrEmail(String name, String email){
        return userDao.find(name, email);
    }
}
