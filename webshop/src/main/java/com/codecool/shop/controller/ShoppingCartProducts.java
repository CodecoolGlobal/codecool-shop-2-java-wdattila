package com.codecool.shop.controller;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.codecool.shop.service.DatabaseManager;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;

@WebServlet(name = "ShoppingCartProducts", urlPatterns = "/api/products", loadOnStartup = 3)
public class ShoppingCartProducts extends HttpServlet {
    private final DatabaseManager dbManager = new DatabaseManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        ProductService productService = dbManager.getProductService();
        String productsStr = request.getParameter("products");
        String[] productsIdList = productsStr.split(",");
        StringBuilder productListResponse = new StringBuilder();
        List<Product> jsonResponse = new ArrayList<>();
        for (String productId : productsIdList) {
            Product product = productService.getProductById(Integer.parseInt(productId));
//            productListResponse.append(product.toString());
            jsonResponse.add(product);
        }


        PrintWriter out = response.getWriter();

        out.println(gson.toJson(jsonResponse));
    }
}
