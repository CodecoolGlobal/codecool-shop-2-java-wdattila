package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.User;
import com.codecool.shop.service.DatabaseManager;
import com.codecool.shop.service.HashManager;
import com.codecool.shop.service.UserService;
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
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final DatabaseManager dbManager = new DatabaseManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession session = request.getSession();
        logger.info((String) session.getAttribute("username"));
        if(session.getAttribute("username") != null){
            response.sendRedirect(request.getContextPath()+"/");
        }else{
            engine.process("loginPage.html", context, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession session = request.getSession();
        String usernameOrEmail = request.getParameter("user");
        String password = request.getParameter("password");
        UserService userService = dbManager.getUserService();
        User user = userService.getUserByNameOrEmail(usernameOrEmail, usernameOrEmail);
        if(user == null){
            context.setVariable("loginAttempt",false);
            engine.process("loginPage.html", context, response.getWriter());
        }
        try {
            String typedInPasswordHash = Arrays.toString(HashManager.passwordToHash(password, user.getSalt()));
            String databasePasswordHash = Arrays.toString(user.getPassword());
            if(!typedInPasswordHash.equals(databasePasswordHash)){
                context.setVariable("loginAttempt",false);
                engine.process("loginPage.html", context, response.getWriter());
            }else{
                session.setAttribute("username", user.getName());
                logger.info("Session created with username: "+ session.getAttribute("username"));
                response.sendRedirect(request.getContextPath()+"/");
            }
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
