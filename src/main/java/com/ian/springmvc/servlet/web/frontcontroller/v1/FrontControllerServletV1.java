package com.ian.springmvc.servlet.web.frontcontroller.v1;

import com.ian.springmvc.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import com.ian.springmvc.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import com.ian.springmvc.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    // 컨트롤러 매핑 정보
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        // 1. 요청에 일치하는 컨트롤러 매핑
        String requestURI = request.getRequestURI(); // /front-controller/v1/new-form
        ControllerV1 controllerV1 = controllerMap.get(requestURI);

        // 2. 일치하는 컨트롤러가 없을 경우 예외 처리
        if (controllerV1 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 3. 매핑된 컨트롤러 호출 및 요청 처리
        controllerV1.process(request, response);
    }
}
