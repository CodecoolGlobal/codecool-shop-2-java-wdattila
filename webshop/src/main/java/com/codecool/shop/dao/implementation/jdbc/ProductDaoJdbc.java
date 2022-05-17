package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

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
