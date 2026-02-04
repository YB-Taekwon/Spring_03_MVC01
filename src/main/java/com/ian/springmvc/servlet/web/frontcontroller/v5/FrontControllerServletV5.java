package com.ian.springmvc.servlet.web.frontcontroller.v5;

import com.ian.springmvc.servlet.web.frontcontroller.ModelView;
import com.ian.springmvc.servlet.web.frontcontroller.MyView;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.ian.springmvc.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.ian.springmvc.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.ian.springmvc.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.ian.springmvc.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.ian.springmvc.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.ian.springmvc.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 전체 흐름
 * 1. 프론트 컨트롤러에서 컨트롤러 매핑 정보를 저장
 * 2. 요청이 들어오면 프론트 컨트롤러가 일치하는 컨트롤러 매핑 -> 핸들러 매핑
 * 3. 핸들러를 처리할 수 있는 어댑터 조회
 * 4. 매핑 된 컨트롤러를 실행 -> 핸들러 어댑터
 * 5. 컨트롤러에서 요청을 처리하고 뷰 이름 (또는 모델과 함께)을 반환 받음 -> 요청을 처리하는 핸들러(컨트롤러)의 반환 값에 따라 달라짐
 * 6. 뷰 리졸버를 호출하여 뷰 논리 이름에 프리픽스, 서픽스를 조합한 실제 뷰 경로(물리)를 반환 받음
 * 7. 뷰에서 뷰와 모델 데이터를 조합(렌더링)하여 클라이언트에 응답
 * <p>
 * 핸들러 어댑터의 경우 어댑터 역할의 인터페이스를 추가하여 다양한 컨트롤러를 상황에 맞게 호출하는 역할을 함
 */
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap(); // 매핑 정보 저장
        initHandlerAdapter(); // 어댑터 초기화
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 핸들러 조회 (요청 정보와 일치하는 컨트롤러 조회)
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 핸들러를 처리할 핸들러 어댑터 조회
        MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        // 3. 핸들러 어댑터로 요청을 처리할 핸들러(컨트롤러) 실행 -> 요청 처리
        ModelView modelView = handlerAdapter.handle(request, response, handler);

        // 4. 반환된 뷰의 논리 이름을 뷰 리졸버로 실제 물리 경로로 변환
        MyView view = viewResolver(modelView);

        // 5. 뷰에서 요청 완료된 데이터(모델)를 조합하여 렌더링 후 클라이언트에게 응답(뷰) 반환
        view.render(request, response, modelView.getModel());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapter() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappingMap.get(request.getRequestURI());
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new IllegalArgumentException("handler adapter not found: handler=" + handler);
    }

    private static MyView viewResolver(ModelView modelView) {
        return new MyView("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
    }
}
