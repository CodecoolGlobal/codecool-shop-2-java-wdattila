package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;

    public ProductDaoJdbc(DataSource dataSource, ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.dataSource = dataSource;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO Products(name, price, currency, description, category_id, supplier_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getDefaultPrice());
            statement.setString(3, product.getDefaultCurrency().toString());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getProductCategoryId());
            statement.setInt(6, product.getSupplierId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, price, currency, description, category_id, supplier_id FROM Products " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                Product product = getProductFromResultSet(rs);
                product.setId(id);
                return getProductFromResultSet(rs);
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
            String sql = "DELETE FROM Products " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Product getProductFromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        BigDecimal price = rs.getBigDecimal("price");
        String currency = rs.getString("currency");
        String description = rs.getString("description");
        int categoryId = rs.getInt("category_id");
        int supplierId = rs.getInt("supplier_id");
        Product product = new Product(name,
                price.setScale(2, RoundingMode.HALF_UP),
                currency,
                description,
                productCategoryDao.find(categoryId),
                supplierDao.find(supplierId));
        return product;
    }

    private List<Product> getProductsFromResulSet(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while(rs.next()){
            Product product = getProductFromResultSet(rs);
            product.setId(rs.getInt("id"));
            products.add(product);
        }

        return products;
    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, category_id, supplier_id FROM Products ";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.executeQuery();
            return getProductsFromResulSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, category_id, supplier_id FROM Products " +
                    "WHERE supplier_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, supplier.getId());
            ResultSet rs = statement.executeQuery();
            return getProductsFromResulSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, category_id, supplier_id FROM Products " +
                    "WHERE category_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, productCategory.getId());
            ResultSet rs = statement.executeQuery();
            return getProductsFromResulSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getMultipleByCategories(List<ProductCategory> productCategories) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, category_id, supplier_id FROM Products " +
                    "WHERE category_id IN (" +
                    String.join(",", Collections.nCopies(productCategories.size(), "?")) +
                    ")";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < productCategories.size(); i++) {
                statement.setInt(i+1, productCategories.get(i).getId());
            }
            ResultSet rs = statement.executeQuery();
            return getProductsFromResulSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getMultipleBySuppliers(List<Supplier> suppliers) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, currency, description, category_id, supplier_id FROM Products " +
                    "WHERE supplier_id IN (" +
                    String.join(",", Collections.nCopies(suppliers.size(), "?")) +
                    ")";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < suppliers.size(); i++) {
                statement.setInt(i+1, suppliers.get(i).getId());
            }
            ResultSet rs = statement.executeQuery();
            return getProductsFromResulSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getMultipleByCategoriesSuppliers(List<ProductCategory> productCategories, List<Supplier> suppliers) {
        return null;
    }

    @Override
    public List<Product> getMultipleById(String ids) {
        return null;
    }
}
