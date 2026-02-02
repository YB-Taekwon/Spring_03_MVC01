package com.ian.springmvc.servlet.web.frontcontroller.v3.controller;

import com.ian.springmvc.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> params);
}
