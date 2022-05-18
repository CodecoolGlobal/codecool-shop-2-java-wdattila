package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {
    private DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO Suppliers (name, description) " +
                    "VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            supplier.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description FROM Suppliers " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = new Supplier(name, description);
                supplier.setId(id);
                return supplier;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM Suppliers " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM Suppliers ";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.executeQuery();
            List<Supplier> suppliers = new ArrayList<>();
            while(rs.next()){
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = new Supplier(name, description);
                supplier.setId(rs.getInt("id"));
                suppliers.add(supplier);
            }

            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Supplier> getMultipleById(String ids) {
        try (Connection conn = dataSource.getConnection()) {
            if(ids.equals("")){
                return new ArrayList<>();
            }
            String[] idList = ids.split(",");
            String sql = "SELECT id, name, description FROM Suppliers " +
                    "WHERE id IN (" +
                    String.join(",", Collections.nCopies(idList.length, "?")) +
                    ")";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i=1; i<=idList.length; i++) {
                statement.setInt(i, Integer.parseInt(idList[i-1]));
            }
            ResultSet rs = statement.executeQuery();
            List<Supplier> suppliers = new ArrayList<>();
            while(rs.next()){
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = new Supplier(name, description);
                supplier.setId(rs.getInt("id"));
                suppliers.add(supplier);
            }

            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
