package com.codecool.payment.controller;


import com.codecool.payment.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PaymentController", urlPatterns = "/payment", loadOnStartup = 1)
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String totalPrice = request.getParameterMap().containsKey("totalPrice") ? request.getParameter("totalPrice") : "";
        String backToUrl = request.getParameterMap().containsKey("backToUrl") ? request.getParameter("backToUrl") : "";

        context.setVariable("price", totalPrice);
        context.setVariable("url", backToUrl);

        engine.process("paymentPage.html", context, response.getWriter());
    }
}
