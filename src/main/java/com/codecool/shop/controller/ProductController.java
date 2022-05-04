package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductService();

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
