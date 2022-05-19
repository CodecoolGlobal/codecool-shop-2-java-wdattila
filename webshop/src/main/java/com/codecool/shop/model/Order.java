package com.codecool.shop.model;

public class Order extends BaseModel{
    private int userId;
    private int cartId;
    private String shippingAddress;
    private String postAddress;
    private String phone;

    public Order(String name, int userId, int cartId, String shippingAddress, String postAddress, String phone) {
        super(name);
        this.userId = userId;
        this.cartId = cartId;
        this.shippingAddress = shippingAddress;
        this.postAddress = postAddress;
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public int getCartId() {
        return cartId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public String getPhone() {
        return phone;
    }
}
