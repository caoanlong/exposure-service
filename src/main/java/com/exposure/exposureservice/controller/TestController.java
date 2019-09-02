package com.exposure.exposureservice.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "TestController", description = "测试")
@RestController
@RequestMapping("/test/main")
public class TestController {
    public String test() {
        return "test";
    }
}
