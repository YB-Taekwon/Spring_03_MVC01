package com.ian.springmvc.servlet.web.frontcontroller.v2;

import com.ian.springmvc.servlet.web.frontcontroller.MyView;
import com.ian.springmvc.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import com.ian.springmvc.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import com.ian.springmvc.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 전체 흐름
 * 1. 프론트 컨트롤러에서 컨트롤러 매핑 정보를 저장
 * 2. 요청이 들어오면 프론트 컨트롤러가 일치하는 컨트롤러 매핑
 * 3. 컨트롤러에서 반환 받은 뷰 정보, 모델을 뷰에 전달
 * 4. 뷰에서 뷰와 모델 데이터를 조합(렌더링)하여 클라이언트에 응답
 */
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    // 컨트롤러 매핑 정보
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        // 1. 요청에 일치하는 컨트롤러 매핑
        String requestURI = request.getRequestURI(); // /front-controller/v2/new-form
        ControllerV2 controllerV2 = controllerMap.get(requestURI);

        // 2. 일치하는 컨트롤러가 없을 경우 예외 처리
        if (controllerV2 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 3. 매핑된 컨트롤러 호출 및 요청 처리 (반환 값: 뷰 경로)
        MyView view = controllerV2.process(request, response); // new MyView("/WEB-INF/views/new-form.jsp");
        // 4. 반환된 뷰 렌더링
        view.render(request, response);
    }
}
