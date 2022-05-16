package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier);
    Supplier find(int id);
    void remove(int id);
    void readFromFile();

    List<Supplier> getAll();
    List<Supplier> getMultipleById(String ids);
}
