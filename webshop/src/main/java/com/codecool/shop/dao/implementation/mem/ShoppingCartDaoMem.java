package com.codecool.shop.dao.implementation.mem;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.ShoppingCart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartDaoMem implements ShoppingCartDao {
    private List<ShoppingCart> data = new ArrayList<>();
    private static ShoppingCartDaoMem instance = null;

    private ShoppingCartDaoMem() {
    }

    public static ShoppingCartDaoMem getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ShoppingCart shoppingCart) {
        shoppingCart.setId(data.size() + 1);
        data.add(shoppingCart);
    }

    @Override
    public ShoppingCart find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<ShoppingCart> getAll() {
        return data;
    }

    @Override
    public List<ShoppingCart> getMultipleById(String ids) {
        return data.stream()
                .filter(category -> Arrays.asList(ids.split(",")).contains(String.valueOf(category.getId())))
                .collect(Collectors.toList());
    }
}
