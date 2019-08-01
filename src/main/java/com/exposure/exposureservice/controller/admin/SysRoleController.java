package com.exposure.exposureservice.controller.admin;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.SysRole;
import com.exposure.exposureservice.service.SysRoleService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "SysRoleController", description = "系统角色管理")
@RestController
@RequestMapping("/admin/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "findAll", notes = "查询所有系统角色", response = SysRole.class, responseContainer = "List")
    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysRole> list = sysRoleService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询系统角色列表", response = SysRole.class, responseContainer = "List")
    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<SysRole>> list = sysRoleService.findList(roleName, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询系统角色详情", response = SysRole.class)
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Integer id) {
        SysRole sysRole = sysRoleService.findById(id);
        return ResultUtils.success(sysRole);
    }

    @ApiOperation(value = "add", notes = "添加系统角色")
    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody SysRole sysRole) {
        sysRoleService.insert(sysRole);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改系统角色")
    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除系统角色")
    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Integer> map) {
        Integer id = map.get("id");
        sysRoleService.del(id);
        return ResultUtils.success();
    }
}
