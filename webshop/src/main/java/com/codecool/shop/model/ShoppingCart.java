package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart extends BaseModel{
    private Map<Product, Integer> products;

    public ShoppingCart(String name) {
        super(name);
        products = new HashMap<>();
    }

    public ShoppingCart() {
        super("cart");
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

    public BigDecimal getTotalPrice(){
        BigDecimal sum = new BigDecimal(0);
        for (Product product: products.keySet()) {
            sum = sum.add(product.getPriceValue().multiply(BigDecimal.valueOf(products.get(product))));
        }
        return sum;
    }

    public String getTotalPriceString() {
        return String.valueOf(getTotalPrice()) + " " + getCurrency();
    }

    public String getCurrency(){
        return products.keySet().stream()
                .map(product -> product.getDefaultCurrency().toString())
                .findFirst()
                .orElse("");
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
