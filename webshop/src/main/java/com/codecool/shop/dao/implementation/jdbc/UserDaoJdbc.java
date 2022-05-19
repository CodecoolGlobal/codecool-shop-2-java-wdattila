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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO Users (name, password, email, salt) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, HashManager.hashToStringMatrix(user.getPassword()));
            statement.setString(3, user.getEmail());
            statement.setString(4, HashManager.hashToStringMatrix(user.getSalt()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, password, email, salt FROM Users " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                byte[] password = HashManager.stringMatrixToHash(rs.getString("password"));
                String email = rs.getString("email");
                byte[] salt = HashManager.stringMatrixToHash(rs.getString("salt"));
                User user = new User(name, email, password, salt);
                user.setId(id);
                return user;
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User find(String name, String email) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, password, email, salt FROM Users " +
                    "WHERE name = ? OR email = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                byte[] password = HashManager.stringMatrixToHash(rs.getString("password"));
                byte[] salt = HashManager.stringMatrixToHash(rs.getString("salt"));
                User user = new User(name, email, password, salt);
                user.setId(id);
                return user;
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "DELETE FROM users " +
                        "WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
