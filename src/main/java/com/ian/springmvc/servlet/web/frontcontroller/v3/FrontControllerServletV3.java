package com.ian.springmvc.servlet.web.frontcontroller.v3;

import com.ian.springmvc.servlet.web.frontcontroller.ModelView;
import com.ian.springmvc.servlet.web.frontcontroller.MyView;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.ControllerV3;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
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
 * 3. 컨트롤러에서 뷰 이름(논리)과 모델을 반환 받음
 * 4. 뷰 리졸버를 호출하여 뷰 논리 이름에 프리픽스, 서픽스를 조합한 실제 뷰 경로(물리)를 반환 받음
 * 5. 뷰에서 뷰와 모델 데이터를 조합(렌더링)하여 클라이언트에 응답
 */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // 컨트롤러 매핑 정보
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        // 1. 요청에 일치하는 컨트롤러 매핑
        String requestURI = request.getRequestURI(); // /front-controller/v3/new-form
        ControllerV3 controllerV3 = controllerMap.get(requestURI);

        // 2. 일치하는 컨트롤러가 없을 경우 예외 처리
        if (controllerV3 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> params = getParams(request);

        // 3. 매핑된 컨트롤러 호출 및 요청 처리 (반환 값: 뷰 논리 이름, 모델)
        ModelView modelView = controllerV3.process(params);

        // 4. 뷰 논리 이름에 prefix, suffix 조합 (뷰 리졸빙)
        MyView view = viewResolver(modelView);

        // 5. 뷰 렌더링
        view.render(request, response, modelView.getModel());
    }

    private static Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> params.put(paramName, request.getParameter(paramName)));

        return params;
    }

    private static MyView viewResolver(ModelView modelView) {
        String viewName = modelView.getViewName(); // new-form

        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
