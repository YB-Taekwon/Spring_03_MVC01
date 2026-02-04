package com.ian.springmvc.servlet.web.frontcontroller.v5.adapter;

import com.ian.springmvc.servlet.web.frontcontroller.ModelView;
import com.ian.springmvc.servlet.web.frontcontroller.v4.ControllerV4;
import com.ian.springmvc.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controllerV4 = (ControllerV4) handler;

        Map<String, String> params = createParams(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controllerV4.process(params, model);

        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);

        return modelView;
    }

    private Map<String, String> createParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> params.put(paramName, request.getParameter(paramName)));

        return params;
    }
}
