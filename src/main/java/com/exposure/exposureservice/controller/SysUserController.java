package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.service.SysUserService;
import com.exposure.exposureservice.utils.JwtUtils;
import com.exposure.exposureservice.utils.MD5Utils;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "SysUserController", description = "系统用户管理")
@RestController
@RequestMapping(value = {"/admin/sysUser"})
public class SysUserController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "findAll", notes = "查询所有系统用户", response = SysUser.class, responseContainer = "List")
    @GetMapping("/findAll")
    public ResultBean findAll() {
        List<SysUser> list = sysUserService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询系统用户列表", response = SysUser.class, responseContainer = "List")
    @GetMapping("/findList")
    public ResultBean findList(
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<SysUser>> list = sysUserService.findList(userName, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询系统用户详情", response = SysUser.class)
    @GetMapping("/findById")
    public ResultBean findById(@RequestParam("id") Long id) {
        SysUser sysUser = sysUserService.findById(id);
        return ResultUtils.success(sysUser);
    }

    @ApiOperation(value = "findByToken", notes = "根据TOKEN查询系统用户详情", response = SysUser.class)
    @GetMapping("/findByToken")
    public ResultBean findByToken(HttpServletRequest request) {
        Long id = Long.valueOf((String) request.getAttribute("sysUserId"));
        SysUser sysUser = sysUserService.findById(id);
        return ResultUtils.success(sysUser);
    }

    @ApiOperation(value = "add", notes = "添加系统用户")
    @PostMapping("/add")
    public ResultBean add(@RequestBody SysUser sysUser) {
        sysUserService.insert(sysUser);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改系统用户")
    @PostMapping("/update")
    public ResultBean update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除系统用户")
    @PostMapping("/del")
    public ResultBean del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Long> map) {
        Long id = map.get("id");
        sysUserService.del(id);
        return ResultUtils.success();
    }

    @ApiOperation(value = "auth", notes = "系统用户认证")
    @PostMapping("/auth")
    public ResultBean auth(HttpServletResponse response, @RequestBody  Map<String, String> map) {
        String userName = map.get("userName");
        String password = map.get("password");
        String type = map.get("type");

        if (StringUtils.isBlank(userName))
            throw new CommonException(ErrorCode.USERNAME_NOTNULL);
        if (StringUtils.isBlank(password))
            throw new CommonException(ErrorCode.PASSWORD_NOTNULL);

        String pwd = MD5Utils.md5(password, Constant.MD5_SALT);
        SysUser sysUser = sysUserService.findByNameAndPassword(userName, pwd);

        if (type.equals("login")) {
            if (null == sysUser) {
                throw new CommonException(ErrorCode.USERNAMEORPASSWORD_ERROR);
            }
            return authReturn(response, sysUser.getId().toString());
        }
        if (null != sysUser) {
            throw new CommonException(ErrorCode.USERNAME_EXIST);
        }
        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName(userName);
        sysUser1.setPassword(pwd);
        sysUserService.insert(sysUser1);
        Long id = sysUser1.getId();
        return authReturn(response, id.toString());
    }

    private ResultBean authReturn(HttpServletResponse response, String sysUserId) {
        String jwt = jwtUtils.createJWT(Constant.JWT_ID, "nimadebi1234fuckgouride", sysUserId, Constant.JWT_TTL);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", jwt);
        return ResultUtils.success();
    }
}
