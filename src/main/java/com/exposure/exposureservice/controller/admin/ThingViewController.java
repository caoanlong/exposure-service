package com.exposure.exposureservice.controller.admin;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/thing")
public class ThingViewController {
    @Autowired
    private ThingService thingService;

    @GetMapping("/list")
    public String list(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            Model model) {
        PageBean<List<Thing>> list = thingService.findList(title, type, pageIndex, pageSize);
        model.addAttribute("title", title);
        model.addAttribute("type", type);
        model.addAttribute("view", "thing");
        model.addAttribute("things", list);
        return "thing";
    }

    @GetMapping("/add")
    public String add() {
        return "addThing";
    }
}
