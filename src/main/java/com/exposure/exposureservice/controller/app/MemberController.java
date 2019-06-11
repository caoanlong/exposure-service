package com.exposure.exposureservice.controller.app;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.utils.JwtUtils;
import com.exposure.exposureservice.utils.MD5Utils;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "MemberController", description = "会员管理")
@RestController
@RequestMapping(value = {"/admin/member", "/app/member"})
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation(value = "findAll", notes = "查询所有会员", response = Member.class, responseContainer = "List")
    @GetMapping("/findAll")
    public ResultBean findAll() {
        List<Member> list = memberService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询会员列表", response = Member.class, responseContainer = "List")
    @GetMapping("/findList")
    public ResultBean findList(
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<Member>> list = memberService.findList(mobile, pageIndex, pageSize);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询会员详情", response = Member.class)
    @GetMapping("/findById")
    public ResultBean findById(@RequestParam("id") Long id) {
        Member member = memberService.findById(id);
        return ResultUtils.success(member);
    }

    @ApiOperation(value = "findByToken", notes = "根据TOKEN查询会员详情", response = Member.class)
    @GetMapping("/findByToken")
    public ResultBean findByToken(HttpServletRequest request) {
        Long id = Long.valueOf((String) request.getAttribute("memberId"));
        Member member = memberService.findById(id);
        return ResultUtils.success(member);
    }

    @ApiOperation(value = "add", notes = "添加会员")
    @PostMapping("/add")
    public ResultBean add(@RequestBody Member member) {
        memberService.insert(member);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改会员")
    @PostMapping("/update")
    public ResultBean update(@RequestBody Member member) {
        memberService.update(member);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除会员")
    @PostMapping("/del")
    public ResultBean del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Long> map) {
        Long id = map.get("id");
        memberService.del(id);
        return ResultUtils.success();
    }

    @ApiOperation(value = "auth", notes = "会员认证")
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
        Member member = memberService.findByNameAndPassword(userName, pwd);

        if (type.equals("login")) {
            if (null == member) {
                throw new CommonException(ErrorCode.USERNAMEORPASSWORD_ERROR);
            }
            return authReturn(response, member.getId().toString());
        }
        if (null != member) {
            throw new CommonException(ErrorCode.USERNAME_EXIST);
        }
        Member member1 = new Member();
        member1.setUserName(userName);
        member1.setPassword(pwd);
        memberService.insert(member1);
        Long id = member1.getId();
        return authReturn(response, id.toString());
    }

    private ResultBean authReturn(HttpServletResponse response, String memberId) {
        String jwt = jwtUtils.createJWT(Constant.JWT_ID, "nimadebi1234fuck", memberId, Constant.JWT_TTL);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", jwt);
        return ResultUtils.success();
    }
}
