package com.exposure.exposureservice.controller.admin;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberViewController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/member")
    public String index(
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            ModelMap modelMap) {
        List<Member> list = memberService.findList(mobile, pageIndex, pageSize).getList();
        modelMap.addAttribute("members", list);
        return "member";
    }
}
