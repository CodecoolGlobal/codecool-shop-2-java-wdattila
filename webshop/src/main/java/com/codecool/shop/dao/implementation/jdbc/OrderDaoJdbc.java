package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT user_id, cart_id, first_name, last_name, phone_number, shipping_address, payment_address FROM orders " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                int userId = rs.getInt("user_id");
                int cartId = rs.getInt("cart_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phone = rs.getString("phone_number");
                String shippingAddress = rs.getString("shipping_address");
                String postAddress = rs.getString("payment_address");
                Order order = new Order("", userId, cartId, shippingAddress, postAddress, phone);
                order.setId(id);
                return order;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }
}
