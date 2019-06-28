package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.Label;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.service.LabelService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "LabelController", description = "标签管理")
@CrossOrigin
@RestController
@RequestMapping(value = {"/admin/label"})
public class LabelController {

    @Autowired
    private LabelService labelService;

    @ApiOperation(value = "findAll", notes = "查询所有标签", response = Label.class, responseContainer = "List")
    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<Label> list = labelService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询标签列表", response = Label.class, responseContainer = "List")
    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<Label>> list = labelService.findList(name, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询标签详情", response = Label.class)
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Long id) {
        Label label = labelService.findById(id);
        return ResultUtils.success(label);
    }

    @ApiOperation(value = "add", notes = "添加标签")
    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody Label label, HttpServletRequest request) {
        Long sysUserId = Long.valueOf((String) request.getAttribute("sysUserId"));
        label.setCreateUserId(sysUserId);
        labelService.insert(label);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改系统用户")
    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody Label label, HttpServletRequest request) {
        Long sysUserId = Long.valueOf((String) request.getAttribute("sysUserId"));
        label.setUpdateUserId(sysUserId);
        labelService.update(label);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除系统用户")
    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Long> map) {
        Long id = map.get("id");
        labelService.del(id);
        return ResultUtils.success();
    }
}
