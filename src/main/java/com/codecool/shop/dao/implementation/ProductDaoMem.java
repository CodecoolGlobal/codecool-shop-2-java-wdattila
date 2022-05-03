package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

    @Override
    public void readFromFile(ProductCategoryDao productCategoryDataStore, SupplierDao supplierDataStore) {
        try (Reader reader = new InputStreamReader(
                SupplierDaoMem.class.getResourceAsStream("/products.json"))) {
            Gson gson = new Gson();

            JsonObject data = gson.fromJson(reader, JsonObject.class);
            JsonArray products = data.getAsJsonArray("products");

            for (int i = 0; i < products.size(); i++) {
                JsonObject product = products.get(i).getAsJsonObject();
                add(new Product(product.get("name").getAsString(),
                        product.get("defaultPrice").getAsBigDecimal(),
                        product.get("defaultCurrency").getAsString(),
                        product.get("description").getAsString(),
                        productCategoryDataStore.find(product.get("categoryId").getAsInt()),
                        supplierDataStore.find(product.get("supplierId").getAsInt())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
