package com.ian.springmvc.servlet.web.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class MyView {

    private String viewPath;

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
