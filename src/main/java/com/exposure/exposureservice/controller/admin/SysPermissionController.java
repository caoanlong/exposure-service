package com.exposure.exposureservice.controller.admin;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.SysPermission;
import com.exposure.exposureservice.entity.SysRole;
import com.exposure.exposureservice.service.SysPermissionService;
import com.exposure.exposureservice.service.SysRoleService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "SysPermissionController", description = "系统权限管理")
@RestController
@RequestMapping("/admin/sysPermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "findAll", notes = "查询所有系统权限", response = SysPermission.class, responseContainer = "List")
    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysPermission> list = sysPermissionService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询系统权限列表", response = SysPermission.class, responseContainer = "List")
    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "perName", required = false) String perName,
            @RequestParam(value = "perType", required = false) String perType,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<SysPermission>> list = sysPermissionService.findList(perName, perType, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询系统权限详情", response = SysPermission.class)
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Integer id) {
        SysPermission sysPermission = sysPermissionService.findById(id);
        return ResultUtils.success(sysPermission);
    }

    @ApiOperation(value = "add", notes = "添加系统权限")
    @PostMapping("/add")
    public ResultBean<Object> add(HttpServletRequest request, @RequestBody SysPermission sysPermission) {
        Long id = Long.valueOf((String) request.getAttribute("sysUserId"));
        sysPermission.setCreateUserId(id);
        sysPermissionService.insert(sysPermission);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改系统权限")
    @PostMapping("/update")
    public ResultBean<Object> update(HttpServletRequest request, @RequestBody SysPermission sysPermission) {
        Long id = Long.valueOf((String) request.getAttribute("sysUserId"));
        sysPermission.setUpdateUserId(id);
        sysPermissionService.update(sysPermission);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除系统权限")
    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Integer> map) {
        Integer id = map.get("id");
        sysPermissionService.del(id);
        return ResultUtils.success();
    }

    @ApiOperation(value = "findByPid", notes = "根据ID查询系统权限列表", response = SysPermission.class)
    @GetMapping("/findByPid")
    public ResultBean<Object> findByPid(@RequestParam(value = "pid", required = false) Integer pid) {
        if (null == pid) pid = -1;
        List<SysPermission> sysPermissions = sysPermissionService.findPermissionByPid(pid);
        return ResultUtils.success(sysPermissions);
    }
}
