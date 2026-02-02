package com.ian.springmvc.servlet.web.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class MyView {

    private String viewPath;

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }

    public void render(
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> model
    ) throws ServletException, IOException {
        // 받아온 모델을 뷰로 전달
        modelToRequestAttribute(request, model);

        // 모델 전달 후 포워딩
        request.getRequestDispatcher(viewPath).forward(request, response);
    }

    private void modelToRequestAttribute(HttpServletRequest request, Map<String, Object> model) {
        model.forEach(request::setAttribute);
    }
}
