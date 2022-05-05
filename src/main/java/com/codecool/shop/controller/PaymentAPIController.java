package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ShoppingCartService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/api/payment"})
public class PaymentAPIController extends HttpServlet {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(PaymentAPIController.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        JsonObject products = gson.fromJson(builder.toString(), JsonObject.class);

        ShoppingCartService cartService = new ShoppingCartService();

        ShoppingCart cart = new ShoppingCart("cart");
        cartService.addShoppingCart(cart);
        products.keySet().
                forEach(p -> cartService.addProductToCartById(cart.getId(), Integer.parseInt(p), products.get(p).getAsInt()));

        Map<String, String> params = new HashMap<>();
        params.put("totalPrice", String.valueOf(cart.getTotalPrice()));
        params.put("targetUrl", "/payment");
        params.put("backToUrl", "/order?orderId=" + cart.getId());

        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(params));
    }
}
