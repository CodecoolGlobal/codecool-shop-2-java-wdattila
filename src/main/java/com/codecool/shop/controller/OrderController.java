package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/order"})
public class OrderController extends HttpServlet {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(OrderController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received GET request");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        ShoppingCartService shoppingCartService = new ShoppingCartService();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", shoppingCartService.getCartDataById(1));
        engine.process("order.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request");

        ShoppingCartService shoppingCartService = new ShoppingCartService();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        logger.info(req.getParameterMap().toString());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        logger.info(shoppingCartService.toString());
        context.setVariable("cart", shoppingCartService.getCartDataById(1));
        engine.process("order.html", context, resp.getWriter());
    }
}
