package com.tdtu.javaFn.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type","text/html");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/login.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/login.jsp");
        HttpSession session = req.getSession();

        if (user==null || password==null){
            session.setAttribute("error","Please provide username and password");
            dispatcher.forward(req,resp);

        } else if (user.isEmpty()||password.isEmpty()) {
            session.setAttribute("error","Username and password must not be empty");

            dispatcher.forward(req,resp);

        } else if (user.equals("admin") && password.equals("admin")) {

            resp.sendRedirect("/");
        }else {
            session.setAttribute("error","Invalid username or password");

            dispatcher.forward(req,resp);
    }


    }
}
