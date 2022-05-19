package com.codecool.shop.controller;

import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.service.DatabaseManager;
import com.codecool.shop.service.ShoppingCartService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/api/payment"})
public class PaymentAPIController extends HttpServlet {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(PaymentAPIController.class);
    private final Gson gson = new Gson();
    private final DatabaseManager dbManager = new DatabaseManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        JsonObject products = gson.fromJson(builder.toString(), JsonObject.class);

        ShoppingCartService cartService = dbManager.getShoppingCartService();

        ShoppingCart cart = new ShoppingCart("cart");
        products.keySet().
                forEach(p -> cartService.addProductToCartById(cart, Integer.parseInt(p), products.get(p).getAsInt()));

        HttpSession session = req.getSession();

        cartService.addShoppingCart(cart, (int) session.getAttribute("userid"));

        Map<String, String> params = new HashMap<>();
        params.put("totalPrice", String.valueOf(cart.getTotalPrice()));
        params.put("targetUrl", "http://localhost:8081/payment");
        params.put("backToUrl", "http://localhost:8080/order?orderId=" + cart.getId());

        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(params));
    }
}
