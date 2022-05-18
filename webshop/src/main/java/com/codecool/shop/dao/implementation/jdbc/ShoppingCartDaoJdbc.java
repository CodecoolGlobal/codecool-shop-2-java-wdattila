package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.ShoppingCart;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDaoJdbc implements ShoppingCartDao {
    private DataSource dataSource;
    private ProductDao productDao;

    public ShoppingCartDaoJdbc(DataSource dataSource, ProductDao productDao) {
        this.dataSource = dataSource;
        this.productDao = productDao;
    }

    @Override
    public void add(ShoppingCart shoppingCart) {

    }

    @Override
    public ShoppingCart find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product_id, quantity FROM Cart_content " +
                    "WHERE cart_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            ShoppingCart shoppingCart = new ShoppingCart();
            while(rs.next()){
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                Product product = productDao.find(productId);
                product.setId(productId);
                shoppingCart.addProduct(product, quantity);
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ShoppingCart> getAll() {
        return null;
    }

    @Override
    public List<ShoppingCart> getMultipleById(String ids) {
        return null;
    }
}
