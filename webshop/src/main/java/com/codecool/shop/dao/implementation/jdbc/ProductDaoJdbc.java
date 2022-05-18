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
                product.setId(id);
                return product;
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

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> getMultipleByCategories(List<ProductCategory> productCategories) {
        return null;
    }

    @Override
    public List<Product> getMultipleBySuppliers(List<Supplier> suppliers) {
        return null;
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
