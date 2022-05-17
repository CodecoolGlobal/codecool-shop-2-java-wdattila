package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
    }

    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
            instance.readFromFile();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        category.setId(data.size() + 1);
        data.add(category);
    }

    @Override
    public ProductCategory find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return data;
    }

    private void readFromFile() {
        try (Reader reader = new InputStreamReader(
                SupplierDaoMem.class.getResourceAsStream("/categories.json"))) {
            Gson gson = new Gson();

            JsonObject data = gson.fromJson(reader, JsonObject.class);
            JsonArray categories = data.getAsJsonArray("categories");

            for (int i = 0; i < categories.size(); i++) {
                JsonObject category = categories.get(i).getAsJsonObject();
                add(new ProductCategory(category.get("name").getAsString(), category.get("department").getAsString(), category.get("description").getAsString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getMultipleById(String ids) {
        return data.stream()
                .filter(category -> Arrays.asList(ids.split(",")).contains(String.valueOf(category.getId())))
                .collect(Collectors.toList());
    }
}
