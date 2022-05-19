package com.codecool.shop.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/api/session"})
public class SessionController extends HttpServlet {

    private final Gson gson = new Gson();
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        Integer intUserId = (Integer) session.getAttribute("userid");
        String userid = String.valueOf(intUserId);



        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        if(!userid.equals("null")){
            params.put("userid", userid);
        }


        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(params));

    }


}
