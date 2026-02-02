package com.ian.springmvc.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// servlet name, mappling url은 중복이 있으면 안 됨
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");

        /**
         * WAS에서 생성한 Request, Respon
         */
        // request = org.apache.catalina.connector.RequestFacade@32f26053
        System.out.println("request = " + request);
        // response = org.apache.catalina.connector.ResponseFacade@741e7bce
        System.out.println("response = " + response);

        String username = request.getParameter("username");

        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Hello " + username);
    }
}
