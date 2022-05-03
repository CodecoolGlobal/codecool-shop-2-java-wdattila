package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier gammaFreak = new Supplier("Gamma Freak", "RPG Video game developer");
        supplierDataStore.add(gammaFreak);
        Supplier camco = new Supplier("Camco", "Video game developer");
        supplierDataStore.add(camco);

        //setting up a new product category
        ProductCategory videogame = new ProductCategory("Video games", "Software", "Electronic games, that involves interaction with a user interface or input device to generate visual feedback");
        productCategoryDataStore.add(videogame);

        //setting up products and printing it
        productDataStore.add(new Product("Chinpokomon: Myrrh", new BigDecimal("29.9"), "EUR", "The next addition of the famous Chinpokomon series. The Myrrh edition contains the legendary chinpokomon Donkeytron.", videogame, gammaFreak));
        productDataStore.add(new Product("Chinpokomon: Platinum", new BigDecimal("29.9"), "EUR", "The next addition of the famous Chinpokomon series. The Platinum edition contains the legendary chinpokomon Lambtron.", videogame, camco));
        productDataStore.add(new Product("Muckman", new BigDecimal("15"), "EUR", "The Original first addition of the Muckman video game series", videogame, gammaFreak));
        productDataStore.add(new Product("Sanic", new BigDecimal("20"), "EUR", "The Original first addition of the Muckman video game series", videogame, gammaFreak));
        productDataStore.add(new Product("Sanic", new BigDecimal("20"), "EUR", "The Original first addition of the Muckman video game series", videogame, gammaFreak));
        productDataStore.add(new Product("Sanic", new BigDecimal("20"), "EUR", "The Original first addition of the Muckman video game series", videogame, gammaFreak));
        productDataStore.add(new Product("Sanic", new BigDecimal("20"), "EUR", "The Original first addition of the Muckman video game series", videogame, gammaFreak));
        productDataStore.add(new Product("Sanic", new BigDecimal("20"), "EUR", "The Original first addition of the Muckman video game series", videogame, gammaFreak));

    }
}
