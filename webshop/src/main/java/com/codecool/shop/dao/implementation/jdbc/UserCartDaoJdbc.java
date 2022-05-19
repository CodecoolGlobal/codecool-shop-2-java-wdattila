package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.UserCartDao;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserCartDaoJdbc implements UserCartDao {
    private DataSource dataSource;

    public UserCartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(User user, ShoppingCart shoppingCart) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO carts (user_id) " +
                    "VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            shoppingCart.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}
