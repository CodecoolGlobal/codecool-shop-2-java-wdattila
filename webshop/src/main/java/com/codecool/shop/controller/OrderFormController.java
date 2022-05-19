package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.DatabaseManager;
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
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/checkout"})
public class OrderFormController extends HttpServlet {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(OrderController.class);
    private final DatabaseManager dbManager = new DatabaseManager();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("paymentPage.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request");

        ShoppingCartService shoppingCartService = dbManager.getShoppingCartService();

        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = req.getSession();

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        JsonObject data = gson.fromJson(builder.toString(), JsonObject.class);

        shoppingCartService.saveOrder(1,
                data.get("BillingCity").getAsString(),
                data.get("ShippingCity").getAsString(),
                data.get("name").getAsString());
    }
}
