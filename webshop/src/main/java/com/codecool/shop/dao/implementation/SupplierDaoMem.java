package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierDaoMem implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
            instance.readFromFile();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setId(data.size() + 1);
        data.add(supplier);
    }

    @Override
    public Supplier find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Supplier> getAll() {
        return data;
    }

    private void readFromFile() {
        try (Reader reader = new InputStreamReader(
                SupplierDaoMem.class.getResourceAsStream("/suppliers.json"))) {
            Gson gson = new Gson();

            JsonObject data = gson.fromJson(reader, JsonObject.class);
            JsonArray suppliers = data.getAsJsonArray("suppliers");

            for (int i = 0; i < suppliers.size(); i++) {
                JsonObject supplier = suppliers.get(i).getAsJsonObject();
                add(new Supplier(supplier.get("name").getAsString(), supplier.get("description").getAsString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getMultipleById(String ids) {
        return data.stream()
                .filter(category -> Arrays.asList(ids.split(",")).contains(String.valueOf(category.getId())))
                .collect(Collectors.toList());
    }
}
