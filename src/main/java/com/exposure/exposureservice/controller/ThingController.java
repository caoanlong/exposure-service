package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.entity.req.ThingDto;
import com.exposure.exposureservice.service.ThingService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "ThingController", description = "事物管理")
//@CrossOrigin
@RestController
@RequestMapping(value = {"/app/thing", "/admin/thing"})
public class ThingController {
    @Autowired
    private ThingService thingService;

    @GetMapping("/findAll")
    public ResultBean findAll() {
        List<Thing> list = thingService.findAll();
        return ResultUtils.success(list);
    }

    @GetMapping("/findList")
    public ResultBean findList(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<Thing>> list = thingService.findList(title, type,  pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @GetMapping("/findById")
    public ResultBean findById(@RequestParam("id") Long id) {
        Thing thing = thingService.findById(id);
        return ResultUtils.success(thing);
    }

    @PostMapping("/add")
    public ResultBean add(@RequestBody ThingDto thingDto) {

        thingService.insert(thingDto);
        return ResultUtils.success();
    }

    @PostMapping("/update")
    public ResultBean update(@RequestBody ThingDto thingDto) {
        thingService.update(thingDto);
        return ResultUtils.success();
    }

    @PostMapping("/del")
    public ResultBean del(@RequestBody Map<String, Long> map) {
        Long id = map.get("id");
        thingService.del(id);
        return ResultUtils.success();
    }
}
