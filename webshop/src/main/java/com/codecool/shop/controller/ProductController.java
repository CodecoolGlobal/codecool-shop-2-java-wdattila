package com.codecool.shop.controller;

import com.codecool.shop.model.Product;
import com.codecool.shop.service.DatabaseManager;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(ProductController.class);
    private final DatabaseManager dbManager = new DatabaseManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received GET request");
        ProductService productService = dbManager.getProductService();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String category = req.getParameterMap().containsKey("category") ? req.getParameter("category") : "";
        String supplier = req.getParameterMap().containsKey("supplier") ? req.getParameter("supplier") : "";
        List<Product> products = productService.getProductsForMultipleCategorySupplier(category, supplier);

        context.setVariable("categories", productService.getAllCategory());
        context.setVariable("suppliers", productService.getAllSupplier());

        context.setVariable("mainCategories", productService.getCategoryByIds(category));
        context.setVariable("mainSuppliers", productService.getSupplierByIds(supplier));

        context.setVariable("products", products);
        context.setVariable("relevantCategories", products.stream()
                .map(Product::getProductCategory)
                .collect(Collectors.toList()));
        context.setVariable("relevantSuppliers", products.stream()
                .map(Product::getSupplier)
                .collect(Collectors.toList()));

        engine.process("product/index.html", context, resp.getWriter());
    }

}
