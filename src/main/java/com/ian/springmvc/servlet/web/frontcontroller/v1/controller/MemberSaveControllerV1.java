package com.ian.springmvc.servlet.web.frontcontroller.v1.controller;

import com.ian.springmvc.servlet.domain.member.Member;
import com.ian.springmvc.servlet.domain.member.MemberRepository;
import com.ian.springmvc.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model에 데이터 보관
        request.setAttribute("member", member);

        String viewPath = "/WEB-INF/views/save.jsp";

        request.getRequestDispatcher(viewPath)
                .forward(request, response);
    }
}
