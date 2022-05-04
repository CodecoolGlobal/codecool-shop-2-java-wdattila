package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.BaseModel;
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
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        String category = "";
        String supplier = "";
        if(req.getParameterMap().containsKey("category") && !req.getParameterMap().containsKey("supplier")){
            category = req.getParameter("category");
            context.setVariable("products", productService.getProductsForMultipleCategory(category));
        }
        else if(!req.getParameterMap().containsKey("category") && req.getParameterMap().containsKey("supplier")){
            supplier = req.getParameter("supplier");
            context.setVariable("products", productService.getProductsForMultipleSupplier(supplier));
        }
        else if(!req.getParameterMap().containsKey("category") && !req.getParameterMap().containsKey("supplier")){
            context.setVariable("products", productDataStore.getAll());
        }
        else {
            supplier = req.getParameter("supplier");
            category = req.getParameter("category");
            context.setVariable("products", productService.getProductsForMultipleCategorySupplier(category, supplier));
        }

        context.setVariable("mainCategories", productCategoryDataStore.getMultipleById(category));

        engine.process("product/index.html", context, resp.getWriter());
    }

}
