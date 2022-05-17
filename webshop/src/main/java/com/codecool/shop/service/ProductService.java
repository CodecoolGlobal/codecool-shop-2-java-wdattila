package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.mem.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
    }

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
        List<Supplier> suppliers;
        if(supplierIds == null || supplierIds.equals("")) {
            suppliers = supplierDao.getAll();
        }
        else {
            suppliers = supplierDao.getMultipleById(supplierIds);
        }

        List<ProductCategory> categories;
        if(categoryIds == null || categoryIds.equals("")) {
            categories = productCategoryDao.getAll();
        }
        else {
            categories = productCategoryDao.getMultipleById(categoryIds);
        }

        return productDao.getMultipleByCategoriesSuppliers(categories, suppliers);
    }

    public List<ProductCategory> getCategoryByIds(String categoryIds){
        return productCategoryDao.getMultipleById(categoryIds);
    }

    public List<Supplier> getSupplierByIds(String supplierIds){
        return supplierDao.getMultipleById(supplierIds);
    }

    public List<ProductCategory> getAllCategory(){
        return productCategoryDao.getAll();
    }

    public List<Supplier> getAllSupplier(){
        return supplierDao.getAll();
    }

    public List<Product> getProductsByIds(String productIds){
        return productIds != null ? productDao.getMultipleById(productIds) : null;
    }
}
