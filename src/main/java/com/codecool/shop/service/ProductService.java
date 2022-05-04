package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;

    public ProductService() {
        this.productDao = ProductDaoMem.getInstance();
        this.productCategoryDao = ProductCategoryDaoMem.getInstance();
        this.supplierDao = SupplierDaoMem.getInstance();
    }

    public ProductCategory getProductCategory(int categoryId){
        return productCategoryDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId){
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<Product> getProductsForMultipleCategory(String categoryIds){
        List<ProductCategory> categories = productCategoryDao.getMultipleById(categoryIds);
        return productDao.getMultipleByCategories(categories);
    }

    public List<Product> getProductsForMultipleSupplier(String supplierIds){
        List<Supplier> suppliers = supplierDao.getMultipleById(supplierIds);
        return productDao.getMultipleBySuppliers(suppliers);
    }

    public List<Product> getProductsForMultipleCategorySupplier(String categoryIds, String supplierIds){
        List<Supplier> suppliers = supplierDao.getMultipleById(supplierIds);
        List<ProductCategory> categories = productCategoryDao.getMultipleById(categoryIds);
        return productDao.getMultipleByCategoriesSuppliers(categories, suppliers);
    }
}
