package com.ian.springmvc.servlet.web.frontcontroller.v1.controller;

import com.ian.springmvc.servlet.domain.member.Member;
import com.ian.springmvc.servlet.domain.member.MemberRepository;
import com.ian.springmvc.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        // Model에 데이터 보관
        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/views/members.jsp";

        request.getRequestDispatcher(viewPath)
                .forward(request, response);
    }
}
