package com.ian.springmvc.servlet.web.frontcontroller.v3.controller;


import com.ian.springmvc.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> params) {
        return new ModelView("new-form");
    }
}
