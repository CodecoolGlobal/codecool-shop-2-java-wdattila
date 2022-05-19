package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;

public class OrderDaoJdbc implements OrderDao {
    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO orders(user_id, cart_id, first_name, last_name, phone_number, shipping_address, payment_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getCartId());
            statement.setString(3, order.getName());
            statement.setString(4, order.getName());
            statement.setString(5, order.getPhone());
            statement.setString(6, order.getShippingAddress());
            statement.setString(7, order.getPostAddress());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            order.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}
