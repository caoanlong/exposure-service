package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.entity.req.ThingDto;
import com.exposure.exposureservice.service.ThingService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "ThingController", description = "事物管理")
@RestController
@RequestMapping("/admin/thing")
public class ThingController {
    @Autowired
    private ThingService thingService;

    @PreAuthorize("hasAnyAuthority('thing', 'thing:list')")
    @GetMapping("/findAll")
    public ResultBean<Object> findAll(@RequestParam(value = "title", required = false) String title) {
        List<Thing> list = thingService.findAll(title);
        return ResultUtils.success(list);
    }

    @PreAuthorize("hasAnyAuthority('thing', 'thing:list')")
    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "sex", required = false) Integer sex,
            @RequestParam(value = "birthDay", required = false) Date birthDay,
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<Thing>> list = thingService.findList(title, type, sex, birthDay, area, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @PreAuthorize("hasAnyAuthority('thing', 'thing:view')")
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Long id) {
        Thing thing = thingService.findById(id);
        return ResultUtils.success(thing);
    }

    @PreAuthorize("hasAnyAuthority('thing', 'thing:add')")
    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody ThingDto thingDto, HttpServletRequest request) {
        Long sysUserId = Long.valueOf((String) request.getAttribute("sysUserId"));
        thingDto.setCreateType(1);
        thingDto.setCreateUserId(sysUserId);
        thingService.insert(thingDto);
        return ResultUtils.success();
    }

    @PreAuthorize("hasAnyAuthority('thing', 'thing:edit')")
    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody ThingDto thingDto, HttpServletRequest request) {
        Long sysUserId = Long.valueOf((String) request.getAttribute("sysUserId"));
        thingDto.setUpdateUserId(sysUserId);
        thingService.update(thingDto);
        return ResultUtils.success();
    }

    @PreAuthorize("hasAnyAuthority('thing', 'thing:del')")
    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody Map<String, Long> map) {
        Long id = map.get("id");
        thingService.del(id);
        return ResultUtils.success();
    }
}
