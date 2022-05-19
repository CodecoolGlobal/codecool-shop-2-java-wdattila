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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private final DatabaseManager dbManager = new DatabaseManager();

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            context.setVariable("user",null);
            engine.process("registerPage.html", context, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userService = dbManager.getUserService();
        if (userService.getUserByNameOrEmail(username,email) != null) {
            context.setVariable("user",username);
            engine.process("registerPage.html", context, response.getWriter());
        } else {
            User user = null;
            try {
                byte[] salt = HashManager.generateSalt();
                user = new User(username, email, HashManager.passwordToHash(password, salt), salt);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
            if (user != null) {
                userService.addUser(user);
                session.setAttribute("username", username);
                session.setAttribute("userid", user.getId());
            }
            response.sendRedirect("/login");
        }
    }
}
