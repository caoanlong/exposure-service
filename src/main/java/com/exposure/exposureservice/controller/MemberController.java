package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(value = "MemberController", description = "会员管理")
@RestController
@RequestMapping(value = {"/admin/member", "/app/member"})
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/findAll")
    public ResultBean findAll() {
        List<Member> list = memberService.findAll();
        return ResultUtils.success(list);
    }
}
