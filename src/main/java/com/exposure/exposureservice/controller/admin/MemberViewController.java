package com.exposure.exposureservice.controller.admin;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberViewController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public String list(
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            Model model) {
        PageBean<List<Member>> list = memberService.findList(mobile, pageIndex, pageSize);
        model.addAttribute("view", "member");
        model.addAttribute("members", list);
        return "member";
    }
}
