package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.ShoppingCart;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShoppingCartDaoJdbc implements ShoppingCartDao {
    private DataSource dataSource;
    private ProductDao productDao;

    public ShoppingCartDaoJdbc(DataSource dataSource, ProductDao productDao) {
        this.dataSource = dataSource;
        this.productDao = productDao;
    }

    @Override
    public void add(ShoppingCart shoppingCart) {
        try (Connection conn = dataSource.getConnection()) {
            for (Map.Entry<Product, Integer> productSet: shoppingCart.getProducts().entrySet()) {
                String sql = "INSERT INTO Cart_content (product_id, quantity, cart_id) " +
                        "VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, productSet.getKey().getId());
                statement.setInt(2, productSet.getValue());
                statement.setInt(3, shoppingCart.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM Cart_content " +
                    "WHERE cart_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, user_id FROM Carts ";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.executeQuery();
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            while(rs.next()){
                int cartId = rs.getInt("id");
                shoppingCarts.add(find(cartId));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShoppingCart> getMultipleById(String ids) {
        try (Connection conn = dataSource.getConnection()) {
            if(ids.equals("")){
                return null;
            }
            String[] idList = ids.split(",");
            String sql = "SELECT id, user_id FROM Carts " +
                    "WHERE id IN (" +
                    String.join(",", Collections.nCopies(idList.length, "?")) +
                    ")";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i=1; i<=idList.length; i++) {
                statement.setInt(i, Integer.parseInt(idList[i-1]));
            }
            ResultSet rs = statement.executeQuery();
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            while(rs.next()){
                int cartId = rs.getInt("id");
                shoppingCarts.add(find(cartId));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
