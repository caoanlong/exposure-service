package com.exposure.exposureservice.controller.admin;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.service.SysUserService;
import com.exposure.exposureservice.utils.JwtUtils;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "SysUserController", description = "系统用户管理")
@RestController
@RequestMapping("/admin/sysUser")
public class SysUserController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "findAll", notes = "查询所有系统用户", response = SysUser.class, responseContainer = "List")
    @RequiresPermissions(value = {"sysUser", "sysUser:list"}, logical = Logical.OR)
    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysUser> list = sysUserService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询系统用户列表", response = SysUser.class, responseContainer = "List")
    @RequiresPermissions(value = {"sysUser", "sysUser:list"}, logical = Logical.OR)
    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<SysUser>> list = sysUserService.findList(userName, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询系统用户详情", response = SysUser.class)
    @RequiresPermissions(value = {"sysUser", "sysUser:view"}, logical = Logical.OR)
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Long id) {
        SysUser sysUser = sysUserService.findById(id);
        return ResultUtils.success(sysUser);
    }

    @ApiOperation(value = "findByToken", notes = "根据TOKEN查询系统用户详情", response = SysUser.class)
    @GetMapping("/findByToken")
    public ResultBean<Object> findByToken(HttpServletRequest request) {
        Long id = Long.valueOf((String) request.getAttribute("sysUserId"));
        SysUser sysUser = sysUserService.findById(id);
        return ResultUtils.success(sysUser);
    }

    @ApiOperation(value = "add", notes = "添加系统用户")
    @RequiresPermissions(value = {"sysUser", "sysUser:add"}, logical = Logical.OR)
    @PostMapping("/add")
    public ResultBean<Object> add(HttpServletRequest request, @RequestBody SysUser sysUser) {
        Long id = Long.valueOf((String) request.getAttribute("sysUserId"));
        sysUser.setCreateUserId(id);
        sysUserService.insert(sysUser);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改系统用户")
    @RequiresPermissions(value = {"sysUser", "sysUser:edit"}, logical = Logical.OR)
    @PostMapping("/update")
    public ResultBean<Object> update(HttpServletRequest request, @RequestBody SysUser sysUser) {
        Long id = Long.valueOf((String) request.getAttribute("sysUserId"));
        sysUser.setUpdateUserId(id);
        sysUserService.update(sysUser);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除系统用户")
    @RequiresPermissions(value = {"sysUser", "sysUser:del"}, logical = Logical.OR)
    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Long> map) {
        Long id = map.get("id");
        sysUserService.del(id);
        return ResultUtils.success();
    }

    @ApiOperation(value = "login", notes = "系统用户登录")
    @PostMapping("/login")
    public ResultBean<Object> login(HttpServletResponse response, @RequestBody SysUser sysUser) {
        String userName = sysUser.getUserName();
        String password = sysUser.getPassword();

        if (StringUtils.isBlank(userName))
            throw new CommonException(ErrorCode.USERNAME_NOTNULL);
        if (StringUtils.isBlank(password))
            throw new CommonException(ErrorCode.PASSWORD_NOTNULL);

        SysUser user = sysUserService.findByNameAndPassword(userName, password);
        if (null == user) {
            throw new CommonException(ErrorCode.USERNAMEORPASSWORD_ERROR);
        }
        return authReturn(response, user.getId().toString());
    }

    private ResultBean<Object> authReturn(HttpServletResponse response, String sysUserId) {
        String jwt = jwtUtils.createJWT(Constant.JWT_ID, "se_nimadebi1234_fuckgouride_gouride", sysUserId, Constant.JWT_TTL);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", jwt);
        return ResultUtils.success();
    }
}
