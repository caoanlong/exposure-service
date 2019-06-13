package com.exposure.exposureservice.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("view", "index");
        return "index";
    }
}
