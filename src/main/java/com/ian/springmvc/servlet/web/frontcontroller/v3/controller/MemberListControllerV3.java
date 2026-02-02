package com.ian.springmvc.servlet.web.frontcontroller.v3.controller;


import com.ian.springmvc.servlet.domain.member.Member;
import com.ian.springmvc.servlet.domain.member.MemberRepository;
import com.ian.springmvc.servlet.web.frontcontroller.ModelView;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> params) {
        List<Member> members = memberRepository.findAll();

        ModelView modelView = new ModelView("members");
        modelView.getModel().put("members", members);

        return modelView;
    }
}
