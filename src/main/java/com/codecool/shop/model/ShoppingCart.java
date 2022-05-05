package com.codecool.shop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart extends BaseModel{
    private Map<Product, Integer> products;

    public ShoppingCart(String name) {
        super(name);
        products = new HashMap<>();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public void addProduct(Product product, int quantity){
        this.products.put(product, quantity);
    }

    public int getTotalPrice(){
        int sum = 0;
        for (Product product: products.keySet()) {
            sum+=product.getPriceValue().intValue();
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}
