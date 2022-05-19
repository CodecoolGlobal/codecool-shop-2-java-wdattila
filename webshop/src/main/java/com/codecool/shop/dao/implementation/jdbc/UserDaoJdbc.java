package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.service.HashManager;

import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class UserDaoJdbc implements UserDao {

    private DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}
