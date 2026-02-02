package com.ian.springmvc.servlet.web.frontcontroller.v3.controller;


import com.ian.springmvc.servlet.domain.member.Member;
import com.ian.springmvc.servlet.domain.member.MemberRepository;
import com.ian.springmvc.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> params) {
        String username = params.get("username");
        int age = Integer.parseInt(params.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView modelView = new ModelView("save");
        modelView.getModel().put("member", member);

        return modelView;
    }
}
