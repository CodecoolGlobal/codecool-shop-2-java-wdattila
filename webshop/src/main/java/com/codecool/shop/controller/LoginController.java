package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.slf4j.ILoggerFactory;
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
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

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
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        // get user from database
        session.setAttribute("username", "chris");
        logger.info((String) session.getAttribute("username"));
        if(session.getAttribute("username") != null){
            response.sendRedirect(request.getContextPath()+"/");
        }else{
            engine.process("loginPage.html", context, response.getWriter());
        }
        // switch until here
    }
}
