package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.DatabaseManager;
import com.codecool.shop.service.ShoppingCartService;
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
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class OrderFormController extends HttpServlet {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(OrderController.class);
    private final DatabaseManager dbManager = new DatabaseManager();

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

        shoppingCartService.saveOrder(session.getAttribute("userid"),
                req.getParameter("BillingCity"),
                req.getParameter("ShippingCity"),
                req.getParameter("name"));
    }
}
